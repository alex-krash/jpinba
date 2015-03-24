package org.krash.jpinba.data;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * User: krash
 * Date: 2/13/15
 * Time: 4:24 PM
 */
public class MeasureTest {

    @Test
    public void testGetters()
    {
        Measure m = new Measure(10f, 20, 30f, 40f);
        assertEquals(10f, m.getRequestTime(), 0);
        assertEquals(20, m.getMaxMemory());
        assertEquals(30f, m.getUtime(), 0);
        assertEquals(40f, m.getStime(), 0);
    }

}
