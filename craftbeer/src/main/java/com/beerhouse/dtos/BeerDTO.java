package com.beerhouse.dtos;

import java.math.BigDecimal;

import com.beerhouse.entities.Beer;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BeerDTO {
	protected int id;
	protected String name;
	protected String ingredients;
	protected String alcoholContent;
	protected BigDecimal price;
	protected String category;
	
	public Beer convertToBeer() {
		return new Beer(name,ingredients,alcoholContent,price,category);
	}
}
