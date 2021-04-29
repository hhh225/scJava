//package org.example;

import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class ServerApp {
    public static void main(String[] args) {
        try {
            DatagramSocket sock=new DatagramSocket(2465, InetAddress.getByName("172.17.7.84"));
            DatagramPacket pack = new DatagramPacket(new byte[2048], 2048);
            sock.receive(pack);
            byte[] bdata=pack.getData();
            String s=new String(bdata,0,pack.getLength(),"utf-8");
            System.out.println(pack.getAddress());
            System.out.println(pack.getPort());
            System.out.println(s);
            pack=new DatagramPacket("hey".getBytes(StandardCharsets.UTF_8),"hey".getBytes().length,pack.getAddress(),pack.getPort());
            sock.send(pack);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
