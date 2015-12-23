package hu.zalatnai.discountwarehouse.sdk;

import java.util.concurrent.TimeUnit;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;

import org.springframework.util.concurrent.ListenableFuture;

/**
 * A caching decorator for ListenableFutureProviders
 * Note: this could've been made much more generic by injecting JSR-107 (or anything like that) Cache implementations
 * But for this simple task this solution is adequate.
 * - It checks if the ListenableFuture representing the entry identified by S source is in the underlying cache, then
 * - - If it is cached, return it
 * - - If it is not cached, delegate to the wrapped provider & cache the returned ListenableFuture
 * @param <S>
 * @param <T>
 */
public class CachingListenableFutureProviderDecorator<S, T> implements ListenableFutureProvider<S, T> {
    private final ListenableFutureProvider<S, T> wrappedProvider;
    private final LoadingCache<S, ListenableFuture<T>> cache;

    //todo: inject cache, maybe add configurable parameters.
    public CachingListenableFutureProviderDecorator(ListenableFutureProvider<S, T> wrappedProvider) {
        this.wrappedProvider = wrappedProvider;
        cache = Caffeine.newBuilder()
            .maximumSize(10000)
            .expireAfterWrite(10, TimeUnit.MINUTES)
            .build(wrappedProvider::get);
    }

    @Override public ListenableFuture<T> get(S source) {
        return this.cache.get(source);
    }
}
