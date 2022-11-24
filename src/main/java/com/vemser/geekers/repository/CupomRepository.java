package com.vemser.geekers.repository;

import com.vemser.geekers.entity.CupomEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CupomRepository extends MongoRepository<CupomEntity, String> {
}
