package com.company.storeapi.repositories.finances.creditCapital.facade.impl;

import com.company.storeapi.model.entity.CreditCapital;
import com.company.storeapi.repositories.finances.creditCapital.CreditCapitalRepository;
import com.company.storeapi.repositories.finances.creditCapital.facade.CreditCapitalRepositoryFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class CreditCapitalRepositoryFacadeImpl implements CreditCapitalRepositoryFacade {

    private final CreditCapitalRepository creditCapitalRepository;

    @Override
    public List<CreditCapital> findCreditCapitalByTicket(String idTicket) {
        return creditCapitalRepository.findCreditCapitalByIdTicket(idTicket);
    }

    @Override
    public CreditCapital saveCreditCapital(CreditCapital creditCapital) {
        return creditCapitalRepository.save(creditCapital);
    }
}
