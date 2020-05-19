package com.taiweb3j.response.staking;

import java.util.List;

public class ImpawnUnit {

    public String address;

    public List<PairstakingValue> value;

    public List<RedeemItem> redeemInfo;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<PairstakingValue> getValue() {
        return value;
    }

    public void setValue(List<PairstakingValue> value) {
        this.value = value;
    }

    public List<RedeemItem> getRedeemInfo() {
        return redeemInfo;
    }

    public void setRedeemInfo(List<RedeemItem> redeemInfo) {
        this.redeemInfo = redeemInfo;
    }


    @Override
    public String toString() {
        return "ImpawnUnit{" +
                "address='" + address + '\'' +
                ", value=" + value +
                ", redeemInfo=" + redeemInfo +
                '}';
    }
}
