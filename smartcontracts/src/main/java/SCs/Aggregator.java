package SCs;

import Dao.Dao;
import domain.Oracle;
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
    static {
        oraclesToDevices= Dao.getOracleDevice();
    }
    static LinkedList<String> oracleList=new LinkedList<>();
    static {
        LinkedList<Oracle> oracles=Dao.getOracle();
        for (Oracle oracle:oracles)
        {
            oracleList.add(oracle.address);
        }
    }
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
        Dao dao=new Dao();
        dao.addOracle(oracleAddr, devicesAddrs);
        return true;
    }

    public String sendDataRequest(String userAddr,String iotDeviceAddr, int nOracles)
    {
        System.out.println("Agg sendreq:"+userAddr);
        System.out.println("Agg sendreq:"+iotDeviceAddr);
        System.out.println("Agg sendreq:"+nOracles);
        int count=0;
        /**
         * 将请求发送给所有能访问该设备的预言机
         */
        for (String oracle:oracleList)      //for all oracles
        {
            LinkedList<String> tempdevices=oraclesToDevices.get(oracle);   //the list of devices the oracle can access
            for (String device:tempdevices)   //for all devices the oracle can access
            {
                if (iotDeviceAddr.equals(device))
                {
                    count++;
                    OracleSC oracleSC = new OracleSC(oracle,this,iotDeviceAddr);
                    String resHash=oracleSC.query();
                    if (!resHash.equals("fake data"))   //if the result is meaningful
                    {
                        hashes.add(resHash);            //resultdata
                        respondingOracles.add(oracle);  //responseOracle
                    }

                    break;
                }
            }
            if (count == nOracles) {
                break;
            }
        }
        String ret=reputation.reportReputationScore(respondingOracles, hashes,numOfRequestOracles);
        return ret;

    }
}
