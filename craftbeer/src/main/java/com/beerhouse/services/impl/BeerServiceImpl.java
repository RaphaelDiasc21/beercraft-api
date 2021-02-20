package com.beerhouse.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.beerhouse.entities.Beer;
import com.beerhouse.exceptions.BeerNotFoundException;
import com.beerhouse.repositories.BeerRepository;

@Component
public class BeerServiceImpl{
	
	@Autowired
	private BeerRepository beerRepository;
	
	public int createBeer(Beer beer) {
		Beer beerCreated = beerRepository.save(beer);
		return beerCreated.getId();
	}
	
	public List<Beer> getBeers(){
		return beerRepository.findAll();
	}
	
	public Beer getBeerById(int id) throws BeerNotFoundException{
		Optional<Beer> beerFounded = beerRepository.findById(id);
		
		if(beerFounded.isPresent()) {
			return beerFounded.get();
		}

		throw new BeerNotFoundException("Beer not found");
	}
	
	public void updateBeer(int id, Beer beer) throws BeerNotFoundException{
		try {
			
			Beer beerToUpdate = getBeerById(id);
			beerToUpdate.setName(beer.getName());
			beerToUpdate.setAlcoholContent(beer.getAlcoholContent());
			beerToUpdate.setCategory(beer.getCategory());
			beerToUpdate.setIngredients(beer.getIngredients());
			beerToUpdate.setPrice(beer.getPrice());
			
			beerRepository.save(beerToUpdate);
		}catch(BeerNotFoundException e) {
			throw new BeerNotFoundException("Beer nor found");
		}
	}
	
	public void deleteBeer(int id) throws BeerNotFoundException{
		Optional<Beer> beerFounded = beerRepository.findById(id);
		
		if(beerFounded.isPresent()) {
			Beer beerToDelete = beerFounded.get();
			beerRepository.delete(beerToDelete);
		}

		throw new BeerNotFoundException("Beer not found");
		
	}
}
