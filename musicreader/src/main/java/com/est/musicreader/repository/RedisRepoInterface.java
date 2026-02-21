package com.est.musicreader.repository;


public interface RedisRepoInterface {
    public void saveWithTimeout(String name, Object object, int TimeExp);
    public void save(String name, Object object);
    public String get(String name);
}
