package com.consistenthashing.hashing;

public interface HashFunction {

    /**
     * Computes hash for the given key
     */
    int hash(String key);
}
