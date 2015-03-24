package org.krash.jpinba;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.krash.jpinba.data.Tag;
import org.krash.jpinba.data.Timer;
import org.krash.jpinba.servlet.ClientFactoryImpl;
import org.krash.jpinba.servlet.Helper;
import org.krash.jpinba.servlet.JPinbaFilter;
import org.krash.jpinba.servlet.JPinbaServletListener;

import javax.servlet.DispatcherType;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.InetAddress;
import java.util.EnumSet;

/**
 * Sample server, listening on 8080 port, an sending stats to pinba
 * @author krash
 */
public class Main {

    public static void main(String[] argv) throws Exception
    {
        String host = argv.length > 1 ? argv[1] : "localhost";
        Server server = new Server(8080);
        ServletContextHandler context = new ServletContextHandler();
        context.setContextPath("/");
        server.setHandler(context);
        context.addServlet(new ServletHolder(new HelloServlet()),"/*");
        context.addEventListener(new JPinbaServletListener(new ClientFactoryImpl(InetAddress.getByName(host))));
        context.addFilter(new FilterHolder(new JPinbaFilter()), "/*", EnumSet.of(DispatcherType.REQUEST, DispatcherType.INCLUDE));

        server.start();
        server.join();
    }

    public static class HelloServlet extends HttpServlet {
        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            response.setContentType("text/html");
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().println("<h1>I was measured with pinba client</h1>");
            Timer timer = new Timer(9.0f);
            timer.addTag(new Tag("Test", "servlet"));
            Helper.extract(request).addTimers(timer);
        }
    }
}
