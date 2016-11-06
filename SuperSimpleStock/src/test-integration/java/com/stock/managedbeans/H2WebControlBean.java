package com.stock.managedbeans;

import com.stock.common.SchemaBuider;
import org.h2.server.web.WebServer;
import org.h2.tools.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.lang.management.ManagementFactory;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by khush on 06/11/2016.
 */
@Component
@Profile("test")
@ManagedResource(objectName = "com.stock:name=H2WebConsoleControl", description = "Web server control for" +
        " embedded H2 test database", log = false)
public class H2WebControlBean implements InitializingBean {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private DataSource dataSource;

    @Autowired
    private SchemaBuider schemaBuider;

    @Autowired
    @Value("${autospawn-h2-console:false}")
    private boolean autospawn;

    private AtomicBoolean started = new AtomicBoolean();

    private Server server;

    @ManagedOperation(description = "start")
    public void start() throws SQLException {
        if(started.compareAndSet(false, true)) {
            WebServer web = new WebServer();
            server = new Server(web);
            server.start();
            web.setShutdownHandler(server);
            String url = web.addSession(dataSource.getConnection());
            url = url.replace("?<=//)[^:/]+", "localhost");

            try {
                Server.openBrowser(url);
            } catch (Exception e) {
                log.warn("Failed to open browser on {}", url);
            }
        }
    }

    @ManagedOperation(description = "stop")
    public void stop() {
        if (started.compareAndSet(true, false))
            server.stop();
    }

    @Override
    public void afterPropertiesSet() throws SQLException {
        maybeAutostartConsole();
        start();
    }

    private void maybeAutostartConsole() throws SQLException {
        if (autospawn && amRunningInDebugger())
            start();
    }

    private boolean amRunningInDebugger() {
        return ManagementFactory.getRuntimeMXBean().getInputArguments().toString().indexOf("-agentlib:jdbw") > 0;
    }
}
