package SCs;


import domain.Token;

import java.util.HashMap;
import java.util.LinkedList;

public class AccessControl {
    int count=0;
    static String owneraddress;
    String aggreagtorSCaddress;
    static LinkedList<String> admins=new LinkedList<>();
    public LinkedList<Token> tokens;
    static public LinkedList<String> devices=new LinkedList<>();
    static HashMap<String,LinkedList<String>> users_devices=new HashMap<>();
    Aggregator aggregator;

    public AccessControl()
    {
        this.aggregator=new Aggregator();
    }

    public static Boolean AccessConstruct(String addr)
    {
        if (!(owneraddress.equals("")||owneraddress==null))
        {
            return false;
        }
        owneraddress=addr;
        return true;
    }


    static public Boolean addDevice(String addr)
    {
        if (!devices.contains(addr))
        {
            devices.add(addr);

            return true;

        }
        return false;
    }

    public static Boolean addAdmin(String addr)
    {
        if (!admins.contains(addr)) {
            admins.add(addr);
            return true;
        }
        return false;
    }

    public static Boolean addUserDevicesMapping(String userAddr,String deviceAddr)
    {
        LinkedList<String> uds=users_devices.get(userAddr);
        if (uds==null)
        {
            uds=new LinkedList<>();
            uds.add(deviceAddr);
            users_devices.put(userAddr, uds);
            return true;
        }
        else {
            if (!uds.contains(deviceAddr))
            {
                uds.add(deviceAddr);
                return true;
            }
            return false;
        }
    }

    public static String requestUserToAccessDevice(String userAddr,String deviceAddr,int nOracles)
    {
        System.out.println("AccessControl request:"+userAddr);
        System.out.println("AccessControl request:"+deviceAddr);
        System.out.println("AccessControl request:"+nOracles);

        boolean deviceExist=false;
        for (String addr:devices)
        {
            if (addr.equals(deviceAddr))
            {
                deviceExist=true;
                break;
            }
        }
        if (!deviceExist)
        {
            return "设备不存在";
        }
        else {
            boolean auth=false;
            LinkedList<String> temps= users_devices.get(userAddr);  //temps代表该用户能访问的设备
            for (String addr:temps)
            {
                if (addr.equals(deviceAddr))
                {
                    auth=true;
                    break;
                }
            }
            if (auth)
            {
                Aggregator agg=new Aggregator();
                String ret=agg.sendDataRequest(userAddr,deviceAddr,nOracles);
                return ret;
            }
            else {
                return "该用户不能访问该设备";
            }
        }
        //
       // return "";
    }



}
