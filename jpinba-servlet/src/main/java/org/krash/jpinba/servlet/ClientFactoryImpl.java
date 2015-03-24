package org.krash.jpinba.servlet;

import org.krash.jpinba.Client;

import java.net.InetAddress;

/**
 * Simple implementation of client factory
 * @author krash
 */
public class ClientFactoryImpl implements IClientFactory {

    public final InetAddress address;

    public ClientFactoryImpl(InetAddress address) {
        this.address = address;
    }

    @Override
    public Client get() {
        return new Client(address);
    }
}
