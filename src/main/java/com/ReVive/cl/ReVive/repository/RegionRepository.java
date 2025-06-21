package com.ReVive.cl.ReVive.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ReVive.cl.ReVive.model.Region;

@Repository
public interface RegionRepository extends JpaRepository<Region, Long>{
    
    Region findByIdRegion(Long idRegion);
}
