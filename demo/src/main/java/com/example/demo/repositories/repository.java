package com.example.demo.repositories;

import com.example.demo.entities.ResourceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface repository extends JpaRepository<ResourceEntity, Integer> {
     Optional<ResourceEntity> findByTypeAndRegion(String type, String region);
     List<ResourceEntity> findByRegion(String region);
     List<ResourceEntity> findByType(String type);
     List<ResourceEntity> findAll();


}
