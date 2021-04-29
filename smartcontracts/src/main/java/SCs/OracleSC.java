package SCs;

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
        //访问预言机
        //得到数据
        String data="fake data";
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
