import com.taiweb3j.sample.StakingUsage;
import org.junit.Before;
import org.junit.Test;

public class StakingTest extends TaiWeb3jRequestTest {
    private StakingUsage stakingUsage;

    @Before
    public void init() {
        stakingUsage = new StakingUsage();
    }

    @Test
    public void testGetChainRewardContentByAddress() {
        stakingUsage.getChainRewardContentByAddress();
    }

    @Test
    public void testGetChainRewardContent() {
        stakingUsage.getChainRewardContent();
    }


    @Test
    public void testGetStakingAccountInfo() {
        stakingUsage.getStakingAccountInfo();
    }

    @Test
    public void testGetAllStakingAccount() {
        stakingUsage.getAllStakingAccount();
    }
}
