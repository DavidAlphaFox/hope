package hope.analyzer.job.policy28;

import hope.analyzer.model.KLineInfo;
import hope.analyzer.util.BusinessException;

/**
 * Created by bing.a.qian on 10/2/2017.
 */
public class TwoEightJobBase500 extends BaseTwoEightJob{

    protected void operate(KLineInfo current300, double week4Range300, KLineInfo current500, double week4Range500) throws BusinessException {
        if(is500Position()) {
            if(week4Range500>0){
                return;
            }else{
                account.clear(INDEX_500_CODE,current500.getClose(),current500.getDate());
            }
        }else{
            if(week4Range500>0){
                account.allIn(INDEX_500_CODE,current500.getClose(),current500.getDate());
            }
        }
    }
}
