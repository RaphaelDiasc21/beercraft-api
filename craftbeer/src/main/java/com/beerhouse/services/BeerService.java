package com.beerhouse.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beerhouse.entities.Beer;
import com.beerhouse.exceptions.BeerNotFoundException;
import com.beerhouse.services.impl.BeerServiceImpl;

@Service
public class BeerService {
	
	@Autowired
	private BeerServiceImpl beerServiceImpl;
	
	public int createBeer(Beer beer) {
		return beerServiceImpl.createBeer(beer);
	}
	
	public List<Beer> getBeers(){
		return beerServiceImpl.getBeers();
	}
	
	public Beer getBeerById(int id) throws BeerNotFoundException{
		try {
			return beerServiceImpl.getBeerById(id);
		}catch(BeerNotFoundException e) {
			throw new BeerNotFoundException("Beer Not found exception");
		}
	}
	
	public void updateBeer(int id, Beer beer) throws BeerNotFoundException{
		try {
			beerServiceImpl.updateBeer(id, beer);
		}catch(BeerNotFoundException e) {
			throw new BeerNotFoundException("Beer nor found");
		}
	}
	
	public void deleteBeer(int id) throws BeerNotFoundException{
		beerServiceImpl.deleteBeer(id);
	}
}
