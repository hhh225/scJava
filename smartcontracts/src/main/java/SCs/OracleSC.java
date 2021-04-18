package SCs;

import SCs.domain.QueryEnitty;
import SCs.domain.RespondEntity;

public class OracleSC {
    String oracleAddr;
    Aggregator aggregator;
    public OracleSC(String oracleAddr)
    {
        this.oracleAddr=oracleAddr;
    }

    public void query(QueryEnitty queryEnitty,String iotdeviceAddr, Aggregator aggregator)
    {
        this.aggregator=aggregator;

    }

    public void reply(){
        RespondEntity respondEntity=new RespondEntity();
        aggregator.oracleResponse(respondEntity);
    }
}
