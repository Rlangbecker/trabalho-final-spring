package com.vemser.geekers.repository;

import com.vemser.geekers.entity.EventoEntity;
import com.vemser.geekers.entity.LogMatchEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogMatchRepository extends MongoRepository<LogMatchEntity, String> {


}
