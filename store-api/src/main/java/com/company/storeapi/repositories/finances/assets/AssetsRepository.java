package com.company.storeapi.repositories.finances.assets;

import com.company.storeapi.model.entity.finance.Assets;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AssetsRepository extends MongoRepository<Assets, String> {

}
