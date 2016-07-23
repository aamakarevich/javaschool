package backend.com.tsystems.ecare.app.utils;

import com.tsystems.ecare.app.utils.TestUtils;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test class for TestUtils.
 */
public class TestUtilsTest {

    /**
     * Tests if generated strings are of proper length.
     */
    @Test
    public void getStringOfLength() {
        Assert.assertEquals(TestUtils.getStringOfLength(-100).length(), 0);
        assertEquals(TestUtils.getStringOfLength(0).length(), 0);
        assertEquals(TestUtils.getStringOfLength(40).length(), 40);
        assertEquals(TestUtils.getStringOfLength(100).length(), 100);
    }
}