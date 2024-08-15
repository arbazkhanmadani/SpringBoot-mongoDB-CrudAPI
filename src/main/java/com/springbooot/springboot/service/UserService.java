package com.springbooot.springboot.service;

import java.util.List;

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
		return userRepository.insert(user);
	} 
	
	
	public void deleteUser(ObjectId id){
		userRepository.deleteById(id);;
	} 
	
	
	public void updateUser(ObjectId uid, User user){
		
		 Query query = new Query(Criteria.where("id").is(user.getId()));
		 
	     Update update = new Update();
	     update.set("name", user.getUname());
	     update.set("pass", user.getPass());
	     
	     //Insert if not exists, update if exists
	     mongoTemplate.upsert(query, update, User.class); 
	     
	     //Update First matching document.
	     //mongoTemplate.updateFirst(query, update, User.class);  
	     //Update all matching documents.	
	     //mongoTemplate.updateMulti(query, update, User.class); 
	}
	
	
	public void upsertUser(User user){
		
		 Query query = new Query(Criteria.where("id").is(user.getId()));
		 
	     Update update = new Update();
	     
	     //Insert if not exists, update if exists
	     mongoTemplate.upsert(query, update, User.class); 
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
