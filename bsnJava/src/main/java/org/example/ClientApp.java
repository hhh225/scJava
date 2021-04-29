package org.example;

import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class ClientApp
{
    public static void main( String[] args )
    {
        try {
            Scanner scanner = new Scanner(System.in);
            String input=scanner.nextLine();
            LinkedList<String> params = new LinkedList<>();
            while (!input.equals("0")){
                params.add(input);
                input=scanner.nextLine();
            }
            String param = "";
            for (String temp:params)
            {
                param=param+temp+"/";
            }
            param=param.substring(0,param.length()-1);
            DatagramSocket sock = new DatagramSocket();
            //String s = "hello";
            DatagramPacket pack=new DatagramPacket(param.getBytes(StandardCharsets.UTF_8),param.getBytes().length, InetAddress.getByName("localhost"),2465);
            sock.send(pack);
            //以下部分接收从服务器返回的
            pack=new DatagramPacket(new byte[2048],2048);
            sock.receive(pack);
            System.out.println("receive:"+pack.getAddress());
            System.out.println("message:"+new String(pack.getData(),0, pack.getLength()));
            sock.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
