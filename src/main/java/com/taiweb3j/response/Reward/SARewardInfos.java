package com.taiweb3j.response.Reward;

import java.util.List;

public class SARewardInfos {
    public List<RewardInfo> Items;

    public List<RewardInfo> getItems() {
        return Items;
    }

    public void setItems(List<RewardInfo> items) {
        Items = items;
    }

    @Override
    public String toString() {
        return "SARewardInfos{" +
                "Items=" + Items +
                '}';
    }
}
