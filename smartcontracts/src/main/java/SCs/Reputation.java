package SCs;

import domain.Oracle;
import utils.OraclesControl;

import java.util.LinkedList;

public class Reputation {


    /**
     *
     * @param oracleList: 代表有回复的预言机的地址
     * @param points: 代表有回复的预言机这次所得的分数
     * @return
     */
    String reportReputationScore(LinkedList<String> oracleList,LinkedList<Integer> points)
    {
        int i=0;
        int maxAvg=-1;
        String maxAddress=null;
        for (String oracle:oracleList)
        {
            Oracle temp = OraclesControl.findOracle(oracle);
            int c=temp.numResponse;
            int avg=temp.avgReputation;
            temp.avgReputation=(c*avg+points.get(i))/(c+1);
            temp.numResponse++;
            if (temp.avgReputation>maxAvg&&points.get(i)==100)
            {
                maxAvg= temp.avgReputation;
                maxAddress= temp.address;
            }
            i++;
        }
        return maxAddress;
    }
}
