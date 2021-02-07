package com.company.storeapi.services.pet.impl;

import com.company.storeapi.core.mapper.PetMapper;
import com.company.storeapi.model.entity.*;
import com.company.storeapi.model.enums.FeedingOption;
import com.company.storeapi.model.enums.Habitat;
import com.company.storeapi.model.enums.Option;
import com.company.storeapi.model.enums.ReproductiveStatus;
import com.company.storeapi.model.payload.request.clinichistory.RequestFeeding;
import com.company.storeapi.model.payload.request.clinichistory.RequestPhysiologicalConstants;
import com.company.storeapi.model.payload.request.pet.*;
import com.company.storeapi.model.payload.response.pet.ResponsePetDTO;
import com.company.storeapi.model.payload.response.vaccination.ResponseVaccination;
import com.company.storeapi.repositories.breed.facade.BreedRepositoryFacade;
import com.company.storeapi.repositories.customer.facade.CustomerRepositoryFacade;
import com.company.storeapi.repositories.pet.facade.PetRepositoryFacade;
import com.company.storeapi.repositories.species.facade.SpeciesRepositoryFacade;
import com.company.storeapi.repositories.vaccination.facade.VaccinationRepositoryFacade;
import com.company.storeapi.services.pet.PetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;

@Service
@RequiredArgsConstructor
public class PetServiceImpl implements PetService {

    private final PetRepositoryFacade petRepositoryFacade;
    private final SpeciesRepositoryFacade speciesRepositoryFacade;
    private final BreedRepositoryFacade breedRepositoryFacade;
    private final CustomerRepositoryFacade customerRepositoryFacade;
    private final VaccinationRepositoryFacade vaccinationRepositoryFacade;
    private final PetMapper petMapper;

    @Override
    public List<ResponsePetDTO> getAllPet() {
        List<Pet> veterinaries = petRepositoryFacade.getAllPet();
        return veterinaries.stream().map(petMapper::toPetDto).collect(Collectors.toList());
    }

    @Override
    public ResponsePetDTO validateAndGetPetById(String id) {
        return petMapper.toPetDto(petRepositoryFacade.validateAndGetPetById(id));
    }

    @Override
    public ResponsePetDTO savePet(RequestAddPetDTO requestAddPetDTO) {
        Species specie = speciesRepositoryFacade.validateAndGetById(requestAddPetDTO.getSpecies());
        Breed breed = breedRepositoryFacade.validateAndGetBreedById(requestAddPetDTO.getBreed());
        Customer customer = customerRepositoryFacade.validateAndGetCustomerById(requestAddPetDTO.getCustomer());

        Pet pet = new Pet();
        pet.setName(requestAddPetDTO.getName());
        pet.setSpecies(specie);
        pet.setBreed(breed);
        pet.setColor(requestAddPetDTO.getColor());
        pet.setSex(requestAddPetDTO.getSex());
        pet.setDateBirth(requestAddPetDTO.getDateBirth());
        pet.setAge(requestAddPetDTO.getAge());
        pet.setParticularSigns(requestAddPetDTO.getParticularSigns());
        pet.setOrigin(requestAddPetDTO.getOrigin());
        pet.setCustomer(customer);
        pet.setCreateAt(new Date());
        pet.setUpdateAt(new Date());

        return petMapper.toPetDto(petRepositoryFacade.savePet(pet));
    }

