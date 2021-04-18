package SCs;

import SCs.domain.QueryEnitty;
import SCs.domain.RespondEntity;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.TreeMap;

public class Aggregator {
    public String owner;
    private Reputation reputation=new Reputation();
    private int numOfResponse=0;
    private int numOfRequestOracles;
    LinkedList<String> respondingOracles;
    private String user;
    HashMap<String,LinkedList<String>> oraclesToDevices;
    LinkedList<String> oracleList;
    LinkedList<String> hashes=new LinkedList<>();
    HashMap<String, LinkedList<String>> duplicateOracles = new HashMap<>();

    public Aggregator(String addr, Reputation reputation)
    {
        owner=addr;
        this.reputation=reputation;
    }

    public void addOracle(String oracleAddr,LinkedList<String> devicesAddrs)
    {
        oracleList.add(oracleAddr);
        oraclesToDevices.put(oracleAddr, devicesAddrs);
    }

    public void sendDataRequest(String userAddr,String iotDeviceAddr, int nOracles)
    {
        int count=0;
        for (int i=0;i<oracleList.size();i++)      //对于所有预言机
        {
            for (int k=0;k<oraclesToDevices.get(oracleList.get(i)).size();k++)   //对于这个预言机能访问的所有物联物联网服务
            {
                if (iotDeviceAddr.equals(oraclesToDevices.get(oracleList.get(i)).get(k)))
                {
                    count++;
                    OracleSC oracleSC = new OracleSC(oracleList.get(i));
                    QueryEnitty queryEnitty=new QueryEnitty();
                    oracleSC.query(new QueryEnitty(),iotDeviceAddr,this);
                    if (count==nOracles)
                    {
                        break;
                    }
                }
            }
        }
    }

    public String oracleResponse(RespondEntity respondEntity){
        numOfResponse++;
        respondingOracles.add(respondEntity.getOracleAddr());
        hashes.add(respondEntity.getData());  //未完成：需要把data转换成哈希值
        if (numOfResponse==numOfRequestOracles)
        {

        }
        return null;
    }

    public String reportReputationScore(Reputation reputationSC, LinkedList<String> oracleList,LinkedList<String> hashes)
    {
        int nMatches=numOfRequestOracles/2;
        String correctHash;
        HashMap<String, Integer> aggrement = new HashMap<>();


       for (String hash:hashes)
       {
           if (aggrement.get(hash)!=null)
           {
               aggrement.put(hash,aggrement.get(hash)+1);
               duplicateOracles.get(hash).add("");
           }
           else {
               aggrement.put(hash, 1);
           }
       }
        return null;
    }
}
