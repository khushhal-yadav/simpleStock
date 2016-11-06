package com.stock.common;

import com.stock.config.StockApplicationContextLoader;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.failures.FailingUponPendingStep;
import org.jbehave.core.io.CasePreservingResolver;
import org.jbehave.core.io.CodeLocations;
import org.jbehave.core.junit.JUnitStory;
import org.jbehave.core.reporters.Format;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.InstanceStepsFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestContextManager;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

/**
 * Created by khush on 06/11/2016.
 */
@ContextConfiguration(loader = StockApplicationContextLoader.class)
public class AppStory<T> extends JUnitStory implements InitializingBean {

    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private static final String JC_CLASSES = "../build/jc/classes";
    private static final String FTL_PATH_PREFIX = "resources/stockapp_ftl/";

    private static final Properties VIEW_RESOURCES = new Properties();

    static {
        //TODO: to configure all the freemarker views for jBeahve stories
        VIEW_RESOURCES.put("decorated", FTL_PATH_PREFIX + "jbehave-report-decorated.ftl");
        VIEW_RESOURCES.put("maps", FTL_PATH_PREFIX + "jbehave-maps.ftl");
        VIEW_RESOURCES.put("decorateNonHtml", FTL_PATH_PREFIX + "true");
    }

    private TestContextManager testContextManager;

    @Autowired
    private ApplicationContext applicationContext;

    private Class<?> stepClass;

    private Object[] steps;

    protected AppStory() {
        Type type = getClass().getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            Type parameterrizedType = ((ParameterizedType)type).getActualTypeArguments()[0];
            if (parameterrizedType instanceof Class) stepClass = (Class<T>)parameterrizedType;
        }
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        if (stepClass != null) {
            setSteps(applicationContext.getBeansOfType(stepClass).values().stream()
                    .filter(bean -> bean.getClass() == stepClass)
                    .findAny().get());
        }
    }

    public void setSteps(Object... steps) {this.steps = steps;}

    @Override
    public final Configuration configuration() {
        this.testContextManager = new TestContextManager(getClass());
        try {
            this.testContextManager.prepareTestInstance(this);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return new MostUsefulConfiguration()
                .useStoryReporterBuilder(
                        new StoryReporterBuilder()
                                .withCodeLocation(CodeLocations.codeLocationFromPath(JC_CLASSES))
                                .withViewResources(VIEW_RESOURCES)
                                .withFormats(Format.CONSOLE, Format.HTML,
                                        Format.STATS)
                                .withFailureTraceCompression(true)
                                .withFailureTrace(true)
                                .withMultiThreading(false)
                ).usePendingStepStrategy(new FailingUponPendingStep())
                .useStoryPathResolver(new CasePreservingResolver());
    }

    @Override
    public InjectableStepsFactory stepsFactory() {return  new InstanceStepsFactory(configuration(), steps);}
}
