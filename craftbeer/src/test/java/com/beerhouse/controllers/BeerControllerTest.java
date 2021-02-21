package com.beerhouse.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import javax.json.Json;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.beerhouse.dtos.BeerRequestDTO;
import com.beerhouse.entities.Beer;
import com.beerhouse.exceptions.BeerNotFoundException;
import com.beerhouse.services.BeerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;



@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = BeerController.class)
class BeerControllerTest {
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean
	private BeerService beerService;
	
	@Test
	void testGetRequestToFindBeers() throws Exception {
		Beer beerToMockOne = generateBeerToMock(1,
							"Beer to mock test one","Ingredients to mock test one",
							"alcohol content to mock test one",
							new BigDecimal(5.50),
							"Category to mock test one");
		
		Beer beerToMockTwo = generateBeerToMock(1,
							"Beer to mock test one","Ingredients to mock test two",
							"alcohol content to mock test two",
							new BigDecimal(5.50),
							"Category to mock test two");

		List<Beer> beersToMock = Arrays.asList(beerToMockOne,beerToMockTwo);

		when(beerService.getBeers()).thenReturn(beersToMock);
		
		MvcResult mvcResult = mockMvc.perform(get("/beers")).andReturn();
		
		assertEquals(200, mvcResult.getResponse().getStatus());
		String content = mvcResult.getResponse().getContentAsString();
		
		Beer[] beersArray = objectMapper.readValue(content, Beer[].class);

		assertEquals(2, beersArray.length);
	}
	
	@Test
	void testPostRequestToCreateBeer() throws Exception {

		BeerRequestDTO beerRequestDTO = generateBeerRequestDTO();
		
		when(beerService.createBeer(Mockito.any(Beer.class))).thenReturn(beerRequestDTO.convertToBeer());
		String beerJson = objectMapper.writeValueAsString(beerRequestDTO);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/beers")
															  .accept(MediaType.APPLICATION_JSON)
															  .contentType(MediaType.APPLICATION_JSON)
															  .content(beerJson);
		
		MvcResult mvcResultSuccessfulPerform = mockMvc.perform(requestBuilder).andReturn();
		assertEquals(201, mvcResultSuccessfulPerform.getResponse().getStatus());
		
		//Perform post request without passing the require data
		MvcResult mvcResultFailedPerform = mockMvc.perform(post("/beers")).andReturn();
		assertEquals(400, mvcResultFailedPerform.getResponse().getStatus());
		
	}
	
	@Test
	void testGetRequestToGetBeerById() throws Exception {
		when(beerService.getBeerById(1)).thenThrow(BeerNotFoundException.class);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/beers/{id}",1)
				  											  .accept(MediaType.APPLICATION_JSON);
			
		MvcResult mvcResultFailedPerform = mockMvc.perform(requestBuilder).andReturn();
		assertEquals(404, mvcResultFailedPerform.getResponse().getStatus());
		assertTrue(mvcResultFailedPerform.getResolvedException() instanceof BeerNotFoundException);
		
		Beer beerToMock = generateBeerToMock(2,
						  "Beer to mock test one","Ingredients to mock test one",
						  "alcohol content to mock test one",
						  new BigDecimal(5.50),
						  "Category to mock test one");
		

		
		when(beerService.getBeerById(2)).thenReturn(beerToMock);
	
		requestBuilder = MockMvcRequestBuilders.get("/beers/{id}",2)
				  .accept(MediaType.APPLICATION_JSON);
		
		MvcResult mvcResultSuccessPerform = mockMvc.perform(requestBuilder).andReturn();
		
		String beerJson = mvcResultSuccessPerform.getResponse().getContentAsString();
		Beer beer = objectMapper.readValue(beerJson, Beer.class);
		
		assertEquals(200, mvcResultSuccessPerform.getResponse().getStatus());
		assertEquals(beerToMock.getName(), beer.getName());
	
	}
	
	@Test
	void testPutRequestToUpdateBeer() throws Exception {
		
		BeerRequestDTO beerRequestDTO = generateBeerRequestDTO();
		String beerJson = objectMapper.writeValueAsString(beerRequestDTO);
		
		doThrow(new BeerNotFoundException("Beer not found"))
			.when(beerService)
			.updateCompleteBeer(Mockito.eq(1), Mockito.any(Beer.class));
		
		doNothing()
			.when(beerService)
			.updateCompleteBeer(Mockito.eq(2), Mockito.any(Beer.class));
	
		RequestBuilder requestBuilderToFailure = MockMvcRequestBuilders.put("/beers/{id}",1)
				  .accept(MediaType.APPLICATION_JSON)	
				  .contentType(MediaType.APPLICATION_JSON)
				  .content(beerJson);
		
		RequestBuilder requestBuilderToSuccess = MockMvcRequestBuilders.put("/beers/{id}",2)
				  .accept(MediaType.APPLICATION_JSON)	
				  .contentType(MediaType.APPLICATION_JSON)
				  .content(beerJson);
		
		MvcResult mvcResultFailedPerform = mockMvc.perform(requestBuilderToFailure).andReturn();
		assertEquals(404, mvcResultFailedPerform.getResponse().getStatus());
		assertTrue(mvcResultFailedPerform.getResolvedException() instanceof BeerNotFoundException);
		
		MvcResult mvcResultSuccessPerform = mockMvc.perform(requestBuilderToSuccess).andReturn();
		assertEquals(200, mvcResultSuccessPerform.getResponse().getStatus());	
	}
	
