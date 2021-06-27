package com.example.myapplication.TikTokApp;


import java.util.*;

public interface BrokerInterface {
    public List<Consumer> registeredUsers = new ArrayList<Consumer>();
    public List<Publisher> registeredPublishers = new ArrayList<Publisher>();

    public void calculateKeys();
    public Publisher acceptConnection(Publisher p);
    public Consumer acceptConnection(Consumer c);
    public void notifyPublisher(String s);
    public void notifyBrokersOnChanges();
    public void pull(String s);
    public void filterConsumers(String s);
}
