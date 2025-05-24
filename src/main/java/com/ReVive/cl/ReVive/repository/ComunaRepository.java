package com.ReVive.cl.ReVive.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ReVive.cl.ReVive.model.Comuna;

@Repository
public interface ComunaRepository extends JpaRepository<Comuna, Long> {

}
