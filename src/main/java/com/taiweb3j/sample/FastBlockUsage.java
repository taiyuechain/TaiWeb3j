package com.taiweb3j.sample;

import com.taiweb3j.response.fast.FastBlock;
import com.taiweb3j.response.snail.FastBalanceChange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;

public class FastBlockUsage extends TaiWeb3jTestNet {
    private static final Logger logger = LoggerFactory.getLogger(FastBlockUsage.class);

    public void getFastBlockByNumber() {
        BigInteger fastNumber = new BigInteger("100");
        FastBlock fastBlock = taiWeb3JRequest.getFastBlockByNumber(fastNumber, true);
        logger.info("fast block=[{}]", fastBlock.toString());
    }

    public void getFastBlockByHash() {
        String fastHash = "0x3d85286e8492eb22911ae004de6e29745f8eb68cf8ea740f4301587bc2e131a4";
        FastBlock fastBlock = taiWeb3JRequest.getFastBlockByHash(fastHash, true);
        logger.info("fast block=[{}]", fastBlock);
    }

    public void getCurrentFastNumber() {
        BigInteger currentFastNumber = taiWeb3JRequest.getCurrentFastNumber();
        logger.info("current fast number=[{}]", currentFastNumber);
    }

    /**
     * useage:
     * get balance with address which has changed
     */
    public void getStateChangeByFastNumber() {
        BigInteger fastNumber = new BigInteger("1011");
        FastBalanceChange fastBalanceChange = taiWeb3JRequest.getStateChangeByFastNumber(fastNumber);
        logger.info("balanceChange=[{}]", fastBalanceChange);
    }

}
