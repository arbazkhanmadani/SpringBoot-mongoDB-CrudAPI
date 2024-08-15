package com.springbooot.springboot.controller;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springbooot.springboot.entity.User;
import com.springbooot.springboot.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController{

	
	@Autowired
	private UserService userService;  
	

	//===============User==========================
	@PostMapping("/add-user")
	public ResponseEntity<User> createUser(@RequestBody User user){
		
		user.setDateOfsignIn(LocalDate.now());
		User savedUser = userService.insertUser(user);
		return new ResponseEntity<User>(savedUser, HttpStatus.CREATED);
	}
	
	
	@GetMapping("/get-user/{uid}")
	public ResponseEntity<User> getUser(@PathVariable ObjectId uid){
		
		User savedUser = userService.getUser(uid);
		return new ResponseEntity<User>(savedUser, HttpStatus.OK);
	}
	
	
	@GetMapping("/getall-user")
	public ResponseEntity<List<User>> getAllUser(){
		
		List<User> savedUser = userService.getAllUser();
		return new ResponseEntity<List<User>>(savedUser, HttpStatus.OK);
	}
	
	
	@DeleteMapping("/delete-user/{uid}")
	public ResponseEntity deleteUser(@PathVariable ObjectId uid){
		
		userService.deleteUser(uid);
		return new ResponseEntity(HttpStatus.OK);
	}
	
	
	@PutMapping("/update-user/{uid}")
	public ResponseEntity updateUser(@RequestBody User user, @PathVariable ObjectId uid){
		
		userService.updateUser(uid, user);
		return new ResponseEntity(HttpStatus.OK);
	}
	
		
}
