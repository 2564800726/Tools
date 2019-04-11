package com.blogofyb.tools.img.interfaces;

public interface Cache<K, V> {

    void put(K key, V value);

    V get(K key);

}
