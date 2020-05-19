package com.taiweb3j.sample;

import com.taiweb3j.response.Reward.ChainRewardContent;
import com.taiweb3j.response.fast.FastBlock;
import com.taiweb3j.response.snail.SnailRewardContenet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.util.Map;

public class RewardUsage extends TaiWeb3jTestNet {
    private static final Logger logger = LoggerFactory.getLogger(RewardUsage.class);

    /**
     * get snail reward address and the balance of the address
     */
    public void getSnailBalanceChange() {
        BigInteger snailNumber = new BigInteger("2");
        Map<String, String> addrWithBalance = taiWeb3JRequest.getSnailBalanceChange(snailNumber);
        logger.info("addrWithBalance=[{}]", addrWithBalance);
    }


    /**
     * get snailReward content  by snailNumber
     * call etrue_getChainRewardContent by empty address
     */
    public void getSnailRewardContent() {
        BigInteger snailNumber = new BigInteger("55000");
        ChainRewardContent snailChainRewardContent = taiWeb3JRequest.getSnailRewardContent(snailNumber);
        System.out.println("snailChainRewardContent=" + snailChainRewardContent.toString());
    }

    public void getSnailRewardContent_Old() {
        BigInteger snailNumber = new BigInteger("55000");
        SnailRewardContenet snailRewardContenet = taiWeb3JRequest.getSnailRewardContent_Old(snailNumber);
        System.out.println("snailRewardContenet=" + snailRewardContenet.toString());
    }


    public void getAddressesSnailReward() {
        BigInteger snailNumber = new BigInteger("2");
        Map<String, BigInteger> addressSnailReward = taiWeb3JRequest.getAddressesSnailReward(snailNumber);
        logger.info("addressSnailReward=[{}]", addressSnailReward);
    }

    public void getFastBockOfReward() {
        BigInteger snailNumber = new BigInteger("1");
        FastBlock fastBlock = taiWeb3JRequest.getFastBockOfReward(snailNumber);
        logger.info("fastBlock=[{}]", fastBlock);
        System.out.println("fastBlock="+fastBlock);
    }


}
