package io.github.atholton.sigmath.util;

import java.util.Objects;
import java.util.function.Supplier;

public class Lazy<T> implements Supplier<T> {
    private T value;
    private Supplier<T> init;

    public Lazy(Supplier<T> init) {
        this.init = init;
    }

    @Override
    public T get() {
        if(value == null) {
            value = Objects.requireNonNull(init.get());
        }
        return value;
    }
}
