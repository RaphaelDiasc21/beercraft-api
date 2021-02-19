package com.beerhouse.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.beerhouse.entities.Beer;

@Repository
public interface BeerRepository extends JpaRepository<Beer,Integer>{

}
