package com.beerhouse.serrvices.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Matchers.argThat;


import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.beerhouse.entities.Beer;
import com.beerhouse.exceptions.BeerNotFoundException;
import com.beerhouse.repositories.BeerRepository;
import com.beerhouse.services.impl.BeerServiceImpl;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class BeerServiceImplTest {
	@Mock
	private BeerRepository beerRepository;
	
	@Autowired
	@InjectMocks
	private BeerServiceImpl beerServiceImpl;
	
	
	@Test
	public void shouldReturnAllBeersEntity() {
		Beer beerToMockOne = new Beer("Beer to mock test one","Ingredients to mock test one",
				  					  "alcohol content to mock test one",
				  					  new BigDecimal(5.50),
				   					  "Category to mock test one");

		Beer beerToMockTwo = new Beer("Beer to mock test two","Ingredients to mock test two",
				  					  "alcohol content to mock test two",
				  					  new BigDecimal(6.50),
                					  "Category to mock test two");


		List<Beer> beersToMock = Arrays.asList(beerToMockOne,beerToMockTwo);

		when(beerRepository.findAll()).thenReturn(beersToMock);


		List<Beer> beers = beerServiceImpl.getBeers();
		assertEquals(2,beers.size());
	}
	
	@Test
	public void shouldReturnCreatedBeer() {
		Beer beerToTestCreateBeerMethod = new Beer("Beer to test","Ingredients to test",
				   									"alcohol content to test",
				   									new BigDecimal(6.50),
				    								"Category to test");
		
		beerToTestCreateBeerMethod.setId(1);
		when(beerRepository.save(beerToTestCreateBeerMethod)).thenReturn(beerToTestCreateBeerMethod);
		
		assertEquals(beerToTestCreateBeerMethod,beerServiceImpl.createBeer(beerToTestCreateBeerMethod));
	}
	
	@Test
	public void shouldUpdateBeer() {
		Beer beerToMock = new Beer();
		beerToMock.setId(1);
		beerToMock.setName("Beer to Mock");
		beerToMock.setIngredients("Beer to mock ingredients");
		beerToMock.setAlcoholContent("Beer to mock alcohol content");
		beerToMock.setPrice(new BigDecimal(10.50));
		beerToMock.setCategory("Pilsen");
		
		
		Beer beerToTestUpdate = new Beer();
		beerToTestUpdate.setId(1);
		beerToTestUpdate.setName("Beer updated");
		beerToTestUpdate.setIngredients("Beer updated ingredients");
		beerToTestUpdate.setAlcoholContent("Beer to mock alcohol content");
		beerToTestUpdate.setPrice(new BigDecimal(10.50));
		beerToTestUpdate.setCategory("Pilsen");
		
		when(beerRepository.findById(beerToMock.getId())).thenReturn(Optional.of(beerToMock));
	
		beerServiceImpl.updateBeer(beerToTestUpdate.getId(), beerToTestUpdate);
		
		verify(beerRepository, times(1)).save(argThat((Beer beer) -> beer.getName() == "Beer updated"));

	}
	
	@Test
	public void shouldDeleteBeer() {
		Beer beerToMock = new Beer();
		beerToMock.setId(1);
		beerToMock.setName("Beer to Mock");
		beerToMock.setIngredients("Beer to mock ingredients");
		beerToMock.setAlcoholContent("Beer to mock alcohol content");
		beerToMock.setPrice(new BigDecimal(10.50));
		beerToMock.setCategory("Pilsen");
		
		when(beerRepository.findById(beerToMock.getId())).thenReturn(Optional.of(beerToMock));
	
		beerServiceImpl.deleteBeer(beerToMock.getId());
		
		verify(beerRepository, times(1)).deleteById(beerToMock.getId());
	}
	
	@Test
	public void shouldThrowExceptionWhenBeerNotExistsInGetBeerByIdMethod() {

		when(beerRepository.findById(10)).thenReturn(Optional.empty());
		
		assertThrows(BeerNotFoundException.class, 
					 () -> beerServiceImpl.getBeerById(10),
				     "Beer not found");
	}
	
	@Test
	public void shouldThrowExceptionWhenBeerNotExistsInUpdateBeerMethod() {

		when(beerRepository.findById(10)).thenReturn(Optional.empty());
		
		Beer beerToTestUpdateBeerMethod = new Beer("Beer to test","Ingredients to test",
				  								   "alcohol content to test",
				  								   	new BigDecimal(6.50),
				  								    "Category to test");

		
		assertThrows(BeerNotFoundException.class, 
					 () -> beerServiceImpl.updateBeer(10,beerToTestUpdateBeerMethod),
				     "Beer not found");
	}
	
	@Test
	public void shouldThrowExceptionWhenBeerNotExistsInDeleteBeerMethod() {
		when(beerRepository.findById(10)).thenReturn(Optional.empty());
		
		assertThrows(BeerNotFoundException.class, 
				 () -> beerServiceImpl.deleteBeer(10),
			     "Beer not found");
	}
}
