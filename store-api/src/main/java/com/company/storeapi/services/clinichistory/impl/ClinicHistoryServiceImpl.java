package com.company.storeapi.services.clinichistory.impl;

import com.company.storeapi.core.mapper.ClinichistoryMapper;
import com.company.storeapi.model.entity.ClinicExam;
import com.company.storeapi.model.entity.ClinicHistory;
import com.company.storeapi.model.entity.Pet;
import com.company.storeapi.model.entity.Veterinary;
import com.company.storeapi.model.enums.OptionClinicExam;
import com.company.storeapi.model.payload.request.clinichistory.*;
import com.company.storeapi.model.payload.response.clinicexam.ResponseClinicExam;
import com.company.storeapi.model.payload.response.clinichistory.ResponseClinicHistoryDTO;
import com.company.storeapi.repositories.clicexam.facade.ClinicExamRepositoryFacade;
import com.company.storeapi.repositories.clinichistory.facade.ClinicHistoryRepositoryFacade;
import com.company.storeapi.repositories.pet.facade.PetRepositoryFacade;
import com.company.storeapi.repositories.veterinary.facade.VeterinaryRepositoryFacade;
import com.company.storeapi.services.clinichistory.ClinicHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class ClinicHistoryServiceImpl implements ClinicHistoryService {

    private final ClinicHistoryRepositoryFacade clinicHistoryRepositoryFacade;
    private final VeterinaryRepositoryFacade veterinaryRepositoryFacade;
    private final PetRepositoryFacade petRepositoryFacade;
    private final ClinichistoryMapper clinichistoryMapper;
    private final ClinicExamRepositoryFacade clinicExamRepositoryFacade;


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


        Set<RequestAddClinicExamClinicHistory> clinicExamClinicHistories = new LinkedHashSet<>();

        requestAddClinicHistoryDTO.getClinicExam().getClinicExamClinicHistories().forEach(exam ->{

            RequestAddClinicExamClinicHistory requestAddClinicExamClinicHistory = new RequestAddClinicExamClinicHistory();

            ResponseClinicExam responseClinicExam = new ResponseClinicExam();

            ClinicExam clinicExam = clinicExamRepositoryFacade.validateAndGetClinicExamById(exam.getClinicExam().getId());
            responseClinicExam.setExam(clinicExam.getExam());

            requestAddClinicExamClinicHistory.setOptionClinicExam(exam.getOptionClinicExam());

            if(exam.getOptionClinicExam() == OptionClinicExam.ANORMAL){
                requestAddClinicExamClinicHistory.setObservation(exam.getObservation());

            }
            requestAddClinicExamClinicHistory.setClinicExam(responseClinicExam);

            clinicExamClinicHistories.add(requestAddClinicExamClinicHistory);

        });
        RequestClinicExamClinicHistory requestClinicExamClinicHistory = new RequestClinicExamClinicHistory();
        requestClinicExamClinicHistory.setAttitude(requestAddClinicHistoryDTO.getClinicExam().getAttitude());
        requestClinicExamClinicHistory.setBodyCondition(requestAddClinicHistoryDTO.getClinicExam().getBodyCondition());
        requestClinicExamClinicHistory.setStateDehydration(requestAddClinicHistoryDTO.getClinicExam().getStateDehydration());
        requestClinicExamClinicHistory.setClinicExamClinicHistories(clinicExamClinicHistories);

        ClinicHistory clinicHistory = new ClinicHistory();
        clinicHistory.setCreateAt(new Date());
        clinicHistory.setVeterinary(veterinary);
        clinicHistory.setPet(pet);
        clinicHistory.setPhysiologicalConstants(requestPhysiologicalConstants);
        clinicHistory.setReasonOfConsultation(requestAddClinicHistoryDTO.getReasonOfConsultation());
        clinicHistory.setAnamnesis(requestAddClinicHistoryDTO.getAnamnesis());
        clinicHistory.setRecipeBook(requestAddClinicHistoryDTO.getRecipeBook());
        clinicHistory.setClinicExam(requestClinicExamClinicHistory);


        return clinichistoryMapper.toClinichistoryDto(clinicHistoryRepositoryFacade.saveClinicHistory(clinicHistory));
    }

    @Override
    public ResponseClinicHistoryDTO updateClinicHistory(RequestUpdateClinicHistoryDTO requestUpdateClinicHistoryDTO) {
        return null;
    }
}
