package com.taiweb3j.sample;

import com.taiweb3j.response.Reward.ChainRewardContent;
import com.taiweb3j.response.snail.SnailBlock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.util.Map;

public class SnailBlockUsage extends TaiWeb3jTestNet {
    private static final Logger logger = LoggerFactory.getLogger(SnailBlockUsage.class);

    public void getSnailBlockByNumber() {
        BigInteger snailNumber = new BigInteger("100");
        SnailBlock snailBlock = taiWeb3JRequest.getSnailBlockByNumber(snailNumber, true);
        logger.info("snailBlock=[{}]", snailBlock);
    }

    public void getSnailBlockByHash() {
        String snailHash = "0x060e8090d3a2babe117eac6cf5be681850cbd4076f4fe465c8d626e051a49dc9";
        SnailBlock snailBlock = taiWeb3JRequest.getSnailBlockByHash(snailHash, true);
        logger.info("snailBlock=[{}]", snailBlock);
    }

    public void getSnailHashByNumber() {
        BigInteger snailNumber = new BigInteger("100");
        String snailHash = taiWeb3JRequest.getSnailHashByNumber(snailNumber);
        logger.info("snailHash=[{}]", snailHash);
    }

    public void getCurrentSnailNumber() {
        BigInteger currentSnailNumber = taiWeb3JRequest.getCurrentSnailNumber();
        logger.info("currentSnailNumber=[{}]", currentSnailNumber);
    }

    public void getSnailBalanceChange() {
        BigInteger snailNumber = new BigInteger("100");
        Map<String, String> addrWithBalance = taiWeb3JRequest.getSnailBalanceChange(snailNumber);
        logger.info("addrWithBalance=[{}]", addrWithBalance.toString());
    }

    public void getSnailRewardContent() {
        BigInteger snailNumber = new BigInteger("100");
        ChainRewardContent chainRewardContent = taiWeb3JRequest.getSnailRewardContent(snailNumber);
        logger.info("chainRewardContent=[{}]", chainRewardContent);
    }
}
