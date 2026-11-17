package com.lelegspears.mural_fyi.util;

import java.util.function.Consumer;

public final class Patcher {    // Classe substituindo o Mapper

    private static final Patcher INSTANCE = new Patcher(); // Usado para construir a instancia

    private Patcher() {}

    public static Patcher apply() {
        return INSTANCE;
    }

    public <T> Patcher ifPresent(T value, Consumer<T> setter) {
        if (value != null) {
            setter.accept(value);
        }
        return this;
    }

    public Patcher ifNotBlank(String value, Consumer<String> setter) {
        if (value != null && !value.isBlank()) {
            setter.accept(value);
        }
        return this;
    }
}