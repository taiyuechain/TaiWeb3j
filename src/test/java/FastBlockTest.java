import com.taiweb3j.sample.FastBlockUsage;
import org.junit.Before;
import org.junit.Test;

public class FastBlockTest {

    FastBlockUsage fastBlockUsage;

    @Before
    public void init() {
        fastBlockUsage = new FastBlockUsage();
    }

    @Test
    public void testGetFastBlockByNumber() {
        fastBlockUsage.getFastBlockByNumber();
    }


    @Test
    public void testGetFastBlockByHash() {
        fastBlockUsage.getFastBlockByHash();
    }

    @Test
    public void testGetCurrentFastNumber() {
        fastBlockUsage.getCurrentFastNumber();
    }

    @Test
    public void testGetStateChangeByFastNumber() {
        fastBlockUsage.getStateChangeByFastNumber();
    }

}
