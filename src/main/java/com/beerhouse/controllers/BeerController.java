package com.beerhouse.controllers;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beerhouse.dtos.BeerRequestDTO;
import com.beerhouse.entities.Beer;
import com.beerhouse.services.BeerService;
import com.github.fge.jsonpatch.JsonPatch;

@RestController
@RequestMapping("/beers")
public class BeerController {
	
	@Autowired
	private BeerService beerService;
	
	@GetMapping
	public ResponseEntity<List<Beer>> beers(){
		List<Beer> beers = beerService.getBeers();
		return new ResponseEntity<>(beers, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Beer> beer(@PathVariable Integer id){
			Beer beer = beerService.getBeerById(id);
			return ResponseEntity.ok()
								 .body(beer);
	}
	
    @PostMapping
	public ResponseEntity<String> createBeer(@RequestBody @Valid BeerRequestDTO body){
			Beer beerCreated = beerService.createBeer(body.convertToBeer());
			return ResponseEntity.status(HttpStatus.CREATED)
								 .header("Location","/beers/" + beerCreated.getId())
								 .body("");
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Void> updateBeer(@PathVariable int id,@RequestBody @Valid BeerRequestDTO body){
			beerService.updateCompleteBeer(id, body.convertToBeer());
			return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PatchMapping("/{id}")
	public ResponseEntity<Void> patchbeer(@PathVariable int id, @RequestBody JsonPatch jsonPatch){
		beerService.updatePatiallyBeer(id, jsonPatch);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteBeer(@PathVariable int id){
		System.out.println(id);
			beerService.deleteBeer(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
