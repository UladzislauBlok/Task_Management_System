package com.blokdev.system.mapper;

public interface Mapper<F, T> {

    T mapFrom(F object);
}
