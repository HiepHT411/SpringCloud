package com.hoanghiep.resfulwebservices.controller;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.hoanghiep.resfulwebservices.entity.Post;
import com.hoanghiep.resfulwebservices.entity.User;
import com.hoanghiep.resfulwebservices.exception.UserNotFoundException;
import com.hoanghiep.resfulwebservices.repository.PostRepository;
import com.hoanghiep.resfulwebservices.repository.UserRepository;


import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/hoanghiep")
@Slf4j
public class UserController {
	
	@Autowired
	private UserRepository userRespository;
	
	@Autowired
	private PostRepository postRepository;
	
	@GetMapping("/users")
	public List<User> retrieveAllUsers(){
		
		return userRespository.findAll();
	}
	
	@GetMapping("/users/{id}")
	public EntityModel<User> retrieveUserById(@PathVariable int id){
		
		User user = userRespository.findById(id)
								.orElseThrow(() -> new UserNotFoundException("User NOT found id-"+id));
		
		EntityModel<User> model = EntityModel.of(user);
		
		WebMvcLinkBuilder linkToUser = linkTo(methodOn(this.getClass()).retrieveAllUsers());
		
		model.add(linkToUser.withRel("all-user"));
		return model;
	}
	
	@DeleteMapping("/users/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteUserById(@PathVariable int id) {
		User user = userRespository.findById(id)
									.orElseThrow(()-> new UserNotFoundException("User NOT found id-"+id));
		
		userRespository.delete(user);
		
		Map<String, Boolean> response = new HashMap<>();
		
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}
	
	@PostMapping("/users")
	public ResponseEntity<User> createUser(@Valid @RequestBody User user){
		log.info("User created");
		
		//return ResponseEntity.ok(userRespository.save(user));
		
		User savedUser = userRespository.save(user);
		
		//get URI of created user
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
							.buildAndExpand(savedUser.getId()).toUri();
		
		return ResponseEntity.created(location).build();
	}
	
	@PutMapping("/users/{id}")
	public ResponseEntity<User> updateUser(@RequestBody User updatedUser, @PathVariable int id){
		User oldUser = userRespository.findById(id)
				.orElseThrow(()-> new UserNotFoundException("User NOT found id-"+id));
		
		oldUser.setName(updatedUser.getName());
		oldUser.setBod(updatedUser.getBod());
		
		return ResponseEntity.ok(userRespository.save(oldUser));
	}
	//Example of dynamic filtering
	@GetMapping("/filtering")
	public MappingJacksonValue exampleFiltering() {
		
		User someObject = new User();
		
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("name");
		
		FilterProvider filterProvide = new SimpleFilterProvider()
											.addFilter("someBeanDynamicFilterName", filter);
		
		MappingJacksonValue mapping = new MappingJacksonValue(someObject);
		
		mapping.setFilters(filterProvide);
		
		return mapping;
	}
	
	//retrieve all posts for a specific user
	@GetMapping("user/{id}/posts")
	public List<Post> retrieveAllPostsForASpecificUser(@PathVariable int id){
		User user = userRespository.findById(id)
				.orElseThrow(()-> new UserNotFoundException("User NOT found id-"+id));
		
		return user.getPost();
	}
	
	@PostMapping("/users/{id}/posts")
	public ResponseEntity<User> createUserPost(@RequestBody Post post, @PathVariable int userId){
		log.info("Post created user.id-"+userId);
		
		User user = userRespository.findById(userId)
								.orElseThrow(()-> new UserNotFoundException("User not found id-"+userId));
		
		post.setUser(user);
		
		Post savedPost = postRepository.save(post);
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
								.buildAndExpand(savedPost.getId()).toUri();
		
		
		
		return ResponseEntity.created(location).build();
	}
}
