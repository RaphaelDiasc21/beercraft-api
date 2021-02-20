package com.beerhouse.dtos;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.beerhouse.entities.Beer;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BeerResponseDTO extends BeerDTO {
	
	public BeerResponseDTO() {
		
	}
	public BeerResponseDTO(int id, String name, String ingredients, String alcoholContent, BigDecimal price, String category) {
		this.id = id;
		this.name = name;
		this.ingredients = ingredients;
		this.alcoholContent = alcoholContent;
		this.price = price;
		this.category = category;
	}
	
	public List<BeerResponseDTO> coinvertToBeerReponseDTOList(List<Beer> beers){
		return beers.stream()
				.map(beer -> new BeerResponseDTO(
							beer.getId(),
							beer.getName(),
							beer.getIngredients(),
							beer.getAlcoholContent(),
							beer.getPrice(),
							beer.getCategory()
						)).collect(Collectors.toList());
	}
	
	public BeerResponseDTO convertToBeerResponseDTO(Beer beer) {
		return new BeerResponseDTO(beer.getId(),
								   beer.getName(),
								   beer.getIngredients(),
								   beer.getAlcoholContent(),
								   beer.getPrice(),
								   beer.getCategory()
								  );
	}
}
