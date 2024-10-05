package com.consistenthashing.hashing;

import com.google.common.hash.Hashing;

public class Murmur3HashFunction implements HashFunction {

    private final com.google.common.hash.HashFunction hashFunction = Hashing.murmur3_128();

    @Override
    public int hash(String key) {
        return hashFunction.hashUnencodedChars(key).asInt();
    }
}
