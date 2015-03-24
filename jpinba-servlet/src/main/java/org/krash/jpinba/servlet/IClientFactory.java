package org.krash.jpinba.servlet;

import org.krash.jpinba.Client;

/**
 * Interface for factories, providing clients for requests
 * @author krash
 */
public interface IClientFactory {
    public Client get();
}
