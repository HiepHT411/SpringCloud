package com.hoanghiep.resfulwebservices.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
//@JsonIgnoreProperties(value = {"name","dob"})			//static filtering
//@JsonFilter("someBeanDynamicFilterName")				//dynamic filter
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	
	@Size(min = 8, message = "Name should have at least 8 characters")
	private String name;
	
	//@JsonIgnore				// static filtering
	@Past
	private Date bod;
	
	@OneToMany(mappedBy = "user")
	private List<Post> post;
	
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", bod=" + bod + "]";
	}
	
}
