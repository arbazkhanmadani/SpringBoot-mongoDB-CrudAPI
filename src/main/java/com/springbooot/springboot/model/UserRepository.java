package com.springbooot.springboot.model;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.springbooot.springboot.entity.User;

public interface UserRepository extends MongoRepository<User, ObjectId>{

	//Creating Custom method..........
	//Make sure -> method suffix name must be same as entity class's property name.
	public User getUserByUname(String uname);
	public String deleteByjorunalEntries(String jid);
	
	//Custom MongoTemplate Criteria query method....
	//"{'id',?0}" -> uname=fieldName, ?0 Zeroth placeholder.
	@Query("{'uname',?0}")
	public void updateByName(String uname);
	
}
