package utils;

import java.sql.*;


public class JDBCUtil {
    public static final String DRIVER = "com.mysql.jdbc.Driver";
    public static final String URL = "jdbc:mysql://112.74.59.107:3306/smartcontract?autoRec";
    public static final String USER_NAME = "smart";
    public static final String USER_PASS = "123456";
    public Connection conn;
    public PreparedStatement stat;
    public ResultSet rs;

    public  JDBCUtil()
    {
        getConnection();
    }

    public void getConnection()
    {
        try
        {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(URL, USER_NAME, USER_PASS);
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

    }

    public ResultSet executeQuery(String sql,Object... obj)
    {
        try
        {
            stat = conn.prepareStatement(sql);
            for(int i=0 ;i<obj.length;i++ )
            {
                stat.setObject(i+1, obj[i]);
            }
            rs = stat.executeQuery();

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return rs;
    }

    public int executeUpdate(String sql,Object... obj)
    {
        int num = 0;
        try
        {
            stat = conn.prepareStatement(sql);
            for(int i=0 ;i<obj.length;i++ )
            {
                stat.setObject(i+1, obj[i]);
            }
            num = stat.executeUpdate();

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return num;
    }
    public int executeUpdatePassword(String sql,String newpass,int id)
    {
        int num = 0;
        try
        {
            stat = conn.prepareStatement(sql);
            stat.setString(1, newpass);
            stat.setInt(2, id);
            num = stat.executeUpdate();

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return num;
    }

    public void close()
    {
        if (rs != null)
        {
            try
            {
                rs.close();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }

        if (stat != null)
        {
            try
            {
                stat.close();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }

        if (conn != null)
        {
            try
            {
                conn.close();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
    }
}
