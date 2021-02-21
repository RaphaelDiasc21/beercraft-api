package com.beerhouse.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.beerhouse.entities.Beer;

@DataJpaTest
public class BeerRepositoryTest {
	
	@Autowired
	BeerRepository beerRepository;
	
	@Test
	public void shouldInsertBeerEntity() {
		Beer beer = new Beer();
		beer.setName("Beer test");
		beer.setIngredients("ingredients beer test");
		beer.setAlcoholContent("alcohol content beer test");
		beer.setPrice(new BigDecimal(10.50));
		beer.setCategory("category beer test");
		
		beerRepository.save(beer);
		
		assertNotNull(beer.getId());
	}
	
	@Test
	public void shouldFindByIdBeerEntity() {
		Beer beer = new Beer();
		beer.setName("Beer test");
		beer.setIngredients("ingredients beer test");
		beer.setAlcoholContent("alcohol content beer test");
		beer.setPrice(new BigDecimal(10.50));
		beer.setCategory("category beer test");
		
		beerRepository.save(beer);
		
		Optional<Beer> beerEntityFromDb = beerRepository.findById(beer.getId());
		
		assertTrue(beerEntityFromDb.isPresent());
	}
	
	@Test
	public void shouldUpdateBeerEntity() {
		Beer beer = new Beer();
		beer.setName("Beer test");
		beer.setIngredients("ingredients beer test");
		beer.setAlcoholContent("alcohol content beer test");
		beer.setPrice(new BigDecimal(10.50));
		beer.setCategory("category beer test");
		
		beerRepository.save(beer);
		
		Optional<Beer> beerEntityFromDb = beerRepository.findById(beer.getId());
		Beer beerToUpdate = beerEntityFromDb.get();
		
		beerToUpdate.setName("Beer test updated");
		beerToUpdate.setIngredients("ingredients beer test updated");
		beerToUpdate.setAlcoholContent("alcohol content beer test updated");
		beerToUpdate.setPrice(new BigDecimal(11.50));
		beerToUpdate.setCategory("category beer test updated");
		
		beerRepository.save(beerToUpdate);
		
		Optional<Beer> beerEntityFromDbAfterUpdate = beerRepository.findById(beer.getId());
		assertTrue(beerEntityFromDbAfterUpdate.isPresent());
		
		Beer beerAfterUpdate = beerEntityFromDbAfterUpdate.get();
		assertEquals("Beer test updated",beerAfterUpdate.getName());
		assertEquals("ingredients beer test updated",beerAfterUpdate.getIngredients());
		assertEquals("alcohol content beer test updated",beerAfterUpdate.getAlcoholContent());
		assertEquals(new BigDecimal(11.50),beerAfterUpdate.getPrice());
		assertEquals("category beer test updated",beerAfterUpdate.getCategory());
	}
	
	@Test
	public void shouldReturnAllBeerEntities() {
		Beer beerFirst = new Beer();
		beerFirst.setName("Beer test");
		beerFirst.setIngredients("ingredients beer test");
		beerFirst.setAlcoholContent("alcohol content beer test");
		beerFirst.setPrice(new BigDecimal(10.50));
		beerFirst.setCategory("category beer test");
		
		beerRepository.save(beerFirst);
		
		Beer beerSecound = new Beer();
		beerSecound.setName("Beer test");
		beerSecound.setIngredients("ingredients beer test");
		beerSecound.setAlcoholContent("alcohol content beer test");
		beerSecound.setPrice(new BigDecimal(10.50));
		beerSecound.setCategory("category beer test");
		
		beerRepository.save(beerSecound);
		
		List<Beer> beers = beerRepository.findAll();
		assertEquals(2,beers.size());
	}
	
	@Test
	public void shouldDeleteBeerEntity() {
		Beer beerFirst = new Beer();
		beerFirst.setName("Beer test");
		beerFirst.setIngredients("ingredients beer test");
		beerFirst.setAlcoholContent("alcohol content beer test");
		beerFirst.setPrice(new BigDecimal(10.50));
		beerFirst.setCategory("category beer test");
		
		beerRepository.save(beerFirst);
		
		Beer beerSecound = new Beer();
		beerSecound.setName("Beer test");
		beerSecound.setIngredients("ingredients beer test");
		beerSecound.setAlcoholContent("alcohol content beer test");
		beerSecound.setPrice(new BigDecimal(10.50));
		beerSecound.setCategory("category beer test");
		
		beerRepository.save(beerSecound);
		beerRepository.deleteById(beerFirst.getId());
		
		List<Beer> beers = beerRepository.findAll();
		assertEquals(1,beers.size());
	}
	
}
