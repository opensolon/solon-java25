/*
 * Copyright 2017-2025 noear.org and authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.noear.solon.util;

import java.lang.ScopedValue;
import java.util.function.Supplier;

public class ScopeLocalJdk25<T> implements ScopeLocal<T> {
    private final ScopedValue<T> ref = ScopedValue.newInstance();
    private final Class<?> applyFor;

    public ScopeLocalJdk25(Class<?> applyFor) {
        this.applyFor = applyFor;
    }

    @Override
    public T get() {
        if (ref.isBound()) {
            return ref.get();
        } else {
            return null;
        }
    }


    @Override
    public T getOr(Supplier<T> supplier) {
        T tmp = null;
        if (ref.isBound()) {
            tmp = ref.get();
        }

        if (tmp == null) {
            return supplier.get();
        } else {
            return tmp;
        }
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
}