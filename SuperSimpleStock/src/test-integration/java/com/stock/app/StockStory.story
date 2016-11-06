Meta:

Scenario: scenario description
Given system date is set to 2016-09-11
When the following Price items exist in the system:
| symbol | exchange | marketPrice | currencyCode | systemDate |
| TEA | GBCE | 1.05 | EUR | 2016-09-11 |
| POP | GBCE | 1.20 | GBP | 2016-09-11 |
| ALE | GBCE | 1.10 | USD | 2016-09-11 |
| GIN | GBCE | 1.30 | EUR | 2016-09-11 |
| JOE | GBCE | 0.90 | USD | 2016-09-11 |
And the following FxRate items exist in the system:
| currencyFrom | currencyTo | rate | systemDate |
| GBP | GBP | 1.00000 | 2016-09-11 |
| EUR | GBP | 0.85000 | 2016-09-11 |
| USD | GBP | 0.75000 | 2016-09-11 |
And the following Stock items exist in the system:
| symbol | exchange | stockType | lastDividend | fixedDividend | parValue | currency |
| TEA | GBCE | COMMON | 0 | 0 | 1 | GBP |
| POP | GBCE | COMMON | 0.08 | 0 | 1 | GBP |
| ALE | GBCE | COMMON | 0.23 | 0 | 0.6 | GBP |
| GIN | GBCE | PREFERRED | 0.08 | 2 | 1 | GBP |
| JOE | GBCE | COMMON | 0.13 | 0 | 2.5 | GBP |
And the following Trade items exist in the system:
| quantity | buySell | tradePrice | tradeDate | settlementDate |
| 100 | BUY | 0.25 | 2016-09-11 | 2016-09-12 |
| 200 | BUY | 0.15 | 2016-09-11 | 2016-09-12 |
| 400 | SELL | 0.10 | 2016-09-11 | 2016-09-12 |


Then Dividend Yield for Stock with Symbol TEA and ExchangeCode GBCE for calculation date 2016-09-11 is 0.000000
And Dividend Yield for Stock with Symbol POP and ExchangeCode GBCE for calculation date 2016-09-11 is 0.066667
And Dividend Yield for Stock with Symbol GIN and ExchangeCode GBCE for calculation date 2016-09-11 is 1.809955
And PE Ratio for Stock with Symbol POP and ExchangeCode GBCE for calculation date 2016-09-11 is 15.000000
And PE Ratio for Stock with Symbol GIN and ExchangeCode GBCE for calculation date 2016-09-11 is 0.552500
And Stock price based on traddes booked in last 15 mins is 0.137143
And Geometric Mean of all stokcs for GBCE exchange on 2016-09-11 is 0.919987

