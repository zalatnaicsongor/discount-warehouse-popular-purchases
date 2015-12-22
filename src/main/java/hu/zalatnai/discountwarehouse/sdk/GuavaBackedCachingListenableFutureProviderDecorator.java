package hu.zalatnai.discountwarehouse.sdk;


import org.springframework.util.concurrent.ListenableFuture;

/**
 * A caching decorator for ListenableFutureProviders
 * Note: this could've been made much more generic by injecting JSR-107 (or anything like that) Cache implementations
 * But for this simple task this solution is adequate.
 * - It checks if the entry identified by S source is in the underlying cache, then
 * - - If it is cached, return it
 * - - If it is not cached, delegate to the wrapped provider & add a callback which caches the value upon completion
 * @param <S>
 * @param <T>
 */
public class GuavaBackedCachingListenableFutureProviderDecorator<S, T> implements ListenableFutureProvider<S, T> {
    private final ListenableFutureProvider<S, T> wrappedProvider;

    public GuavaBackedCachingListenableFutureProviderDecorator(ListenableFutureProvider<S, T> wrappedProvider) {
        this.wrappedProvider = wrappedProvider;
    }

    @Override public ListenableFuture<T> get(S source) {
        return null;
    }
}
