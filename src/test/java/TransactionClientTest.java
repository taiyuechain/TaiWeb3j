import com.taiweb3j.common.AddressConstant;
import com.taiweb3j.sample.erc20.TransactionClientUsage;
import org.junit.Before;
import org.junit.Test;

public class TransactionClientTest extends TaiWeb3jRequestTest {
    private TransactionClientUsage transactionClientUsage;

    @Before
    public void init() {
        transactionClientUsage = new TransactionClientUsage();
    }

    @Test
    public void testGetBalance() {
        transactionClientUsage.getBalance(AddressConstant.fromAddress);
    }

    @Test
    public void testGetBalanceWithPrivateKey() {
        transactionClientUsage.getBalanceWithPrivateKey(AddressConstant.fromPrivateKey);
    }

    @Test
    public void testGenRawTransaction() {
        String hexMessage =transactionClientUsage.genRawTransaction();
        transactionClientUsage.sendRawTransaction(hexMessage);
    }


}
