package com.beerhouse.interfaces;

import java.util.List;

import com.beerhouse.entities.Beer;
import com.github.fge.jsonpatch.JsonPatch;

public interface IBeerServiceImpl {
	public Beer createBeer(Beer beer);
	public List<Beer> getBeers();
	public Beer getBeerById(int id);
	public void updateCompleteBeer(int id, Beer beer);
	public void updatePartiallyBeer(int id, JsonPatch patch);
	public void deleteBeer(int id);
	public void updateBeer(int id, Beer beer);
}
