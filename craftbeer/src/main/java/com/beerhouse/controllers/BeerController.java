package com.beerhouse.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.beerhouse.dtos.BeerRequestDTO;
import com.beerhouse.entities.Beer;
import com.beerhouse.services.BeerService;

@RestController
public class BeerController {
	
	@Autowired
	private BeerService beerService;
	
	@GetMapping("/beers")
	public ResponseEntity<?> beers(){
		List<Beer> beers = beerService.getBeers();
		return new ResponseEntity<>(beers, HttpStatus.OK);
	}
	
	@GetMapping("/beers/:id")
	public ResponseEntity<?> beer(@PathVariable int id){
			Beer beer = beerService.getBeerById(id);
			return ResponseEntity.ok()
								 .body(beer);
	}
	
    @PostMapping(value = "/beers")
	public ResponseEntity<?> createBeer(@RequestBody @Valid BeerRequestDTO body){
			Beer beerCreated = beerService.createBeer(body.convertToBeer());
			return ResponseEntity.ok()
								 .header("Location","/beers/" + beerCreated.getId())
								 .body("");
	}
	
	@PutMapping("/beers/{id}")
	public ResponseEntity<?> updateBeer(@PathVariable int id,@RequestBody BeerRequestDTO body){
			System.out.println(id);
			beerService.updateBeer(id, body.convertToBeer());
			return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@DeleteMapping("/beers/{id}")
	public ResponseEntity<?> deleteBeer(@PathVariable int id){
		System.out.println(id);
			beerService.deleteBeer(id);
			return new ResponseEntity<>(HttpStatus.OK);
	}
}
