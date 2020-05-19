package com.taiweb3j.sample;

import com.taiweb3j.response.committee.CommitteeInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;

public class CommitteeUsage extends TaiWeb3jTestNet {
    private static final Logger logger = LoggerFactory.getLogger(CommitteeUsage.class);

    public void getCommitteeByNumber() {
        BigInteger committeeNumber = new BigInteger("100");
        CommitteeInfo committeeInfo = taiWeb3JRequest.getCommitteeByNumber(committeeNumber);
        logger.info("committeeInfo=[{}]", committeeInfo);
    }

    public void testGetCurrentCommitteeNumber() {
        Integer currentCommitteeNumber = taiWeb3JRequest.getCurrentCommitteeNumber();
        logger.info("current committee number=[{}]", currentCommitteeNumber);
    }

}
