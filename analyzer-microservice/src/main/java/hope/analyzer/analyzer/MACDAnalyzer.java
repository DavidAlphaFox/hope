package hope.analyzer.analyzer;

import hope.analyzer.model.KLineInfo;
import hope.analyzer.model.Macd;
import hope.analyzer.model.ResultInfo;
import hope.analyzer.model.Stock;

import java.util.LinkedList;
import java.util.List;

public class MACDAnalyzer extends AbstractStockAnalyzer {
    private static float TURNOVER_RATE_THRESHHOLD = 1.0f ;
    private static int SHORT = 12;
    private static int LONG = 26;
    private static int M = 9;
    // consider how many days before now.
    int daysToNow = 10;

    public MACDAnalyzer() {
    }

    public boolean analyze(ResultInfo resultInfo, Stock stock) {
        boolean ok = false;
        List<KLineInfo> infos = stock.getkLineInfos();
        if (infos.size() <= daysToNow) {
            // 元数据天数要求大于考察天数
            return ok;
        }


        KLineInfo today = infos.get(infos.size() - 1);
        KLineInfo yesterday = infos.get(infos.size() - 2);

        double macdToday=today.getMacd();
        double macdYesterday = yesterday.getMacd();
        double latestTurnoverRate=today.getTurnoverRate();

        double todayMACDThreshhold=0.1;
        if(stock.getkLineType().equals("weekly")){
            todayMACDThreshhold=0.12;
        }else if(stock.getkLineType().equals("monthly")){
            todayMACDThreshhold=0.12;
        }

        if (macdToday>0&&macdToday<=todayMACDThreshhold&&macdYesterday<=macdToday&& latestTurnoverRate>=TURNOVER_RATE_THRESHHOLD) {
            ok = true;
        }
        return ok;
    }

    public String format(Stock stock) {
        StringBuilder sb = new StringBuilder();
        sb.append(stock.getCode()).append("  ").append(stock.getName())
                .append("\n");
        return (sb.toString());
    }

    public String getDescription() {
        return "MACD 介于0和0.1之间，且大于前一天的值，换手率大于"+TURNOVER_RATE_THRESHHOLD+"%";
    }

    public int getDaysToNow() {
        return daysToNow;
    }

    public void setDaysToNow(int daysToNow) {
        this.daysToNow = daysToNow;
    }


}
