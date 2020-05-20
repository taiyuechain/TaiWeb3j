package com.taiweb3j.sample;

import com.taiweb3j.TaiWeb3jRequest;
import com.taiweb3j.common.Constant;
import com.taiweb3j.response.TaiSendTransaction;
import com.taiweb3j.response.transaction.TaiRawTransaction;
import com.taiweb3j.response.transaction.TaiTransactionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;

import java.math.BigInteger;

public class PaymentTransactionUsage extends TaiWeb3jTestNet {
    private static final Logger logger = LoggerFactory.getLogger(FastBlockUsage.class);

    public String signPaymentTxWithFrom() {
        String fromSignedTxStr = null;
        TaiTransactionManager taiTransactionManager = new TaiTransactionManager(taiWeb3JRequest, chainId);
        try {
            //get nonce of from address
            EthGetTransactionCount ethGetTransactionCount = web3j.ethGetTransactionCount(fromAddress, DefaultBlockParameterName.LATEST).sendAsync().get();
            BigInteger nonce = ethGetTransactionCount.getTransactionCount();
            TaiRawTransaction taiRawTransaction = TaiRawTransaction.creattaiPaymentTransaction(nonce, Constant.DEFAULT_GASPRICE,
                    Constant.DEFAULT_GASLIMIT, toAddress, Constant.DEFAULT_VALUE, null, paymentAddress);

            fromSignedTxStr = taiTransactionManager.signWithFromPrivateKey(taiRawTransaction, fromPrivatekey);
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
            TaiTransactionManager taiTransactionManager = new TaiTransactionManager(taiWeb3JRequest,
                    chainId);

            //payment sign and send transaction
            TaiSendTransaction taiSendTransaction = taiTransactionManager.signWithPaymentAndSend(signedTxWithFrom, paymentPrivateKey);


            if (taiSendTransaction != null && taiSendTransaction.hasError()) {
                logger.error("sendPaymentTransactionWithSigned error=[{}] ", taiSendTransaction.getError().getMessage());
            }
            txHash = taiSendTransaction.getTrueTransactionHash();
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
            TaiRawTransaction taiRawTransaction = TaiRawTransaction.creattaiPaymentTransaction(nonce, Constant.DEFAULT_GASPRICE,
                    Constant.DEFAULT_GASLIMIT, toAddress, Constant.DEFAULT_VALUE, null, paymentAddress);

            TaiTransactionManager taiTransactionManager = new TaiTransactionManager(taiWeb3JRequest, chainId);


            TaiSendTransaction taiSendTransaction = taiTransactionManager.signWithFromPaymentAndSend(
                    taiRawTransaction, fromPrivatekey, paymentPrivateKey);
            if (taiSendTransaction != null && taiSendTransaction.hasError()) {
                logger.error("sendPaymentTransactionWithSigned error=[{}] ", taiSendTransaction.getError().getMessage());
            }
            txHash = taiSendTransaction.getTrueTransactionHash();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return txHash;
    }
}
