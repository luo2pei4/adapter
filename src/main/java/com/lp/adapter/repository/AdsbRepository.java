package com.lp.adapter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lp.adapter.entity.AdsbEntity;

@Repository
public interface AdsbRepository extends JpaRepository<AdsbEntity, Long> {

}
