package com.beerhouse.services;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

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

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@SpringBootTest
class BeerServiceTest {
	
	@Mock
	BeerRepository beerRepository;
	
	@Autowired
	@InjectMocks
	BeerService beerService;
	
	
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


		List<Beer> beers = beerService.getBeers();
		assertEquals(2,beers.size());
	}
	
	@Test
	public void shouldReturnBeerCreatedId() {
		Beer beerToTestCreateBeerMethod = new Beer("Beer to test","Ingredients to test",
				   									"alcohol content to test",
				   									new BigDecimal(6.50),
				    								"Category to test");
		
		beerToTestCreateBeerMethod.setId(1);
		when(beerRepository.save(beerToTestCreateBeerMethod)).thenReturn(beerToTestCreateBeerMethod);
		
		assertEquals(1,beerService.createBeer(beerToTestCreateBeerMethod));
	}
	
	@Test
	public void shouldThrowExceptionWhenBeerNotExistsInGetBeerByIdMethod() {

		when(beerRepository.findById(10)).thenReturn(Optional.empty());
		
		assertThrows(BeerNotFoundException.class, 
					 () -> beerService.getBeerById(10),
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
					 () -> beerService.updateBeer(10,beerToTestUpdateBeerMethod),
				     "Beer not found");
	}
	
	@Test
	public void shouldThrowExceptionWhenBeerNotExistsInDeleteBeerMethod() {
		when(beerRepository.findById(10)).thenReturn(Optional.empty());
		
		assertThrows(BeerNotFoundException.class, 
				 () -> beerService.deleteBeer(10),
			     "Beer not found");
	}
	

}
