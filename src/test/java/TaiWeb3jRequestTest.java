import com.taiweb3j.TaiWeb3jRequest;
import com.taiweb3j.common.Constant;
import org.junit.Before;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

public class TaiWeb3jRequestTest {

    public TaiWeb3jRequest taiWeb3JRequest = null;

    public Web3j web3j = null;

    @Before
    public void init() {
        taiWeb3JRequest = new TaiWeb3jRequest(Constant.RPC_TESTNET_URL);
        web3j = Web3j.build(new HttpService(Constant.RPC_TESTNET_URL));
    }

}

