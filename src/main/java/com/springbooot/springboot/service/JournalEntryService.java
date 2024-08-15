package com.springbooot.springboot.service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.springbooot.springboot.entity.JournalEntry;
import com.springbooot.springboot.entity.User;
import com.springbooot.springboot.model.JournalEntryRepository;
import com.springbooot.springboot.model.UserRepository;

@Component
public class JournalEntryService{

	@Autowired
	private JournalEntryRepository journalEntryRepository;
	@Autowired
	private UserService userService;
	@Autowired
	private UserRepository userRepo;
	
	
	//Save in collection.....................................
	public JournalEntry saveEntry(JournalEntry journalEntry, String uname){
		
		//Journal Saving
		journalEntry.setDate(LocalDate.now());
		JournalEntry je = journalEntryRepository.save(journalEntry);
		
		// Fetch user and add the journal entry
	    User user = userService.getUserByName(uname);
	    if (user != null) {
	        user.getJorunalEntries().add(je); 

	        // Upsert user with updated journal entries
	        userService.upsertUser(user);
	    } else {
	        throw new NoSuchElementException("User not found");
	    }
	    return je;
	}
	

	//Deleting from collection.................
	 @Transactional
	public void deleteEntry(String id, String uname){
		
		//Deleting Journal from JournalEntry
		JournalEntry jeu = journalEntryRepository.findById(id).orElse(null);
		journalEntryRepository.deleteById(id);
		System.out.println(jeu.toString());
		
		//Deleting journalID from User
		List<User> users = userRepo.findAll();
        for(User user : users){

        	//here the !entry.getId().equals(id)) -> would be removed.
        	List<JournalEntry> updatedEntries = user.getJorunalEntries()
        		.stream()
        		.filter(entry -> !entry.getId().equals(id))
                .collect(Collectors.toList());

            user.setJorunalEntries(updatedEntries);
            
            //except removed Id all are updated.
            userRepo.save(user);
        }
    }
	

	
	//Updating from collection.................
	public JournalEntry updateEntry(JournalEntry journalEntry){
			
		JournalEntry jeu = journalEntryRepository.save(journalEntry);
		return jeu;
	}
		

	//Getting All from collection..................
	public List<JournalEntry> getAllEntry(){
		
		List<JournalEntry> jeu = journalEntryRepository.findAll();
		System.out.println(jeu.toString());
		return jeu;
	}
	
	//Getting 1 from collection..................
	public JournalEntry getEntry(String id){
			
		JournalEntry jeu = journalEntryRepository.findById(id).orElse(null);
		System.out.println(jeu.toString());
		return jeu;
	}


	public void saveEntry(JournalEntry oldEntry) {
		journalEntryRepository.save(oldEntry);	
	}


	
}
