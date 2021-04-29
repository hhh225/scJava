package SCs;

import Dao.Dao;
import domain.Oracle;
import utils.OraclesControl;

import java.util.HashMap;
import java.util.LinkedList;

public class Reputation {
    /**
     *
     *
     * @param oracleList：all oracles that have result
     * @param hashes：all ressult
     * @return
     */
    public String reportReputationScore( LinkedList<String> oracleList,LinkedList<String> hashes,int numOfRequestOracles)
    {
        HashMap<String, LinkedList<String>> duplicateOracles = new HashMap<>();
        System.out.println("nMatches:"+numOfRequestOracles);
        System.out.println("hashes:"+hashes);
        int nMatches=numOfRequestOracles/2;
        String correctHash;
        HashMap<String, Integer> aggrement = new HashMap<>();
        LinkedList<String> trueOracles = new LinkedList<>();
        LinkedList<Integer> points = new LinkedList<>();
        int i=0;
        String selectOracle;

        /**
         * 找出每个回复有多少个预言机
         * aggrement存放这个回复的次数
         * duplicateOracles代表这个回复的预言机列表
         */
        for (String hash:hashes)
        {
            if (aggrement.get(hash)!=null)
            {
                aggrement.put(hash,aggrement.get(hash)+1);
                LinkedList candidateOracles=duplicateOracles.get(hash);
                candidateOracles.add(oracleList.get(i));
                duplicateOracles.put(hash,candidateOracles);
            }
            else {
                aggrement.put(hash, 1);
                LinkedList<String> candidateOracles = new LinkedList<>();
                candidateOracles.add(oracleList.get(i));
                duplicateOracles.put(hash, candidateOracles);
            }
            i++;
        }

        /**
         * 找出返回大于一半的回复，将这个回复称为correctHash
         * 将这个回复的所有预言机放到trueOracles中
         * aggrement代表这个回复的次数
         * duplicateOracles代表这个回复的预言机列表
         * trueOracles代表correctHash的Oracle列表
         */
        for (i=0;i<hashes.size();i++)
        {
            if (aggrement.get(hashes.get(i))>=nMatches)        //如果这个答案的个数大于一半
            {
                correctHash = hashes.get(i);
                trueOracles.addAll(duplicateOracles.get(correctHash));
                break;
            }


        }

        /**
         * 对于每个有回复的oracle，如果它等于某个trueOracle，那么这个Oracle这次得100分，否则得0分
         *
         */
        for (i=0;i<hashes.size();i++) {
            for (String trueOracle : trueOracles) {
                if (oracleList.get(i).equals(trueOracle)) {
                    points.add(100);

                } else {
                    points.add(0);

                }
            }

        }
        selectOracle = reportReputationScore(oracleList, points);
        return selectOracle;
    }

    /**
     *统计所有有返回结果的预言机低分
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
            int c=temp.numResponse;     //这个预言机以前回复的总次数
            int avg=temp.avgReputation; //这个预言机的平均信誉值
            temp.avgReputation=(c*avg+points.get(i))/(c+1);//预言机新的平均信誉值
            temp.numResponse++;         //预言机新的总次数
            Dao dao=new Dao();
            dao.updateOracle(oracle, temp.numResponse, temp.avgReputation);//更新预言机在数据库中的总次数和平均信誉值
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
