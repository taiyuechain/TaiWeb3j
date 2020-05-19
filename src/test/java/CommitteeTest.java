import com.taiweb3j.sample.CommitteeUsage;
import org.junit.Before;
import org.junit.Test;

public class CommitteeTest extends TaiWeb3jRequestTest {
    private CommitteeUsage committeeUsage;

    @Before
    public void init() {
        committeeUsage = new CommitteeUsage();
    }

    @Test
    public void testGetCommitteeByNumber() {
        committeeUsage.getCommitteeByNumber();
    }

    @Test
    public void testGetCurrentCommitteeNumber() {
        committeeUsage.testGetCurrentCommitteeNumber();
    }
}
