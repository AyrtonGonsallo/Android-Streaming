package com.example.appstreaming;

public class UrlBroker {

    public UrlBroker() {
    }

    public String broke(String relative){
        String absolute=relative.replace("..","https://ayr-streaming.herokuapp.com");
        return absolute;
    }
}
