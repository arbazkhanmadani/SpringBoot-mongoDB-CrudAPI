package com.springbooot.springboot.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springbooot.springboot.entity.JournalEntry;
import com.springbooot.springboot.entity.User;
import com.springbooot.springboot.service.JournalEntryService;
import com.springbooot.springboot.service.UserService;

@RestController
@RequestMapping("/journal")
public class JournalController{

	@Autowired
	private JournalEntryService journalEntryService;  
	
	@Autowired
	private UserService userService;  
	
	
	//===============Entry==========================
	@PostMapping("/add-entry/username/{uname}")
	public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry journalEntry, @PathVariable String uname){
		
		journalEntry.setDate(LocalDate.now());
		JournalEntry je = journalEntryService.saveEntry(journalEntry,uname);
		
		return ResponseEntity.ok(je);
	}
	
	
	@DeleteMapping("/delete-entry/by-journalId/{jid}/{uname}")
	public ResponseEntity deleteEntry(@PathVariable String jid, @PathVariable String uname){
		
		//getting user and deleting common entry.
		User user = userService.getUserByName(uname);
		user.getJorunalEntries().removeIf(x -> x.getId().equals(jid));
		userService.insertUser(user);
		
		//Deleting entry in journal................
		journalEntryService.deleteEntry(jid,uname);
		return ResponseEntity.ok(HttpStatus.OK);
	}
	
	
	@PutMapping("/update-entry/id/{uid}")
	public ResponseEntity<JournalEntry> updateEntry(@RequestBody JournalEntry newEntry, @PathVariable String uid){
		
		JournalEntry oldEntry = journalEntryService.getEntry(uid);
		
		if(oldEntry!=null){
			//If user only update the Title.....
			oldEntry.setTitle( newEntry.getTitle()!=null && !newEntry.getTitle().equals("") ? newEntry.getTitle() : oldEntry.getTitle() );
			//If user only update the Content...
			oldEntry.setContent( newEntry.getContent()!=null && !newEntry.getContent().equals("") ? newEntry.getContent(): oldEntry.getContent() );
			journalEntryService.updateEntry(oldEntry);
			
			return new ResponseEntity<JournalEntry>(oldEntry,HttpStatus.OK);
					
		}
		
		return new ResponseEntity<JournalEntry>(HttpStatus.NOT_FOUND);
	}
	
	
	@PostMapping("/get-entry/username/{uname}")
	public ResponseEntity<List<JournalEntry>> getEntry(@PathVariable String uname){
		
		User user = userService.getUserByName(uname);
		System.out.println(user);
		List<JournalEntry> je = user.getJorunalEntries();
		for(JournalEntry j : je){
			System.out.println(je.size()+" "+j);
		}
		return ResponseEntity.ok(je);
	}
	
	
	@PostMapping("/getall-entry") 
	public ResponseEntity<List<JournalEntry>> getAllEntry(){
		
		List<JournalEntry> je = journalEntryService.getAllEntry();
		
		if(je.isEmpty() || je==null) return new ResponseEntity(HttpStatus.NOT_FOUND);
		return ResponseEntity.ok(je);
	}
	

	


}
