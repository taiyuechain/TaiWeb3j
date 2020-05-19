package com.taiweb3j.sample;

import com.taiweb3j.TaiWeb3jRequest;
import com.taiweb3j.common.Constant;
import com.taiweb3j.response.EtrueSendTransaction;
import com.taiweb3j.response.transaction.TrueRawTransaction;
import com.taiweb3j.response.transaction.TrueTransactionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;

import java.math.BigInteger;

public class PaymentTransactionUsage extends TaiWeb3jTestNet {
    private static final Logger logger = LoggerFactory.getLogger(FastBlockUsage.class);

    public String signPaymentTxWithFrom() {
        String fromSignedTxStr = null;
        TrueTransactionManager trueTransactionManager = new TrueTransactionManager(taiWeb3JRequest, chainId);
        try {
            //get nonce of from address
            EthGetTransactionCount ethGetTransactionCount = web3j.ethGetTransactionCount(fromAddress, DefaultBlockParameterName.LATEST).sendAsync().get();
            BigInteger nonce = ethGetTransactionCount.getTransactionCount();
            TrueRawTransaction trueRawTransaction = TrueRawTransaction.createTruePaymentTransaction(nonce, Constant.DEFAULT_GASPRICE,
                    Constant.DEFAULT_GASLIMIT, toAddress, Constant.DEFAULT_VALUE, null, paymentAddress);

            fromSignedTxStr = trueTransactionManager.signWithFromPrivateKey(trueRawTransaction, fromPrivatekey);
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("fromSignedTxStr=[{}]", fromSignedTxStr);
        return fromSignedTxStr;
    }

    public String signPaymentTxWithPaymentAndSend(String signedTxWithFrom) {
        String txHash = null;
        try {
            TaiWeb3jRequest taiWeb3JRequest = new TaiWeb3jRequest(Constant.RPC_TESTNET_URL);
            TrueTransactionManager trueTransactionManager = new TrueTransactionManager(taiWeb3JRequest,
                    chainId);

            //payment sign and send transaction
            EtrueSendTransaction etrueSendTransaction = trueTransactionManager.signWithPaymentAndSend(signedTxWithFrom, paymentPrivateKey);


            if (etrueSendTransaction != null && etrueSendTransaction.hasError()) {
                logger.error("sendPaymentTransactionWithSigned error=[{}] ", etrueSendTransaction.getError().getMessage());
            }
            txHash = etrueSendTransaction.getTrueTransactionHash();
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("txHash=" + txHash);
        return txHash;
    }

    public String sendPaymentTx() {
        String txHash = null;
        try {
            //get nonce of from address
            EthGetTransactionCount ethGetTransactionCount = web3j.ethGetTransactionCount(fromAddress, DefaultBlockParameterName.LATEST).sendAsync().get();
            BigInteger nonce = ethGetTransactionCount.getTransactionCount();

            //create trueRawTransaction
            TrueRawTransaction trueRawTransaction = TrueRawTransaction.createTruePaymentTransaction(nonce, Constant.DEFAULT_GASPRICE,
                    Constant.DEFAULT_GASLIMIT, toAddress, Constant.DEFAULT_VALUE, null, paymentAddress);

            TrueTransactionManager trueTransactionManager = new TrueTransactionManager(taiWeb3JRequest, chainId);


            EtrueSendTransaction etrueSendTransaction = trueTransactionManager.signWithFromPaymentAndSend(
                    trueRawTransaction, fromPrivatekey, paymentPrivateKey);
            if (etrueSendTransaction != null && etrueSendTransaction.hasError()) {
                logger.error("sendPaymentTransactionWithSigned error=[{}] ", etrueSendTransaction.getError().getMessage());
            }
            txHash = etrueSendTransaction.getTrueTransactionHash();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return txHash;
    }
}
