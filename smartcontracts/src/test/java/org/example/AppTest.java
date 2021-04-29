package org.example;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import utils.JDBCUtil;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        final String str="12345";
        Thread thread=new Thread(new RunnableImpl(this));
        thread.start();
        print();
    }

    @Test
    public void databasetest()
    {
        JDBCUtil jdbcUtil=new JDBCUtil();
        String sql="insert into device values (?)";
        String params1 = "de1";
        int res=jdbcUtil.executeUpdate(sql, params1);
        System.out.println(res);
    }

    public void print()
    {
        for (int i=0;i<50;i++)
        {
            System.out.println(Thread.currentThread().getId());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}

class RunnableImpl implements Runnable{

    AppTest appTest;

    public RunnableImpl(AppTest appTest) {
        this.appTest = appTest;
    }

    @Override
    public void run() {
        appTest.print();
    }
}