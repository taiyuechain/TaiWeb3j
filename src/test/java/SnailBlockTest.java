import com.taiweb3j.sample.SnailBlockUsage;
import org.junit.Before;
import org.junit.Test;

public class SnailBlockTest extends TaiWeb3jRequestTest {

    private SnailBlockUsage snailBlockUsage;

    @Before
    public void init() {
        snailBlockUsage = new SnailBlockUsage();
    }

    @Test
    public void testGetSnailBlockByNumber() {
        snailBlockUsage.getSnailBlockByNumber();
    }

    @Test
    public void testGetSnailBlockByHash() {
        snailBlockUsage.getSnailBlockByHash();
    }

    @Test
    public void testGetCurrentSnailNumber() {
        snailBlockUsage.getCurrentSnailNumber();
    }


    @Test
    public void testGetSnailHashByNumber() {
        snailBlockUsage.getSnailHashByNumber();
    }


    @Test
    public void testGetSnailBalanceChange() {
        snailBlockUsage.getSnailBalanceChange();
    }

    @Test
    public void testGetSnailRewardContent() {
        snailBlockUsage.getSnailRewardContent();
    }


}
