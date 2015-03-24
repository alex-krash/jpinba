package org.krash.jpinba.servlet;

import org.krash.jpinba.Client;
import org.krash.jpinba.data.JPinbaRequest;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This listener sends JPinbaRequest, attached to servlet request, before it's destroyed
 * @author krash
 */
public class JPinbaServletListener implements ServletRequestListener {

    private final IClientFactory factory;
    Logger logger = Logger.getLogger(JPinbaServletListener.class.getName());

    public JPinbaServletListener(IClientFactory factory) {
        this.factory = factory;
    }

    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
        JPinbaRequest req = Helper.extract(sre.getServletRequest());
        if(null != req)
        {
            try(Client client = factory.get()) {
                client.send(req);
            } catch (IOException e) {
                logger.log(Level.SEVERE, null, e);
            }
        }
    }

    @Override
    public void requestInitialized(ServletRequestEvent sre) {
    }
}
