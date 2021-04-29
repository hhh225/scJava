package SCs;


import Dao.Dao;
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
    static {
        devices= Dao.getDevice();
    }
    static HashMap<String,LinkedList<String>> users_devices=new HashMap<>();
    static {
        users_devices=Dao.getUserDevice();
    }
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


    public static Boolean addDevice(String addr)
    {
        Dao dao=new Dao();
        if (dao.addDevice(addr))
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
        Dao dao=new Dao();
        if (uds==null)
        {
            dao.addUserDevice(userAddr, deviceAddr);
            uds=new LinkedList<>();
            uds.add(deviceAddr);
            users_devices.put(userAddr, uds);
            return true;
        }
        else {
            if (!uds.contains(deviceAddr))
            {
                dao.addUserDevice(userAddr, deviceAddr);
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
        /**
         * 检查设备是否存在
         */
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
            /**
             * 检查用户是否能访问这个设备
             */
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
            /**
             * 用户能访问这个设备
             */
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
