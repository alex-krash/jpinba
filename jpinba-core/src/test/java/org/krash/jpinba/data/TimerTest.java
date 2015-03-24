package org.krash.jpinba.data;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * User: krash
 * Date: 2/13/15
 * Time: 3:04 PM
 */
public class TimerTest {

    @Test
    public void testGetTags() {
        Timer timer = new Timer();
        ITag tag = new ITag() {
            @Override
            public ITagName getName() {
                return null;
            }

            @Override
            public String getValue() {
                return null;
            }
        };
        timer.addTag(tag);
        assertEquals(1, timer.getTags().size());
        assertTrue(timer.getTags().contains(tag));

        //same tag
        timer.addTag(tag);
        assertEquals(1, timer.getTags().size());
        assertTrue(timer.getTags().contains(tag));

        timer = new Timer(tag, tag);
        assertEquals(1, timer.getTags().size());
        assertTrue(timer.getTags().contains(tag));

        timer = new Timer(Arrays.asList(tag, tag));
        assertEquals(1, timer.getTags().size());
        assertTrue(timer.getTags().contains(tag));
    }

    @Test
    public void testDuration() throws InterruptedException {
        float duration = 0.045f;
        assertEquals(duration, new Timer(duration).getDuration(), 0);

        long start = System.currentTimeMillis();
        Timer timer = new Timer();
        Thread.sleep(1000);
        timer.stop();
        long finish = System.currentTimeMillis();
        assertTrue("Timer duration is lower than zero", timer.getDuration() > 0);
        assertTrue("Timer duration is greater than expected", timer.getDuration() <= (finish - start));
    }
}
