package org.noear.solon.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.ScopedValue;
import java.util.function.Supplier;

public class ScopeLocalJdk25<T> implements ScopeLocal<T> {
    private static final Logger log = LoggerFactory.getLogger(ScopeLocalJdk25.class);
    private final ScopedValue<T> ref = ScopedValue.newInstance();

    public ScopeLocalJdk25() {

    }

    public ScopeLocalJdk25(Class<?> applyFor) {

    }

    @Override
    public T get() {
        return ref.get();
    }

    @Override
    public <X extends Throwable> void with(T value, RunnableTx<X> runnable) throws X {
        ref.where(ref, value).call(() -> {
            runnable.run();
            return null;
        });
    }

    @Override
    public <R, X extends Throwable> R with(T value, CallableTx<? extends R, X> callable) throws X {
        return ref.where(ref, value).call(callable::call);
    }

    @Override
    public <X extends Throwable> void with(T value, ConsumerTx<T, X> consumer) throws X {
        ref.where(ref, value).call(() -> {
            consumer.accept(ref.get());
            return null;
        });
    }

    @Override
    public <R, X extends Throwable> R with(T value, FunctionTx<T, ? extends R, X> function) throws X {
        return ref.where(ref, value).call(() -> function.apply(ref.get()));
    }

    @Override
    public void set(T value) {
        log.error("ScopeLocal.set is invalid, please use ScopeLocal.with");
        throw new UnsupportedOperationException();
    }

    @Override
    public void remove() {
        log.error("ScopeLocal.remove is invalid, please use ScopeLocal.with");
        throw new UnsupportedOperationException();
    }
}