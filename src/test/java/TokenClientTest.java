import com.taiweb3j.common.AddressConstant;
import com.taiweb3j.sample.erc20.TokenClientUsage;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;

public class TokenClientTest extends TaiWeb3jRequestTest {
    private static final Logger logger = LoggerFactory.getLogger(TokenClientTest.class);

    private TokenClientUsage tokenClientUsage;

    @Before
    public void init() {
        tokenClientUsage = new TokenClientUsage();
    }


    @Test
    public void testSendTokenTransaction() {
        tokenClientUsage.sendTokenTransaction(AddressConstant.contractAddress,
                AddressConstant.toAddress,AddressConstant.fromPrivateKey);
    }

    @Test
    public void testGetTokenBalance() {
        BigInteger balanceValue = tokenClientUsage.getTokenBalance(AddressConstant.fromAddress, AddressConstant.contractAddress);
        logger.info("balanceValue =[{}]", balanceValue);
    }

    @Test
    public void testGetTokenName() {
        String tokenName = tokenClientUsage.getTokenName(AddressConstant.contractAddress);
        logger.info("tokenName =[{}]", tokenName);
    }

    @Test
    public void testGetTokenDecimals() {
        int tokenDecimals = tokenClientUsage.getTokenDecimals(AddressConstant.contractAddress);
        logger.info("tokenDecimals =[{}]", tokenDecimals);
    }

    @Test
    public void testGetTokenSymbol() {
        String tokenSymbol = tokenClientUsage.getTokenSymbol(AddressConstant.contractAddress);
        logger.info("tokenSymbol={}", tokenSymbol);
    }


    @Test
    public void testGetTokenTotalSupply() {
        BigInteger totalSupply = tokenClientUsage.getTokenTotalSupply(AddressConstant.contractAddress);
        logger.info("totalSupply={}",totalSupply.toString());
    }


}
