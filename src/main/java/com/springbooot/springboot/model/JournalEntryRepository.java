package com.springbooot.springboot.model;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.springbooot.springboot.entity.JournalEntry;

public interface JournalEntryRepository extends MongoRepository<JournalEntry, String>{

}
