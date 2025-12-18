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
    public void with(T value, Runnable runnable) {
        ref.where(ref, value).run(runnable);
    }

    @Override
    public <R> R with(T value, Supplier<R> callable) {
        return ref.where(ref, value).call(callable::get);
    }

    @Override
    public <X extends Throwable> void withOrThrow(T value, RunnableTx<X> runnable) throws X {
        ref.where(ref, value).call(() -> {
            runnable.run();
            return null;
        });
    }

    @Override
    public <R, X extends Throwable> R withOrThrow(T value, CallableTx<? extends R, X> callable) throws X {
        return ref.where(ref, value).call(callable::call);
    }

    @Override
    public ScopeLocal<T> set(T value) {
        log.error("ScopeLocal.set is invalid, please use ScopeLocal.with");
        return null;
    }

    @Override
    public void remove() {
        log.error("ScopeLocal.remove is invalid, please use ScopeLocal.with");
    }
}
