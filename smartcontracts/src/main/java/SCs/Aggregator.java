package SCs;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.TreeMap;

public class Aggregator {
    public String owner;
    private Reputation reputation=new Reputation();
    private int numOfResponse;
    private int numOfRequestOracles;
    LinkedList<String> respondingOracles;
    private String user;
    HashMap<String,LinkedList<String>> oraclesToDevices;
    LinkedList<String> oracleList;

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
        for (int i=0;i<oracleList.size();i++)
        {
            for (int k=0;k<oraclesToDevices.get(oracleList.get(i)).size();k++)
            {
                if (iotDeviceAddr.equals(oraclesToDevices.get(oracleList.get(i)).get(k)))
                {
                    count++;
                    OracleSC oracleSC = new OracleSC(oracleList.get(i));
                    oracleSC.query(this);
                }
            }
        }
    }
}
