package com.lp.adapter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lp.adapter.entity.FlightInfoEntity;

@Repository
public interface FlightInfoRepository extends JpaRepository<FlightInfoEntity, Long> {

	@Query(value = "select f from FlightInfoEntity f where f.an = ?1")
	public FlightInfoEntity searchEntityByAn(String an);
}
