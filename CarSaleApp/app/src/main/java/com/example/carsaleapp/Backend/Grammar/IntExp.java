package com.example.carsaleapp.Backend.Grammar;

public class IntExp {

    // @author: Preetish Thirumalai u7157098
    // the 'lessthan' Boolean parameter is whether the query is asking
    // for less or greater than. true means less than, false means greater than.
    // the 'value' parameter is the price in the query
    private final Integer value;
    private final boolean lessThan; //true if less than, false if greater than
    public IntExp(Integer value, boolean b) {
        this.value = value;
        this.lessThan = b;
    }
    public boolean lessOrGreater() {
        return this.lessThan;
    }
    public Integer getValue() {
        return this.value;
    }
}
