package com.taiweb3j.response.staking;

public class PairstakingValue {
    public String amount;

    public String height;

    public String state;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "PairstakingValue{" +
                "amount='" + amount + '\'' +
                ", height='" + height + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}
