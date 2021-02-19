package com.company.storeapi.services.clinichistory.impl;

import com.company.storeapi.core.mapper.ClinichistoryMapper;
import com.company.storeapi.model.entity.ClinicHistory;
import com.company.storeapi.model.entity.Pet;
import com.company.storeapi.model.entity.Veterinary;
import com.company.storeapi.model.payload.request.clinichistory.RequestAddClinicHistoryDTO;
import com.company.storeapi.model.payload.request.clinichistory.RequestPhysiologicalConstants;
import com.company.storeapi.model.payload.request.clinichistory.RequestUpdateClinicHistoryDTO;
import com.company.storeapi.model.payload.response.clinichistory.ResponseClinicHistoryDTO;
import com.company.storeapi.repositories.clinichistory.facade.ClinicHistoryRepositoryFacade;
import com.company.storeapi.repositories.pet.facade.PetRepositoryFacade;
import com.company.storeapi.repositories.veterinary.facade.VeterinaryRepositoryFacade;
import com.company.storeapi.services.clinichistory.ClinicHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@RequiredArgsConstructor
@Service
public class ClinicHistoryServiceImpl implements ClinicHistoryService {

    private final ClinicHistoryRepositoryFacade clinicHistoryRepositoryFacade;
    private final VeterinaryRepositoryFacade veterinaryRepositoryFacade;
    private final PetRepositoryFacade petRepositoryFacade;
    private final ClinichistoryMapper clinichistoryMapper;


    @Override
    public ResponseClinicHistoryDTO validateAndGetClinicHistoryById(String id) {
        return clinichistoryMapper.toClinichistoryDto(clinicHistoryRepositoryFacade.validateAndGetClinicHistoryById(id));
    }

    @Override
    public ResponseClinicHistoryDTO saveClinicHistory(RequestAddClinicHistoryDTO requestAddClinicHistoryDTO) {
        Veterinary veterinary = veterinaryRepositoryFacade.validateAndGetVeterinaryById(requestAddClinicHistoryDTO.getVeterinary());
        Pet pet = petRepositoryFacade.validateAndGetPetById(requestAddClinicHistoryDTO.getPet());

        RequestPhysiologicalConstants requestPhysiologicalConstants = new RequestPhysiologicalConstants();
        requestPhysiologicalConstants.setCapillaryFillTime(requestAddClinicHistoryDTO.getPhysiologicalConstants().getCapillaryFillTime());
        requestPhysiologicalConstants.setHeartRate(requestAddClinicHistoryDTO.getPhysiologicalConstants().getHeartRate());
        requestPhysiologicalConstants.setRespiratoryFrequency(requestAddClinicHistoryDTO.getPhysiologicalConstants().getRespiratoryFrequency());
        requestPhysiologicalConstants.setPulse(requestAddClinicHistoryDTO.getPhysiologicalConstants().getPulse());
        requestPhysiologicalConstants.setTemperature(requestAddClinicHistoryDTO.getPhysiologicalConstants().getTemperature());
        requestPhysiologicalConstants.setWeight(requestAddClinicHistoryDTO.getPhysiologicalConstants().getWeight());

        ClinicHistory clinicHistory = new ClinicHistory();
        clinicHistory.setCreateAt(new Date());
        clinicHistory.setVeterinary(veterinary);
        clinicHistory.setPet(pet);
        clinicHistory.setPhysiologicalConstants(requestPhysiologicalConstants);
        clinicHistory.setReasonOfConsultation(requestAddClinicHistoryDTO.getReasonOfConsultation());
        clinicHistory.setAnamnesis(requestAddClinicHistoryDTO.getAnamnesis());
        clinicHistory.setRecipeBook(requestAddClinicHistoryDTO.getRecipeBook());


        return clinichistoryMapper.toClinichistoryDto(clinicHistoryRepositoryFacade.saveClinicHistory(clinicHistory));
    }

    @Override
    public ResponseClinicHistoryDTO updateClinicHistory(RequestUpdateClinicHistoryDTO requestUpdateClinicHistoryDTO) {
        return null;
    }
}
