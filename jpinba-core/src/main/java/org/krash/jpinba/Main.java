package org.krash.jpinba;

import org.krash.jpinba.data.JPinbaRequest;
import org.krash.jpinba.data.Tag;
import org.krash.jpinba.data.Timer;

import java.io.IOException;

/**
 * Emulation of sending data, measured single request
 * @author krash
 */
public class Main {

    public static void main(String[] argv) throws InterruptedException, IOException {

        Client client = new Client("localhost");
        JPinbaRequest request = new JPinbaRequest("www56.local","example.com", "/some-page/");

        Tag tagOne = new Tag("memcache", "get");
        Tag tagTwo = new Tag("db", "select");

        Timer one = new Timer(tagOne);
        Thread.sleep(500);
        one.stop();

        Timer two = new Timer(100f, tagOne, tagTwo);

        request.addTimers(one,two);

        request.setRequestTime(100f);

        client.send(request);
    }
}
