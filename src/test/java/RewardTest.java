import com.taiweb3j.sample.RewardUsage;
import org.junit.Before;
import org.junit.Test;

public class RewardTest extends TaiWeb3jRequestTest {
    private RewardUsage rewardUsage;

    @Before
    public void init() {
        rewardUsage = new RewardUsage();
    }

    @Test
    public void testGetSnailBalanceChange() {
        rewardUsage.getSnailBalanceChange();
    }

}