    @Override
    public ResponsePetDTO updatePet(RequestUpdatePetDTO requestUpdatePetDTO) {

        Species specie = speciesRepositoryFacade.validateAndGetById(requestUpdatePetDTO.getSpecies());
        Breed breed = breedRepositoryFacade.validateAndGetBreedById(requestUpdatePetDTO.getBreed());
        Customer customer = customerRepositoryFacade.validateAndGetCustomerById(requestUpdatePetDTO.getCustomer());

        Pet pet = petRepositoryFacade.validateAndGetPetById(requestUpdatePetDTO.getId());
        pet.setName(defaultIfNull(requestUpdatePetDTO.getName().trim(), pet.getName()));
        pet.setSpecies(specie);
        pet.setBreed(breed);
        pet.setColor(defaultIfNull(requestUpdatePetDTO.getColor(), pet.getColor()));
        pet.setSex(defaultIfNull(requestUpdatePetDTO.getSex(), pet.getSex()));
        pet.setDateBirth(defaultIfNull(requestUpdatePetDTO.getDateBirth(), pet.getDateBirth()));
        pet.setAge(defaultIfNull(requestUpdatePetDTO.getAge(), pet.getAge()));
        pet.setParticularSigns(defaultIfNull(requestUpdatePetDTO.getParticularSigns(), pet.getParticularSigns()));
        pet.setOrigin(defaultIfNull(requestUpdatePetDTO.getOrigin(), pet.getOrigin()));
        pet.setCustomer(customer);
        pet.setCreateAt(pet.getCreateAt());
        pet.setUpdateAt(new Date());
        return petMapper.toPetDto(petRepositoryFacade.savePet(pet));
    }

    @Override
    public void deletePet(String id) {
        petRepositoryFacade.deletePet(id);
    }

    @Override
    public ResponsePetDTO updateVaccination(String id, RequestPatientHistoryVaccinations requestPatientHistory) {

        Pet pet = petRepositoryFacade.validateAndGetPetById(id);

        Set<ResponseVaccination> vaccinations = pet.getVaccinations();
        requestPatientHistory.getVaccinations().forEach(vaccination -> {
            Vaccination vaccinationValidate = vaccinationRepositoryFacade.validateAndGetById(vaccination.getId());
            if (!(vaccination.getId().equals(vaccinationValidate.getId()))) {
                ResponseVaccination responseVaccination = new ResponseVaccination();
                responseVaccination.setVaccination(vaccinationValidate);
                responseVaccination.setVaccinationDate((vaccination.getVaccinationDate()));
                vaccinations.add(responseVaccination);
            }
        });
        pet.setVaccinations(vaccinations);

        return petMapper.toPetDto(petRepositoryFacade.savePet(pet));
    }

    @Override
    public ResponsePetDTO updatePhysiologicalConstants(String id, RequestPatientHistoryPhysiologicalConstants requestPatientHistory) {
        Pet pet = petRepositoryFacade.validateAndGetPetById(id);
        Set<RequestPhysiologicalConstants> physiologicalConstants = pet.getPhysiologicalConstants();

        requestPatientHistory.getPhysiologicalConstants().forEach(physiological -> {
            RequestPhysiologicalConstants requestPhysiologicalConstants = new RequestPhysiologicalConstants();
            requestPhysiologicalConstants.setCapillaryFillTime(physiological.getCapillaryFillTime());
            requestPhysiologicalConstants.setHeartRate(physiological.getHeartRate());
            requestPhysiologicalConstants.setRespiratoryFrequency(physiological.getRespiratoryFrequency());
            requestPhysiologicalConstants.setPulse(physiological.getPulse());
            requestPhysiologicalConstants.setTemperature(physiological.getTemperature());
            requestPhysiologicalConstants.setWeight(physiological.getWeight());
            physiologicalConstants.add(requestPhysiologicalConstants);
        });

        pet.setPhysiologicalConstants(physiologicalConstants);

        return petMapper.toPetDto(petRepositoryFacade.savePet(pet));

    }

    @Override
    public ResponsePetDTO updateDewormingInternal(String id, RequestPatientHistoryDeworming requestPatientHistory) {
        Pet pet = petRepositoryFacade.validateAndGetPetById(id);
        Set<RequestDeworming> dewormings = pet.getDewormingInternal();

        getDewormings(requestPatientHistory, dewormings);

        pet.setDewormingInternal(dewormings);
        return petMapper.toPetDto(petRepositoryFacade.savePet(pet));
    }

    public void getDewormings(RequestPatientHistoryDeworming requestPatientHistory, Set<RequestDeworming> dewormings) {
        requestPatientHistory.getDeworming().forEach(physiological -> {
            RequestDeworming requestDeworming = new RequestDeworming();
            if (physiological.getOption() == Option.SI) {
                requestDeworming.setDescription(physiological.getDescription());
                requestDeworming.setDewormingDate(physiological.getDewormingDate());
                requestDeworming.setProduct(physiological.getProduct());
            } else {
                requestDeworming.setDescription("N/A");
                requestDeworming.setProduct("N/A");
            }

            dewormings.add(requestDeworming);
        });
    }

