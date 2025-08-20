package com.example.demo.repositories;

import com.example.demo.entities.ResourceEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Optional;

@Component
public class FakeDb
{
    static ArrayList<ResourceEntity> resources= new ArrayList<>();

    public FakeDb() {
        init();
    }

    public void init() {
        resources.add(new ResourceEntity("VM", 100, "us-east"));
        resources.add(new ResourceEntity("Storage", 10, "us-east"));
        resources.add(new ResourceEntity("VM", 120, "europe-west"));
    }
    public Optional<ResourceEntity> findByNameAndRegion(String name, String region) {
        return resources.stream()
                .filter(r -> r.getType().equalsIgnoreCase(name) && r.getRegion().equalsIgnoreCase(region))
                .findFirst();
    }
    public int findByTypeAndRegion(String name,String region){
        for(ResourceEntity obj:resources){
            System.out.println("Checking: " + obj.getType() + ", " + obj.getRegion());
            if(obj.getRegion().equalsIgnoreCase(region)){
                if(obj.getType().equalsIgnoreCase(name)){
                    return obj.getPrice();
                }
            }
        }
        return 0;
    }



}
