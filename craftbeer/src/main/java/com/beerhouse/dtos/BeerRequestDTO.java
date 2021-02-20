package com.beerhouse.dtos;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BeerRequestDTO extends BeerDTO{
	
	@NotNull(message = "Beer's name is required")
	private String name;
	
	@NotNull(message = "Beer's ingredients is required")
	private String ingredients;
	
	@NotNull(message = "Beer's alcoholContent is required")
	private String alcoholContent;
	
	@NotNull(message = "Beer's price is required")
	private BigDecimal price;
	
	@NotNull(message = "Beer's category is required")
	private BigDecimal category;
	
}
