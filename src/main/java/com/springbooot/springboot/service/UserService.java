package com.springbooot.springboot.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import com.springbooot.springboot.entity.User;
import com.springbooot.springboot.model.UserRepository;

@Component
public class UserService{

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private User user;
	
	@Autowired
	private MongoTemplate mongoTemplate;
	

	
	public User insertUser(User user){
		//save()   -> save and update both
		//insert() -> only save.
		return userRepository.save(user);
	} 
	
	
	public void deleteUser(ObjectId id){
		userRepository.deleteById(id);;
	} 
	
	
	public void updateUser(ObjectId uid, User user){
		
		// Ensure that the user object is not null and has a valid password
	    if(user == null || user.getPass() == null)
	        throw new IllegalArgumentException("User or password cannot be null");

	    // Check if the user exists
	    User oldUser = userRepository.findById(uid).orElse(null);
	    if(oldUser == null) 
	        throw new NoSuchElementException("User not found");
	    
	    
	    // Create a query to find the document by _id
	    Query query = new Query(Criteria.where("_id").is(uid));

	    // Create an update object to set the new password
	    Update update = new Update();
	    update.set("pass", user.getPass());

	    // Perform the update operation
	    mongoTemplate.updateFirst(query, update, User.class);
     
	     //Insert if not exists, update if exists
	     //mongoTemplate.upsert(query, update, User.class);  
	     //Update all matching documents.	
	     //mongoTemplate.updateMulti(query, update, User.class); 
	}
	
	
	public void upsertUser(User user){
		
		// Create a query to find the document by _id
	    Query query = new Query(Criteria.where("_id").is(user.getId()));

	    // Create an update object to set the user's details and journal entries
	    Update update = new Update();
	    update.set("uname", user.getUname());
	    update.set("jorunalEntries", user.getJorunalEntries()); 

	    // Insert if not exists, update if exists
	    mongoTemplate.updateFirst(query, update, User.class);
	}
	
	
	public List<User> getAllUser(){
		return userRepository.findAll();
	} 
	
	
	public User getUser(ObjectId id){
		return userRepository.findById(id).orElse(null);
	} 
	
	
	public User getUserByName(String uname){
		return userRepository.getUserByUname(uname);
	}

	
	public String deleteByjorunalEntries(String jid){
		
		String deletedJid = userRepository.deleteByjorunalEntries(jid);
		return deletedJid;
	}
		
	
}
