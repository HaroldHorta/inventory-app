package com.company.storeapi.repositories.finances.expenses;

import com.company.storeapi.model.entity.finance.Expenses;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface  ExpensesRepository extends MongoRepository<Expenses, String> {
}
