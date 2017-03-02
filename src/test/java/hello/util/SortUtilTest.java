package hello.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class SortUtilTest {

    @Test
    public void testSanity(){
        assertTrue(true);
    }

    @Test
    public void testQuickSortEmpty(){
        assertEquals(new String[]{},SortUtil.quickSort(new String[]{}));
    }

    @Test
    public void testUnsortedList(){
        String[] input = new String[]{"world","hello","is","best"};
        String[] output = new String[]{"best","hello","is","world"};
        assertEquals(output,SortUtil.quickSort(input));
    }
}
