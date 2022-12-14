package com.vemser.geekers.repository;

import com.vemser.geekers.entity.LogMatchEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LogMatchRepository extends MongoRepository<LogMatchEntity, String> {

    List<LogMatchEntity> findByData(String data);

}
