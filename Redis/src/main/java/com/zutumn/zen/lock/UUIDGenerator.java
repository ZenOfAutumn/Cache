package com.zutumn.zen.lock;

import java.util.UUID;

/**
 * Unique ID Generator
 *
 * @author zhikong.wl
 * 2017-10-23 11:44
 **/
public class UUIDGenerator implements  UniqueIDGenerator {

    public String generate() {
       return UUID.randomUUID().toString();
    }
}
