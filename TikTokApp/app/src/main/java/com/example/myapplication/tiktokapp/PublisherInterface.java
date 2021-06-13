package com.example.myapplication.TikTokApp;

public interface PublisherInterface {
    public void addHashTag(String s);
    public void removeHashTag(String s);
    public void getBrokerList();
    public Broker HashTopic(String s);
    public void push(String path, String name, String hashtag);
    public void notifyFailure(Broker b);
    public void notifyBrokersForHashTags(String s);
    public void generateChunks(String path, String name, String hashtag);
}
