package com.company.storeapi.services.countingGeneral;

import com.company.storeapi.model.entity.CountingGeneral;
import com.company.storeapi.model.enums.OrderStatus;

import java.util.List;

public interface CountingGeneralService {

    List<CountingGeneral> getAllCountingGeneral();

    CountingGeneral saveCountingGeneral(CountingGeneral countingGeneral);

    void counting (String idOrder, OrderStatus orderStatus);

    CountingGeneral validateCountingGeneral(String id);

}
