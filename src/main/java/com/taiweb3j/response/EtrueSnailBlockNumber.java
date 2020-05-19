package com.taiweb3j.response;

import org.web3j.protocol.core.Response;
import org.web3j.utils.Numeric;

import java.math.BigInteger;

/**
 * eth_True_blockNumber.
 */
public class EtrueSnailBlockNumber extends Response<String> {
    public BigInteger getSnailBlockNumber() {
        return Numeric.decodeQuantity(getResult());
    }
}