    @Override
    public ResponsePetDTO updateDewormingExternal(String id, RequestPatientHistoryDeworming requestPatientHistory) {
        Pet pet = petRepositoryFacade.validateAndGetPetById(id);
        Set<RequestDeworming> dewormings = pet.getDewormingExternal();

        getDewormings(requestPatientHistory, dewormings);

        pet.setDewormingExternal(dewormings);
        return petMapper.toPetDto(petRepositoryFacade.savePet(pet));
    }

    @Override
    public ResponsePetDTO updateFeeding(String id, RequestFeeding requestPatientHistory) {

        Pet pet = petRepositoryFacade.validateAndGetPetById(id);

        RequestFeeding feeding = new RequestFeeding();
        feeding.setFeedingOption(requestPatientHistory.getFeedingOption());

        if (requestPatientHistory.getFeedingOption().equals(FeedingOption.OTRA)) {
            feeding.setDescription(requestPatientHistory.getDescription());
        }

        pet.setFeeding(feeding);
        return petMapper.toPetDto(petRepositoryFacade.savePet(pet));

    }

    @Override
    public ResponsePetDTO updateReproductiveStatus(String id, ReproductiveStatus requestPatientHistory) {
        Pet pet = petRepositoryFacade.validateAndGetPetById(id);
        pet.setReproductiveStatus(requestPatientHistory);
        return petMapper.toPetDto(petRepositoryFacade.savePet(pet));
    }

    @Override
    public ResponsePetDTO updatePreviousIllnesses(String id, String requestPatientHistory) {
        Pet pet = petRepositoryFacade.validateAndGetPetById(id);
        String previousIllnesses;
        if (pet.getPreviousIllnesses() != null) {
            previousIllnesses = pet.getPreviousIllnesses() + ", " + requestPatientHistory;
        } else {
            previousIllnesses = requestPatientHistory;

        }
        pet.setPreviousIllnesses(previousIllnesses);
        return petMapper.toPetDto(petRepositoryFacade.savePet(pet));
    }

    @Override
    public ResponsePetDTO updateSurgeries(String id, String requestPatientHistory) {
        Pet pet = petRepositoryFacade.validateAndGetPetById(id);
        String surgeries;
        if (pet.getSurgeries() == null) {
            surgeries = requestPatientHistory;
        } else {
            surgeries = pet.getSurgeries() + ", " + requestPatientHistory;
        }
        pet.setSurgeries(surgeries);
        return petMapper.toPetDto(petRepositoryFacade.savePet(pet));
    }

    @Override
    public ResponsePetDTO updateAllergy(String id, String requestPatientHistory) {

        Pet pet = petRepositoryFacade.validateAndGetPetById(id);
        String allergy;

        if (pet.getAllergy() != null) {
            allergy = pet.getAllergy() + ", " + requestPatientHistory;
        } else {
            allergy = requestPatientHistory;
        }

        pet.setAllergy(allergy);

        return petMapper.toPetDto(petRepositoryFacade.savePet(pet));
    }

    @Override
    public ResponsePetDTO updateFamilyBackground(String id, String requestPatientHistory) {
        Pet pet = petRepositoryFacade.validateAndGetPetById(id);
        String familyBackground;
        if (pet.getFamilyBackground() != null) {
            familyBackground = pet.getFamilyBackground() + ", " + requestPatientHistory;
        } else {
            familyBackground = requestPatientHistory;

        }
        pet.setFamilyBackground(familyBackground);
        return petMapper.toPetDto(petRepositoryFacade.savePet(pet));
    }

    @Override
    public ResponsePetDTO updateHabitat(String id, Habitat requestPatientHistory) {
        Pet pet = petRepositoryFacade.validateAndGetPetById(id);
        pet.setHabitat(requestPatientHistory);
        return petMapper.toPetDto(petRepositoryFacade.savePet(pet));
    }



}
