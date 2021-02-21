package com.beerhouse.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beerhouse.entities.Beer;
import com.beerhouse.exceptions.BeerNotFoundException;
import com.beerhouse.exceptions.ParseJsonPatchException;
import com.beerhouse.interfaces.IBeerService;
import com.beerhouse.services.impl.BeerServiceImpl;
import com.github.fge.jsonpatch.JsonPatch;

@Service
public class BeerService implements IBeerService{
	
	@Autowired
	private BeerServiceImpl beerServiceImpl;
	
	public Beer createBeer(Beer beer) {
		return beerServiceImpl.createBeer(beer);
	}
	
	public List<Beer> getBeers(){
		return beerServiceImpl.getBeers();
	}
	
	public Beer getBeerById(int id) throws BeerNotFoundException{
			return beerServiceImpl.getBeerById(id);

	}
	
	public void updateCompleteBeer(int id, Beer beer) throws BeerNotFoundException{
			beerServiceImpl.updateCompleteBeer(id, beer);
	}
	
	public void updatePatiallyBeer(int id, JsonPatch patch) throws ParseJsonPatchException {
		beerServiceImpl.updatePartiallyBeer(id, patch);
	}
	
	public void deleteBeer(int id) throws BeerNotFoundException{
		beerServiceImpl.deleteBeer(id);
	}
}
