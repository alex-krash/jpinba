package org.krash.jpinba.data;

import org.junit.Test;
import org.krash.jpinba.request.PinbaRequest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

/**
 * User: krash
 * Date: 2/13/15
 * Time: 4:15 PM
 */
public class RequestTest {

    @Test
    public void testBuilderFilledCorrectly() {
        PinbaRequest.RequestBody.Builder builder = PinbaRequest.RequestBody.newBuilder();
        Measure measure = new Measure(10f, 20, 30f, 40f);
        JPinbaRequest request = new JPinbaRequest("host", "server", "script", measure);
        request.setDocumentSize(300);

        Timer timerOne = new Timer(10f);
        timerOne.addTag(new Tag("key", "value"));
        Timer timerTwo = new Timer(20f);
        timerTwo.addTag(new Tag("key", "value"));
        request.addTimers(timerOne, timerTwo);

        request.initBuilder(builder);
        assertSame("host", builder.getHostname());
        assertSame("server", builder.getServerName());
        assertSame("script", builder.getScriptName());
        assertEquals(300, builder.getDocumentSize());
        assertEquals(measure.getRequestTime(), builder.getRequestTime(), 0);
        assertEquals(measure.getMaxMemory(), builder.getMemoryPeak());
        assertEquals(measure.getUtime(), builder.getRuUtime(), 0);
        assertEquals(measure.getStime(), builder.getRuStime(), 0);
        assertSame("", builder.getSchema());
    }
}
