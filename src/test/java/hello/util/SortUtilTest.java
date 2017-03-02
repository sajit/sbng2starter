package hello.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.quicktheories.quicktheories.core.Source;
import org.quicktheories.quicktheories.impl.TheoryBuilder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.function.Function;

import static org.junit.Assert.*;
import static org.quicktheories.quicktheories.QuickTheory.qt;
import static org.quicktheories.quicktheories.generators.SourceDSL.*;

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

    @Test
    public void addingTwoPositiveIntegersAlwaysGivesAPositiveInteger(){
        Source<String> stringSource = strings().ascii().ofLengthBetween(1,5);
        final TheoryBuilder<String[]> theoryBuilder = qt().forAll(arrays().ofStrings(stringSource).withLengthBetween(0,3));
        theoryBuilder
        .checkAssert(arr -> assertEquals(arr.length,SortUtil.quickSort(arr).length));
        theoryBuilder.checkAssert(arr -> {
            String[] sorted = SortUtil.quickSort(arr);
            for(int i=0;i<sorted.length-1;i++){
                assertTrue(sorted[i].compareTo(sorted[i+1])<=0);
            }
        });


    }

}
