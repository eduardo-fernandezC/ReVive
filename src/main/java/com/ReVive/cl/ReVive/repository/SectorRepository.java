package com.ReVive.cl.ReVive.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ReVive.cl.ReVive.model.Sector;
@Repository
public interface SectorRepository extends JpaRepository<Sector, Long> {
   
   Sector findByIdSector(Long idSector);
}
