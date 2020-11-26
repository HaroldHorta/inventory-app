package com.company.storeapi.repositories.finances.cashRegister;

import com.company.storeapi.model.entity.finance.CashRegisterHistory;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CashRegisterRepository extends MongoRepository<CashRegisterHistory, String> {
}
