/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik
 * der Landeshauptstadt München, 2021
 */
package io.miragon.bpmrepo.server.configuration;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.Ticker;
import org.springframework.cache.Cache;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.concurrent.TimeUnit;

/**
 * This class provides the caches.
 * To disable the caching functionality delete this class, remove the corresponding bean creation methods
 * or remove the annotation {@link EnableCaching} above the class definition.
 */
@Configuration
@EnableCaching
public class CachingConfiguration {

    private static final int AUTHENTICATION_CACHE_EXPIRATION_TIME_SECONDS = 60;

    /**
     * Creates a bean to get a time source.
     *
     * @return The time source.
     */
    @Bean
    public Ticker ticker() {
        return Ticker.systemTicker();
    }

    /**
     * The config to provide a cache for method {@link CustomUserInfoTokenServices#loadAuthentication(String)}.
     *
     * @param ticker The time source for the cache.
     * @return The cache.
     */
    @Bean
    @Profile("!no-security")
    public Cache authenticationCache(final Ticker ticker) {
        return new CaffeineCache(CustomUserInfoTokenServices.NAME_AUTHENTICATION_CACHE,
                Caffeine.newBuilder()
                        .expireAfterWrite(AUTHENTICATION_CACHE_EXPIRATION_TIME_SECONDS, TimeUnit.SECONDS)
                        .ticker(ticker)
                        .build()
        );
    }

    //    /**
    //     * The config to provide a cache for repo {@link TheEntityRepository}.
    //     *
    //     * @param ticker The time source for the cache.
    //     * @return The cache.
    //     */
    //    @Bean
    //    public Cache theEntityRepositoryCache(Ticker ticker) {
    //        return new CaffeineCache(TheEntityRepository.CACHE,
    //                Caffeine.newBuilder()
    //                        .ticker(ticker)
    //                        .build()
    //        );
    //    }

}
