package SCs;


import SCs.domain.Token;

import java.util.HashMap;
import java.util.LinkedList;

public class AccessControl {
    int count=0;
    String owneraddress;
    String aggreagtorSCaddress;
    LinkedList<String> admins=new LinkedList<>();
    public LinkedList<Token> tokens;
    public LinkedList<String> devices;
    HashMap<String,LinkedList<String>> users_devices;
    Aggregator aggregator;

    public AccessControl(String addr, Aggregator aggregator)
    {
        admins.add(addr);
        owneraddress=new String(addr);
        this.aggregator = aggregator;
    }


    public static void main(String[] args) {

    }

}
