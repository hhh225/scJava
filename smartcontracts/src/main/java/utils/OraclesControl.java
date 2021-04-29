package utils;

import Dao.Dao;
import domain.Oracle;

import java.util.HashMap;
import java.util.LinkedList;

public class OraclesControl {
    public static HashMap<String, Oracle> oracles = new HashMap<>();
    static {
        LinkedList<Oracle> oracles1=Dao.getOracle();
        for (Oracle oracle:oracles1)
        {
            oracles.put(oracle.address, oracle);
        }
    }
    public static Oracle findOracle(String addr)
    {
        addOracle(addr);
        return oracles.get(addr);

    }
    public static Boolean addOracle(String addr)
    {
        if (oracles.get(addr)==null)
        {
            oracles.put(addr, new Oracle(addr));
            return true;
        }
        return false;

    }
    public static Boolean deleteOracle(String addr)
    {
        if (oracles.get(addr)!=null)
        {
            oracles.remove(addr);
            return true;
        }
        return false;
    }
}
