package com.example.demo.controllers;

import com.example.demo.entities.ResourceEntity;
import com.example.demo.entities.inputEntity;
import com.example.demo.services.CostEstimation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.example.demo.DTO.CostResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/cost")
public class HandleRequest
{
    @Autowired
    private CostEstimation cost;

    @PostMapping()
    public CostResponse cost(@RequestBody List<inputEntity> req){
        return cost.costEstimate(req);
    }
    @GetMapping("/region/{region}")
    public List<String> seachByRegion(@PathVariable String region){
        return cost.getTypeInRegion(region);
    }
    @GetMapping("/type/{type}")
    public List<String> searchbyType(@PathVariable String type){
        return cost.searchType(type);
    }
    @GetMapping("/search")
    public List<ResourceEntity> searchAll(){
        return cost.searchAll();
    }
    @GetMapping("/searchregion")
    public List<String> searchregions(){
        return cost.getAllRegions();
    }
    @GetMapping("/searchAllTypes")
    public List<String> searchalltypes(){
        return cost.getAllTypes();
    }
    @GetMapping("/checkoutprices/{resourceType}")
    public List<Map<String, Object>> getCheckoutPrices(@PathVariable String resourceType) {
        return cost.getPricesByResourceType(resourceType);
    }


}
