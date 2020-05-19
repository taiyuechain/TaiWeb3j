package com.taiweb3j.response.transaction;

import com.taiweb3j.TaiWeb3jRequest;
import com.taiweb3j.response.EtrueSendTransaction;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.Sign;
import org.web3j.utils.Numeric;

public class TrueTransactionManager {

    public TaiWeb3jRequest taiWeb3JRequest;
    public int chainId;

    private TrueTransactionManager() {

    }

    public TrueTransactionManager(TaiWeb3jRequest taiWeb3JRequest, int chainId) {
        this.taiWeb3JRequest = taiWeb3JRequest;
        this.chainId = chainId;
    }

    /**
     * 代付签名出现的几种应用场景：
     * <p>
     * 1 前端from签名，将签名结果发送给后端，后端payment签名，将交易发送出去，
     * 或者后端将代付后的签名发送给前端，让前端发送交易
     * <p>
     * 2 后端from签名，将签名结果发送给前端，前端payment签名，将交易发送出去
     * 或者后端接收前端payment签名后的结果将交易发送出去
     * <p>
     * 3 前端from签名，然后再继续payment签名，将交易发送出去
     */

    /**
     * get signedTxWithFromPrivate
     * @param trueRawTransaction
     * @param fromPrivateKey
     * @return
     */
    public String signWithFromPrivateKey(TrueRawTransaction trueRawTransaction, String fromPrivateKey) {
        String signedTxWithFrom = null;
        try {
            Credentials credentials_from = Credentials.create(fromPrivateKey);
            byte[] signedMessage = TrueTransactionEncoder.signMessageFrom(trueRawTransaction, chainId, credentials_from);
            signedTxWithFrom = Numeric.toHexString(signedMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return signedTxWithFrom;
    }

    /**
     * @param signedTxWithFrom  transaction singed with from privatekey,called signedTxWithFrom
     * @param paymentPrivateKey payment privateKey
     * @return transaction singed with payment privatekey  based on signedTxWithFrom
     */
    public String signWithPaymentPrivateKey(String signedTxWithFrom, String paymentPrivateKey) {
        String signedTxWithPayment = null;
        try {
            Credentials credentials_payment = Credentials.create(paymentPrivateKey);
            System.out.println("sendPaymentTransaction payment address: " + credentials_payment.getAddress());

            //通过rawTransaction解码出交易信息，包括发送者签名rsv
            SignedTrueRawTransaction signtrueRawTransaction = (SignedTrueRawTransaction) TrueTransactionDecoder.decode(signedTxWithFrom);
            Sign.SignatureData decode_signatureData = signtrueRawTransaction.getSignatureData();
            TrueRawTransaction decode_trueRawTransaction = new TrueRawTransaction(signtrueRawTransaction);

            byte[] signedMessage = TrueTransactionEncoder.
                    signMessage_payment(decode_trueRawTransaction, decode_signatureData,
                            chainId, credentials_payment);

            signedTxWithPayment = Numeric.toHexString(signedMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return signedTxWithPayment;
    }

    /**
     * sign with payment and send transction
     *
     * @param signedTxWithFrom  transaction singed with from privatekey,called signedTxWithFrom
     * @param paymentPrivateKey payment privateKey
     * @return transaction singed with payment privatekey  based on signedTxWithFrom
     */
    public EtrueSendTransaction signWithPaymentAndSend(String signedTxWithFrom, String paymentPrivateKey) {
        String signedTxWithPayment = signWithPaymentPrivateKey(signedTxWithFrom, paymentPrivateKey);
        EtrueSendTransaction etrueSendTransaction = taiWeb3JRequest.etrueSendRawTransaction(signedTxWithPayment);
        return etrueSendTransaction;
    }


    /**
     * sign with from and payment private
     * @param trueRawTransaction trueTransaction info
     * @param fromPrivateKey     tx from privatekey
     * @param paymentPrivateKey  tx payment privatekey
     * @return
     */
    public String signWithFromAndPayment(TrueRawTransaction trueRawTransaction,
                                         String fromPrivateKey, String paymentPrivateKey) {
        Credentials fromCredentials = Credentials.create(fromPrivateKey);
        Credentials paymentCredentials = Credentials.create(paymentPrivateKey);
        byte[] signedMessage = TrueTransactionEncoder.signMessage_fromAndPayment(
                trueRawTransaction, chainId, fromCredentials, paymentCredentials);
        String signedTxWithPayment = Numeric.toHexString(signedMessage);
        return signedTxWithPayment;
    }

    public EtrueSendTransaction signWithFromPaymentAndSend(TrueRawTransaction trueRawTransaction,
                                                           String fromPrivateKey, String paymentPrivateKey) {
        Credentials fromCredentials = Credentials.create(fromPrivateKey);
        Credentials paymentCredentials = Credentials.create(paymentPrivateKey);
        byte[] signedMessage = TrueTransactionEncoder.signMessage_fromAndPayment(
                trueRawTransaction, chainId, fromCredentials, paymentCredentials);
        String signedWithFromPayment = Numeric.toHexString(signedMessage);
        EtrueSendTransaction etrueSendTransaction = taiWeb3JRequest.etrueSendRawTransaction(signedWithFromPayment);
        return etrueSendTransaction;
    }


}
