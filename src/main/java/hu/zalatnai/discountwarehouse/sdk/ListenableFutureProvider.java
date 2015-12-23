package hu.zalatnai.discountwarehouse.sdk;

import org.springframework.util.concurrent.ListenableFuture;

/**
 * Generic interface that provides something in the future identified by an identifier(s)
 * @param <S> Identifier
 * @param <T> Object whose identifier is the given identifier
 */
public interface ListenableFutureProvider<S, T> {
    ListenableFuture<T> get(S source);
}