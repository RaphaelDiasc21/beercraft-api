package com.beerhouse.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.beerhouse.dtos.BeerRequestDTO;
import com.beerhouse.dtos.BeerResponseDTO;
import com.beerhouse.entities.Beer;
import com.beerhouse.exceptions.BeerNotFoundException;
import com.beerhouse.services.BeerService;

@RestController
public class BeerController {
	
	@Autowired
	private BeerService beerService;
	
	@GetMapping("/beers")
	public ResponseEntity<?> beers(){
		List<Beer> beers = beerService.getBeers();
		
		BeerResponseDTO beerResponseDTO = new BeerResponseDTO();
		List<BeerResponseDTO> beerReponse = beerResponseDTO.coinvertToBeerReponseDTOList(beers);
		
		return new ResponseEntity<>(beerReponse, HttpStatus.OK);
	}
	
	@GetMapping("/beers/:id")
	public ResponseEntity<?> beer(@PathVariable int id){
		try {
			Beer beer = beerService.getBeerById(id);
			
			BeerResponseDTO beerResponseDTO = new BeerResponseDTO();
			BeerResponseDTO beerResponse = beerResponseDTO.convertToBeerResponseDTO(beer);
			return new ResponseEntity<BeerResponseDTO>(beerResponse,HttpStatus.OK);
		}catch(BeerNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}	
	}
	
    @PostMapping(value = "/beers")
	public ResponseEntity<?> createBeer(@RequestBody @Valid BeerRequestDTO body){
	
			Beer beer = body.convertToBeer();
			int beerCreatedId = beerService.createBeer(beer);
			
			HttpHeaders headers = new HttpHeaders();
			headers.add(HttpHeaders.LOCATION, "/beer/"+beerCreatedId);
			
			return ResponseEntity.ok()
								 .headers(headers).body("");
	    }
	
	@PutMapping("/beers/:id")
	public ResponseEntity<?> updateBeer(@PathVariable int id,@RequestBody BeerRequestDTO body){
		Beer beer = body.convertToBeer();
		try {
			beerService.updateBeer(id, beer);
			return new ResponseEntity<>(HttpStatus.OK);
		}catch(BeerNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping("/beers/:id")
	public ResponseEntity<?> deleteBeer(@PathVariable int id){
		try {
			beerService.deleteBeer(id);
			return new ResponseEntity<>(HttpStatus.OK);
		}catch(BeerNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
