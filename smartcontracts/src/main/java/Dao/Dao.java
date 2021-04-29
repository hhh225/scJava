package Dao;

import utils.JDBCUtil;

import java.util.LinkedList;

public class Dao {
    JDBCUtil jdbcUtil=new JDBCUtil();
    public Boolean addDevice(String addr)
    {
        String sql="insert into device values(?)";
        int n=jdbcUtil.executeUpdate(sql, addr);
        if (n==1)
            return true;
        else return false;
    }
    public Boolean addUserDevice(String useraddr,String deviceaddr)
    {
        String sql="insert into userdevice(userid,deviceid) values (?,?)";
        int n = jdbcUtil.executeUpdate(sql, useraddr, deviceaddr);
        if (n==1)
            return true;
        else return false;
    }
    public Boolean addOracle(String oracleaddr, LinkedList<String> devices)
    {
        String sql="insert into oracle values (?)";
        int m = jdbcUtil.executeUpdate(sql, oracleaddr);
        if (m == 0) {
            return false;
        }
        sql="insert into oracledevice(oracleid,deviceid) values (?,?)";
        int n;
        for (String device:devices)
        {
            n = jdbcUtil.executeUpdate(sql, oracleaddr, device);
            if (n == 0) {
                return false;
            }
        }
        return true;
    }
}
