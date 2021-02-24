package com.company.storeapi.services.clinichistory.impl;

import com.company.storeapi.core.exceptions.enums.LogRefServices;
import com.company.storeapi.core.exceptions.persistence.DataNotFoundPersistenceException;
import com.company.storeapi.model.entity.*;
import com.company.storeapi.model.enums.OptionClinicExam;
import com.company.storeapi.model.payload.request.clinichistory.*;
import com.company.storeapi.model.payload.response.clinicexam.ResponseClinicExam;
import com.company.storeapi.model.payload.response.clinichistory.ResponseClinicHistoryDTO;
import com.company.storeapi.model.payload.response.diagnosticplan.ResponseDiagnosticPlan;
import com.company.storeapi.model.payload.response.pet.ResponsePetDTO;
import com.company.storeapi.repositories.clicexam.facade.ClinicExamRepositoryFacade;
import com.company.storeapi.repositories.clinichistory.facade.ClinicHistoryRepositoryFacade;
import com.company.storeapi.repositories.diagnosticplan.facade.DiagnosticPlanRepositoryFacade;
import com.company.storeapi.repositories.pet.facade.PetRepositoryFacade;
import com.company.storeapi.repositories.veterinary.facade.VeterinaryRepositoryFacade;
import com.company.storeapi.services.clinichistory.ClinicHistoryService;
import com.company.storeapi.services.pet.PetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ClinicHistoryServiceImpl implements ClinicHistoryService {

    private final ClinicHistoryRepositoryFacade clinicHistoryRepositoryFacade;
    private final VeterinaryRepositoryFacade veterinaryRepositoryFacade;
    private final PetRepositoryFacade petRepositoryFacade;
    private final ClinicExamRepositoryFacade clinicExamRepositoryFacade;
    private final DiagnosticPlanRepositoryFacade diagnosticPlanRepositoryFacade;
    private final PetService petService;


    @Override
    public ResponseClinicHistoryDTO validateAndGetClinicHistoryById(String id) {
        return toClinicHistoryDto(clinicHistoryRepositoryFacade.validateAndGetClinicHistoryById(id));
    }

    @Override
    public ResponseClinicHistoryDTO saveClinicHistory(RequestAddClinicHistoryDTO requestAddClinicHistoryDTO) {

        if (requestAddClinicHistoryDTO.getPhysiologicalConstants() == null) {
            throw new DataNotFoundPersistenceException(LogRefServices.ERROR_DATA_CORRUPT, "EL campo de constantes fisiologicas es obligatorio");
        }
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
        Set<RequestListProblems> listProblems = new LinkedHashSet<>();
        Set<ResponseDiagnosticPlan> diagnosticPlans = new LinkedHashSet<>();


        requestAddClinicHistoryDTO.getClinicExam().getClinicExamClinicHistories().forEach(exam -> {

            RequestAddClinicExamClinicHistory requestAddClinicExamClinicHistory = new RequestAddClinicExamClinicHistory();

            ResponseClinicExam responseClinicExam = new ResponseClinicExam();

            ClinicExam clinicExam = clinicExamRepositoryFacade.validateAndGetClinicExamById(exam.getClinicExam().getId());
            responseClinicExam.setExam(clinicExam.getExam());

            requestAddClinicExamClinicHistory.setOptionClinicExam(exam.getOptionClinicExam());

            if (exam.getOptionClinicExam() == OptionClinicExam.ANORMAL && exam.getObservation().isEmpty()) {
                throw new DataNotFoundPersistenceException(LogRefServices.ERROR_DATA_CORRUPT, "la observación no debe estar vacia");
            }
            if (exam.getOptionClinicExam() == OptionClinicExam.ANORMAL) {
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

        requestAddClinicHistoryDTO.getListProblems().forEach(problems -> {
            RequestListProblems requestListProblems = new RequestListProblems();
            requestListProblems.setProblem(problems.getProblem());
            requestListProblems.setListMaster(problems.getListMaster());
            requestListProblems.setDifferentialDiagnosis(problems.getDifferentialDiagnosis());

            listProblems.add(requestListProblems);
        });

        requestAddClinicHistoryDTO.getDiagnosticPlans().forEach(requestDiagnosticPlan -> {
            DiagnosticPlan responseDiagnosticPlan = diagnosticPlanRepositoryFacade.validateAndGetById(requestDiagnosticPlan.getId());

            ResponseDiagnosticPlan diagnosticPlan = new ResponseDiagnosticPlan();
            diagnosticPlan.setId(responseDiagnosticPlan.getId());
            diagnosticPlan.setDescription(responseDiagnosticPlan.getDescription());
            diagnosticPlan.setCreateAt(responseDiagnosticPlan.getCreateAt());

            diagnosticPlans.add(diagnosticPlan);
        });


        ClinicHistory clinicHistory = new ClinicHistory();
        clinicHistory.setCreateAt(new Date());
        clinicHistory.setVeterinary(veterinary);
        clinicHistory.setPet(pet);
        clinicHistory.setPhysiologicalConstants(requestPhysiologicalConstants);
        clinicHistory.setReasonOfConsultation(requestAddClinicHistoryDTO.getReasonOfConsultation());
        clinicHistory.setAnamnesis(requestAddClinicHistoryDTO.getAnamnesis());
        clinicHistory.setRecipeBook(requestAddClinicHistoryDTO.getRecipeBook());
        clinicHistory.setClinicExam(requestClinicExamClinicHistory);
        clinicHistory.setListProblems(listProblems);
        clinicHistory.setDiagnosticPlans(diagnosticPlans);

        return toClinicHistoryDto(clinicHistoryRepositoryFacade.saveClinicHistory(clinicHistory));
    }

    @Override
    public ResponseClinicHistoryDTO updateClinicHistory(RequestUpdateClinicHistoryDTO requestUpdateClinicHistoryDTO) {
        return null;
    }

    @Override
    public List<ResponseClinicHistoryDTO> findClinicHistoryByCustomer(String nroDocument) {
        List<ClinicHistory> clinicHistories = clinicHistoryRepositoryFacade.findClinicHistoryByCustomer(nroDocument);
        return clinicHistories.stream().map(this::toClinicHistoryDto).collect(Collectors.toList());
    }

    ResponseClinicHistoryDTO toClinicHistoryDto(ClinicHistory clinicHistory) {
        ResponsePetDTO pet = petService.validateAndGetPetById(clinicHistory.getPet().getId());

        ResponseClinicHistoryDTO responseClinicHistoryDTO = new ResponseClinicHistoryDTO();
        responseClinicHistoryDTO.setId(clinicHistory.getId());
        responseClinicHistoryDTO.setCreateAt(clinicHistory.getCreateAt());
        responseClinicHistoryDTO.setVeterinary(clinicHistory.getVeterinary());
        responseClinicHistoryDTO.setPet(pet);
        responseClinicHistoryDTO.setReasonOfConsultation(clinicHistory.getReasonOfConsultation());
        responseClinicHistoryDTO.setAnamnesis(clinicHistory.getAnamnesis());
        responseClinicHistoryDTO.setRecipeBook(clinicHistory.getRecipeBook());
        responseClinicHistoryDTO.setPhysiologicalConstants(clinicHistory.getPhysiologicalConstants());
        responseClinicHistoryDTO.setClinicExam(clinicHistory.getClinicExam());
        responseClinicHistoryDTO.setListProblems(clinicHistory.getListProblems());
        responseClinicHistoryDTO.setDiagnosticPlans(clinicHistory.getDiagnosticPlans());
        return responseClinicHistoryDTO;
    }


}
