package com.taiweb3j.response.staking;

public class RedeemItem {
    String amount;

    String epochID;

    String state;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getEpochID() {
        return epochID;
    }

    public void setEpochID(String epochID) {
        this.epochID = epochID;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "RedeemItem{" +
                "amount='" + amount + '\'' +
                ", epochID='" + epochID + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}
