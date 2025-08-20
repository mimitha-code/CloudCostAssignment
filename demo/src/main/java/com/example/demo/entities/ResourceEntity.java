package com.example.demo.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class ResourceEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String type;
    private  int price;
    private  String region;
    protected ResourceEntity() {}
    public ResourceEntity(String type, int price, String region) {
        this.type = type;
        this.price = price;
        this.region = region;
    }

    public String getType() { return type; }

    public void setType(String type) { this.type = type; }

    public int getPrice() { return price; }

    public void setPrice(int price) {this.price = price; }

    public String getRegion() { return region; }

    public void setRegion(String region) { this.region = region; }



}
