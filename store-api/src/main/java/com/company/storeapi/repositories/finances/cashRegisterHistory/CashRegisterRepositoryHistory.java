package com.company.storeapi.repositories.finances.cashRegisterHistory;

import com.company.storeapi.model.entity.finance.CashRegisterHistory;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CashRegisterRepositoryHistory extends MongoRepository<CashRegisterHistory, String> {
}