	@Test
	void testPatchRequestToUpdateBeer() throws Exception {
		
		String jsonObject = "[\n" + 
				"   {\"op\":\"replace\",\"path\":\"/ingredients\",\"value\":\"LUPULO\"}\n" + 
				"]";
		
		doThrow(new BeerNotFoundException("Beer not found"))
			.when(beerService)
			.updatePatiallyBeer(Mockito.eq(1), Mockito.any(JsonPatch.class));
		
		doNothing()
			.when(beerService)
			.updatePatiallyBeer(Mockito.eq(2), Mockito.any(JsonPatch.class));
	
		RequestBuilder requestBuilderToFailure = MockMvcRequestBuilders.patch("/beers/{id}",1)
				  .accept(MediaType.APPLICATION_JSON)	
				  .contentType(MediaType.APPLICATION_JSON)
				  .content(jsonObject);
		
		RequestBuilder requestBuilderToSuccess = MockMvcRequestBuilders.patch("/beers/{id}",2)
				  .accept(MediaType.APPLICATION_JSON)	
				  .contentType(MediaType.APPLICATION_JSON)
				  .content(jsonObject);
		
		MvcResult mvcResultFailedPerform = mockMvc.perform(requestBuilderToFailure).andReturn();
		assertEquals(404, mvcResultFailedPerform.getResponse().getStatus());
		assertTrue(mvcResultFailedPerform.getResolvedException() instanceof BeerNotFoundException);
		
		MvcResult mvcResultSuccessPerform = mockMvc.perform(requestBuilderToSuccess).andReturn();
		assertEquals(200, mvcResultSuccessPerform.getResponse().getStatus());	
	}
	
	@Test
	void testDeleteRequestToDeleteBeer() throws Exception {
				
		doThrow(new BeerNotFoundException("Beer not found"))
			.when(beerService)
			.deleteBeer(Mockito.eq(1));
		
		doNothing()
			.when(beerService)
			.deleteBeer(Mockito.eq(2));
	
		RequestBuilder requestBuilderToFailure = MockMvcRequestBuilders.delete("/beers/{id}",1)
				  .accept(MediaType.APPLICATION_JSON);	
		
		RequestBuilder requestBuilderToSuccess = MockMvcRequestBuilders.delete("/beers/{id}",2)
				  .accept(MediaType.APPLICATION_JSON);
		
		MvcResult mvcResultFailedPerform = mockMvc.perform(requestBuilderToFailure).andReturn();
		assertEquals(404, mvcResultFailedPerform.getResponse().getStatus());
		assertTrue(mvcResultFailedPerform.getResolvedException() instanceof BeerNotFoundException);
		
		MvcResult mvcResultSuccessPerform = mockMvc.perform(requestBuilderToSuccess).andReturn();
		assertEquals(200, mvcResultSuccessPerform.getResponse().getStatus());	
	}
	
	
	private Beer generateBeerToMock(int beerId,
									String beerName,
									String beerIngredients,
									String beerAlcoholContent,
									BigDecimal beerPrice,
									String beerCategory
									) {
		
		Beer beerToMock = new Beer();
		beerToMock.setId(beerId);
		beerToMock.setName(beerName);
		beerToMock.setIngredients(beerIngredients);
		beerToMock.setAlcoholContent(beerAlcoholContent);
		beerToMock.setPrice(beerPrice);
		beerToMock.setCategory(beerCategory);
		
		return beerToMock;
	}
	
	private BeerRequestDTO generateBeerRequestDTO() {
		BeerRequestDTO beerRequestDTO = new BeerRequestDTO();
		beerRequestDTO.setName("Teste beer");
		beerRequestDTO.setIngredients("Teste beer");
		beerRequestDTO.setAlcoholContent("Teste beer");
		beerRequestDTO.setCategory("Teste beer");
		beerRequestDTO.setPrice(new BigDecimal(10.0));
		
		return beerRequestDTO;
	}

}
