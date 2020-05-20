package com.taiweb3j.response.snail;

import org.web3j.protocol.core.Response;
import org.web3j.utils.Numeric;

import java.math.BigInteger;

/**
 * eth_True_blockNumber.
 */
public class TaiSnailBlockNumber extends Response<String> {
    public BigInteger getTrueBlockNumber() {
        return Numeric.decodeQuantity(getResult());
    }
}
