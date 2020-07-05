package com.lp.adapter.repository;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lp.adapter.entity.AdsbEntity;

@Repository(value = "adsbRepository")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public interface AdsbRepository extends JpaRepository<AdsbEntity, Long> {

}
