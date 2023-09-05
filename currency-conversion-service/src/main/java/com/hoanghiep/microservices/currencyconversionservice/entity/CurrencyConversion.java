package com.hoanghiep.microservices.currencyconversionservice.entity;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CurrencyConversion {

	private long id;
	
	private String from;
	
	private String to;
	
	private BigDecimal quantity;
	
	private BigDecimal conversionMultiple;
	
	private BigDecimal totalCalculatedAmount;
	
	private String environment;
}
