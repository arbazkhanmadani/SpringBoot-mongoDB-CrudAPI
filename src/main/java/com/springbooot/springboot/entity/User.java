package com.springbooot.springboot.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import com.mongodb.lang.NonNull;

@Component
@Document(collection="user_entries")
public class User{

	@Id
	private ObjectId id;
	@Indexed(unique=true)
	@NonNull
	private String uname;
	@NonNull
	private String pass;
	private LocalDate dateOfsignIn;
	
	//user multiple entries[embading]
	@DBRef //JournalEntry Collection reference.
	private List<JournalEntry> jorunalEntries = new ArrayList<>();
	
	public List<JournalEntry> getJorunalEntries() {
		return jorunalEntries;
	}
	public void setJorunalEntries(List<JournalEntry> jorunalEntries) {
		this.jorunalEntries = jorunalEntries;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", uname=" + uname + ", pass=" + pass + ", dateOfsignIn=" + dateOfsignIn
				+ ", jorunalEntries=" + jorunalEntries + "]";
	}
	public ObjectId getId() {
		return id;
	}
	public void setId(ObjectId id) {
		this.id = id;
	}
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public LocalDate getDateOfsignIn() {
		return dateOfsignIn;
	}
	public void setDateOfsignIn(LocalDate dateOfsignIn) {
		this.dateOfsignIn = dateOfsignIn;
	}
}
