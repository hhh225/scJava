package interfaces;

import SCs.AccessControl;
import SCs.Aggregator;

import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;

public class Connection implements Runnable{

    static DatagramSocket sock;

    DatagramPacket pack;

    public Connection(DatagramPacket pack) {
        this.pack = pack;
    }

    public Connection() {
    }

    static {
        try {
            sock = new DatagramSocket(2465, InetAddress.getByName("localhost"));
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        byte[] bdata=pack.getData();      //获取到的数据体
        try {
            DatagramSocket retsSock =new DatagramSocket();
            String data= null;
            data = new String(bdata,0, pack.getLength(),"utf-8");  //transfer to string
            String[] datas=data.split("/");       //split the data with :
            switch (datas[0]){
                case "AccessConstruct": // 保存
                    Boolean res=AccessControl.AccessConstruct(datas[1]);
                    String retMe="合约已构建，不能重复构建";
                    if (res){
                        retMe="成功构建";
                    }
                    DatagramPacket retpack=new DatagramPacket(retMe.getBytes(StandardCharsets.UTF_8),retMe.getBytes().length,pack.getAddress(), pack.getPort());
                    retsSock.send(retpack);
                    break;
                case "AggregatorConstruct":
                    res = Aggregator.AggregatorConstruct(datas[1]);
                    retMe = "合约已构建，不能重复构建";
                    if (res){
                        retMe="成功构建";
                    }
                    retpack = new DatagramPacket(retMe.getBytes(StandardCharsets.UTF_8), retMe.getBytes().length, pack.getAddress(), pack.getPort());
                    retsSock.send(retpack);
                    break;
                case "AddDevice":      //设备名
                    res=AccessControl.addDevice(datas[1]);
                    retMe = "设备已存在";
                    if (res){
                        retMe="成功添加";
                    }
                    retpack = new DatagramPacket(retMe.getBytes(StandardCharsets.UTF_8), retMe.getBytes().length, pack.getAddress(), pack.getPort());
                    retsSock.send(retpack);
                    break;
                case "AddAdmin":
                    res=AccessControl.addAdmin(datas[1]);
                    retMe = "管理员已存在";
                    if (res){
                        retMe="成功添加";
                    }
                    retpack = new DatagramPacket(retMe.getBytes(StandardCharsets.UTF_8), retMe.getBytes().length, pack.getAddress(), pack.getPort());
                    retsSock.send(retpack);
                    break;
                case "AddUserDevice":
                    res=AccessControl.addUserDevicesMapping(datas[1],datas[2]);
                    retMe = "该用户已有此设备";
                    if (res){
                        retMe="成功添加";
                    }
                    retpack = new DatagramPacket(retMe.getBytes(StandardCharsets.UTF_8), retMe.getBytes().length, pack.getAddress(), pack.getPort());
                    retsSock.send(retpack);
                    break;

                case "AddOracle":      //IP地址加端口号
                    LinkedList<String> tempdevices=new LinkedList<>();    //该预言机所能访问的设备列表
                    for (int i=0;i<datas.length-2;i++)
                    {
                        tempdevices.add(datas[i+2]);
                    }
                    System.out.println(tempdevices);
                    res=Aggregator.addOracle(datas[1],tempdevices);      //增加预言机操作
                    retMe = "预言机已存在";
                    if (res){
                        retMe="成功添加";
                    }
                    retpack = new DatagramPacket(retMe.getBytes(StandardCharsets.UTF_8), retMe.getBytes().length, pack.getAddress(), pack.getPort());
                    retsSock.send(retpack);
                    break;
                case "RequestUserToAccessDevices":      //要先addDevice，再 adduserdevice，再addOracle,最后才能request
                    AccessControl accessControl=new AccessControl();
                    retMe = AccessControl.requestUserToAccessDevice(datas[1], datas[2], Integer.parseInt(datas[3]));
                    retpack=new DatagramPacket(retMe.getBytes(StandardCharsets.UTF_8), retMe.getBytes().length, pack.getAddress(), pack.getPort());
                    retsSock.send(retpack);
                    break;
                case "DelAdmin":
                    //return DelAdmin(args[0])
                case "DelUser":
                    //return DelUser(args[0])
            }
        } catch (IOException e) {
            e.printStackTrace();
        }



    }

    public static void main(String[] args) {
        while (true)
        {
            DatagramPacket pack=new DatagramPacket(new byte[2048],2048); //获取数据，
            try {
                sock.receive(pack);
                Thread req=new Thread(new Connection(pack));
                req.start();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
