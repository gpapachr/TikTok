package com.example.myapplication.TikTokApp;

import java.util.*;

public interface Node {
    public List<Broker> brokers = new ArrayList<Broker>();

    public void init(int port);
    public List<Broker> getBrokers();
    public void connect();
    public void disconnect();
    public void updateNodes();
}
