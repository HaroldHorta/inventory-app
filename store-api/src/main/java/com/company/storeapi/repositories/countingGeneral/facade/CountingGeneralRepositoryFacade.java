package com.company.storeapi.repositories.countingGeneral.facade;

import com.company.storeapi.model.entity.CountingGeneral;

import java.util.List;

public interface CountingGeneralRepositoryFacade {

    List<CountingGeneral> getAllCountingGeneral();
    CountingGeneral saveCountingGeneral(CountingGeneral countingGeneral);

    CountingGeneral validateCountingGeneral(String id);
}
