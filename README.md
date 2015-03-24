jpinba
=============
**Pure JAVA client for Pinba Engine (http://pinba.org)**

Pinba is a MySQL storage engine that acts as a realtime monitoring/statistics server
using MySQL as a read-only interface.
Jpinba provides client for Pinba server that provides statistics sending from your application.

## Usage

General usage of client:

```java
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
```

## Servlet API
For ready-to-ride integration with javax.servlet-api, see module [jpinba-servlet](jpinba-servlet/src/test/java/org/krash/jpinba/Main.java).

