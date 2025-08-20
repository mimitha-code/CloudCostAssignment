package com.example.demo.entities;

import org.springframework.stereotype.Component;


public class inputEntity
{
    String region;
    int count;
    String type;

    public inputEntity(String region, int count, String type) {
        this.region = region;
        this.count = count;
        this.type = type;
    }

    public String getRegion() {
        return region;
    }

    public int getCount() {
        return count;
    }

    public String getType() {
        return type;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setType(String type) {
        this.type = type;
    }
}
