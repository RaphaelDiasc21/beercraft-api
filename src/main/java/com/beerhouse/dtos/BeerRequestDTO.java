package com.beerhouse.dtos;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import com.beerhouse.entities.Beer;

public class BeerRequestDTO{
	
	@NotNull(message = "Beer's name is required")
	private String name;
	
	@NotNull(message = "Beer's ingredients is required")
	private String ingredients;
	
	@NotNull(message = "Beer's alcoholContent is required")
	private String alcoholContent;
	
	@NotNull(message = "Beer's price is required")
	private BigDecimal price;
	
	@NotNull(message = "Beer's category is required")
	private String category;
	
	
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getIngredients() {
		return ingredients;
	}


	public void setIngredients(String ingredients) {
		this.ingredients = ingredients;
	}


	public String getAlcoholContent() {
		return alcoholContent;
	}


	public void setAlcoholContent(String alcoholContent) {
		this.alcoholContent = alcoholContent;
	}


	public BigDecimal getPrice() {
		return price;
	}


	public void setPrice(BigDecimal price) {
		this.price = price;
	}


	public String getCategory() {
		return category;
	}


	public void setCategory(String category) {
		this.category = category;
	}


	public Beer convertToBeer() {
		return new Beer(name,ingredients,alcoholContent,price,category);
	}
	
}
