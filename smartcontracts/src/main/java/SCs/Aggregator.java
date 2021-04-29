package SCs;

import utils.OraclesControl;

import java.util.HashMap;
import java.util.LinkedList;

public class Aggregator {
    static  public String owneraddress;
    private Reputation reputation=new Reputation();
    private int numOfResponse=0;
    private int numOfRequestOracles;
    LinkedList<String> respondingOracles=new LinkedList<>();
    private String user;
    static HashMap<String,LinkedList<String>> oraclesToDevices=new HashMap<>();
    static LinkedList<String> oracleList=new LinkedList<>();
    LinkedList<String> hashes=new LinkedList<>();
    HashMap<String, LinkedList<String>> duplicateOracles = new HashMap<>();

    public Aggregator()
    {
        this.reputation=new Reputation();
    }

    public static Boolean AggregatorConstruct(String addr)
    {
        if (!(owneraddress.equals("")||owneraddress==null))
        {
            return false;
        }
        owneraddress=addr;
        return true;
    }

    public static Boolean addOracle(String oracleAddr,LinkedList<String> devicesAddrs)
    {
        if (oracleList.contains(oracleAddr)){
            return false;
        }
        oracleList.add(oracleAddr);
        oraclesToDevices.put(oracleAddr, devicesAddrs);
        OraclesControl.addOracle(oracleAddr);
        return true;
    }

    public String sendDataRequest(String userAddr,String iotDeviceAddr, int nOracles)
    {
        System.out.println("Agg sendreq:"+userAddr);
        System.out.println("Agg sendreq:"+iotDeviceAddr);
        System.out.println("Agg sendreq:"+nOracles);
        int count=0;
        for (String oracle:oracleList)      //对于所有预言机
        {
            LinkedList<String> tempdevices=oraclesToDevices.get(oracle);   //该预言机能访问的所有device
            for (String device:tempdevices)   //对于这个预言机能访问的所有物联物联网服务
            {
                if (iotDeviceAddr.equals(device))
                {
                    count++;
                    OracleSC oracleSC = new OracleSC(oracle,this,iotDeviceAddr);
                    String resHash=oracleSC.query();
                    hashes.add(resHash);
                    respondingOracles.add(oracle);
                    break;
                }
            }
            if (count == nOracles) {
                break;
            }
        }
        String ret=reportReputationScore(respondingOracles, hashes);
        return ret;

    }

    /**
     * 每当有预言机要返回结果时，就调用这个函数
     * respondingOracles代表有回复的预言机的地址
     *
     * @return
     */



    /**
     *
     *
     * @param oracleList：代表了所有返回了回复的预言机
     * @param hashes：代表所有的回复
     * @return
     */
    public String reportReputationScore( LinkedList<String> oracleList,LinkedList<String> hashes)
    {
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
        selectOracle = reputation.reportReputationScore(oracleList, points);
        return selectOracle;
    }
}
