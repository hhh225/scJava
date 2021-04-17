package SCs;

public class OracleSC {
    String oracleAddr;
    Aggregator aggregator;
    public OracleSC(String oracleAddr)
    {
        this.oracleAddr=oracleAddr;
    }

    public void query(Aggregator aggregator)
    {
        this.aggregator=aggregator;

    }
}
