package com.example.demo.services;

import com.example.demo.DTO.CostResponse;
import com.example.demo.entities.ResourceEntity;
import com.example.demo.entities.inputEntity;
import com.example.demo.repositories.repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CostEstimation
{
    @Autowired
    private  final repository repo;
    @Autowired
    public CostEstimation(repository repo)
    {
        this.repo=repo;
    }

    public int calculate(String type, String region, int count) {
        Optional<ResourceEntity> resource = repo.findByTypeAndRegion(type, region);
        if (resource.isEmpty()) {
            System.out.println("No match found in DB!");
        }
        return resource
            .map(r -> r.getPrice() * count)  // If found → price * count
            .orElse(0);                      // If not found → 0
    }

    public CostResponse costEstimate(List<inputEntity> req){
        int res=0;
        List<CostResponse.BreakDown> breakDowns=new ArrayList<>();

        for(inputEntity entity:req){
            int itemPrice=calculate(entity.getType(), entity.getRegion(),entity.getCount());
            res+=itemPrice;
            Optional<ResourceEntity> price=repo.findByTypeAndRegion(entity.getType(),entity.getRegion());
            breakDowns.add(new CostResponse.BreakDown(itemPrice,entity,price.get().getPrice()));
        }
        return  new CostResponse(res,breakDowns);
    }
    public List<ResourceEntity> searchRegion(String region){
        return repo.findByRegion(region);
    }
    public List<String> searchType(String type){
        return repo.findByType(type)
                .stream()
                .map(ResourceEntity::getRegion)
                .distinct()
                .toList();
    }
    public List<ResourceEntity> searchAll(){return repo.findAll();}

    public List<String> getAllRegions() {
        return repo.findAll()
                .stream()
                .map(ResourceEntity::getRegion)
                .distinct()
                .toList();
    }
    public List<String> getAllTypes() {
        return repo.findAll()
                .stream()
                .map(ResourceEntity::getType)
                .distinct()
                .toList();
    }
    public List<String> getTypeInRegion(String region) {
        return repo.findByRegion(region)
                .stream()
                .map(ResourceEntity::getType)
                .distinct()
                .toList();
    }
    public List<Map<String, Object>> getPricesByResourceType(String type) {
        return repo.findByType(type)
                .stream()
                .map(r -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("region", r.getRegion());
                    map.put("price", r.getPrice());
                    return map;
                })
                .collect(Collectors.toList());
    }



}


