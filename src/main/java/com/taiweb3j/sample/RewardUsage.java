package com.taiweb3j.sample;

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
}
