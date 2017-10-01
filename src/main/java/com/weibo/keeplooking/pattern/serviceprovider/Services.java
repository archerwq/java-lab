package com.weibo.keeplooking.pattern.serviceprovider;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Non-instantiable class for service registration and access.
 * 
 * @author Johnny
 * 
 */
public class Services {

    private static final String DEFAULT_PROVIDER_NAME = "<def>";

    // prevents instantiation and access
    private Services() {}

    // maps provider names to providers
    private static Map<String, Provider> providers = new ConcurrentHashMap<String, Provider>();

    // providers registration API
    public static void registerDefautProvider(Provider provider) {
        providers.put(DEFAULT_PROVIDER_NAME, provider);
    }

    public static void registerProvider(String name, Provider provider) {
        providers.put(name, provider);
    }

    // service access API
    public static Service newInstance() {
        return newInstance(DEFAULT_PROVIDER_NAME);
    }

    public static Service newInstance(String name) {
        Provider provider = providers.get(name);
        if (provider == null) {
            throw new IllegalArgumentException(
                    "No providers registered with name: " + name);
        }
        return provider.newService();
    }
}
