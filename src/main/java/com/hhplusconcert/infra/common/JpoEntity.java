package com.hhplusconcert.infra.common;

public interface JpoEntity<T> {
    T toDomain();
}
