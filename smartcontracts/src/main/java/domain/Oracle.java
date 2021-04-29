package domain;

import java.util.LinkedList;

public class Oracle {
    public String address;
    //public double point;
    public Integer numResponse;
    public Integer avgReputation;

    public Oracle() {
    }

    public Oracle(String address) {
        this.address = address;
        //this.point = 0;
        this.numResponse = 0;
        this.avgReputation=0;
    }
}
