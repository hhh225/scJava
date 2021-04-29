package SCs;

import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class OracleSC implements Runnable{
    String oracleAddr;
    Aggregator aggregator;
    String deviceAddr;

    public OracleSC(String oracleAddr,Aggregator aggregator,String deviceAddr)
    {
        this.oracleAddr=oracleAddr;
        this.aggregator=aggregator;
        this.deviceAddr=deviceAddr;
    }

    public String query(){
        String data="fake data";
        try {
            DatagramSocket sock=new DatagramSocket();
            String[] oracleAddrs=oracleAddr.split(":");
            Integer port = Integer.parseInt(oracleAddrs[1]);
            DatagramPacket pack = new DatagramPacket(deviceAddr.getBytes(StandardCharsets.UTF_8),deviceAddr.getBytes().length, InetAddress.getByName(oracleAddrs[0]),port);
            /**
             * 以下接收返回数据
             * 新声明一个pack用来接收返回的数据
             */
            pack=new DatagramPacket(new byte[2048],2048);
            sock.receive(pack);
            byte[] bdata = pack.getData();
            if (bdata.length>0)
            {
                data = new String(bdata, 0, pack.getLength(), "utf-8");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //得到数据

        return data;
    }


    @Override
    public void run() {
        //发送请求
        //结果
        String data="fake data";
        //aggregator.oracleResponse(oracleAddr,data);
    }
}
