import com.taiweb3j.sample.PaymentTransactionUsage;
import org.junit.Before;
import org.junit.Test;

public class PaymentTransactionTest extends TaiWeb3jRequestTest {
    private PaymentTransactionUsage paymentTransactionUsage;

    @Before
    public void init() {
        paymentTransactionUsage = new PaymentTransactionUsage();
    }

    @Test
    public void TestSignPaymentTxWithFrom(){
       paymentTransactionUsage.signPaymentTxWithFrom();
    }


    @Test
    public void TestSignPaymentTxWithPaymentAndSend(){
        String fromSignedTxStr = paymentTransactionUsage.signPaymentTxWithFrom();
        paymentTransactionUsage.signPaymentTxWithPaymentAndSend(fromSignedTxStr);
    }

    @Test
    public void TestSendPaymentTx(){
        paymentTransactionUsage.sendPaymentTx();
    }




}
