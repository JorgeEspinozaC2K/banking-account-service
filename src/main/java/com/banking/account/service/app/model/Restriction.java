package com.banking.account.service.app.model;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "restrictions")
public class Restriction {
	
	@Id
	private String id;
	
	private String name;
	
	private Integer knownAs;
	
	private Boolean offer;
	
	private Double maintCommission;
	
	private Boolean hasMaxMove;
	
	private Integer maxMovements;
	
	private Boolean hasSpecificDay;
	
	private Boolean haveExpirationDate;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate expirationDay = null;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate createAt;
}
