package Dao;

import domain.Oracle;
import utils.JDBCUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;

public class Dao {
    static JDBCUtil jdbcUtil=new JDBCUtil();
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
        String sql="insert into oracle values (?,0,0)";
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

    public Boolean updateOracle(String oracleAddr,int nres,int avg)
    {
        String sql="update oracle set nresponse=? where id=?";
        String sql2="update oracle set avg=? where id=?";
        int n1 = jdbcUtil.executeUpdate(sql, nres,oracleAddr);
        int n2 = jdbcUtil.executeUpdate(sql, avg,oracleAddr);
        if (n1==1&&n2==1)
        {
            return true;
        }
        else return false;
    }

    public static LinkedList<String> getDevice(){
        String sql="select * from device";
        ResultSet rs = jdbcUtil.executeQuery(sql);
        LinkedList<String> result = new LinkedList<>();
        try {
            while (rs.next())
            {
                result.add(rs.getString("id"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }
    public static LinkedList<Oracle> getOracle(){
        String sql="select * from oracle";
        ResultSet rs = jdbcUtil.executeQuery(sql);
        LinkedList<Oracle> result = new LinkedList<>();
        try {
            while (rs.next())
            {
                result.add(new Oracle(rs.getString("id"),rs.getInt("nresponse"),rs.getInt("avg")));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }


    public static HashMap<String,LinkedList<String>> getUserDevice()
    {
        String sql="select userid,deviceid from userdevice";
        ResultSet rs = jdbcUtil.executeQuery(sql);
        HashMap<String, LinkedList<String>> result = new HashMap<>();
        try {
            while (rs.next())
            {
                String user = rs.getString("userid");
                String device = rs.getString("deviceid");
                if (result.get(user)==null)
                {
                    LinkedList<String> devices = new LinkedList<>();
                    devices.add(device);
                    result.put(user, devices);
                }
                else {
                    LinkedList<String> devices = result.get(user);
                    devices.add(device);

                    result.put(user, devices);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }

    public static HashMap<String,LinkedList<String>> getOracleDevice()
    {
        String sql="select oracleid,deviceid from oracledevice";
        ResultSet rs = jdbcUtil.executeQuery(sql);
        HashMap<String, LinkedList<String>> result = new HashMap<>();
        try {
            while (rs.next())
            {
                String user = rs.getString("oracleid");
                String device = rs.getString("deviceid");
                if (result.get(user)==null)
                {
                    LinkedList<String> devices = new LinkedList<>();
                    devices.add(device);
                    result.put(user, devices);
                }
                else {
                    LinkedList<String> devices = result.get(user);
                    devices.add(device);

                    result.put(user, devices);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }


}
