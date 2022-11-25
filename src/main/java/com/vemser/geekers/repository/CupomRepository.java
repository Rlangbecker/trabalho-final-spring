package com.vemser.geekers.repository;

import com.vemser.geekers.entity.CupomEntity;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;
import java.util.List;

public interface CupomRepository extends MongoRepository<CupomEntity, String> {

@Aggregation(pipeline = {
            "{ '$match': { 'valor': { $gte: ?0 } } }"
    })
    List<CupomEntity> aggPorValor(Double valor);



}
