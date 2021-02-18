package com.company.storeapi.services.pet.impl;

import com.company.storeapi.core.exceptions.enums.LogRefServices;
import com.company.storeapi.core.exceptions.persistence.DataCorruptedPersistenceException;
import com.company.storeapi.core.util.Util;
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

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashSet;
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

    @Override
    public List<ResponsePetDTO> getAllPet() {
        List<Pet> veterinaries = petRepositoryFacade.getAllPet();
        return veterinaries.stream().map(this::toPetDto).collect(Collectors.toList());
    }

    @Override
    public List<ResponsePetDTO> findPetByCustomerNroDocument(String nroDocument) {
        List<Pet> veterinaries = petRepositoryFacade.findPetByCustomerNroDocument(nroDocument);
        return veterinaries.stream().map(this::toPetDto).collect(Collectors.toList());
    }

    @Override
    public ResponsePetDTO validateAndGetPetById(String id) {
        return toPetDto(petRepositoryFacade.validateAndGetPetById(id));
    }

    @Override
    public ResponsePetDTO savePet(RequestAddPetDTO requestAddPetDTO) {
        Species specie = speciesRepositoryFacade.validateAndGetById(requestAddPetDTO.getSpecies());
        Breed breed = breedRepositoryFacade.validateAndGetBreedById(requestAddPetDTO.getBreed());
        Customer customer = customerRepositoryFacade.findByNroDocument(requestAddPetDTO.getCustomer());

        if (requestAddPetDTO.getDateBirth().after(new Date())) {
            throw new DataCorruptedPersistenceException(LogRefServices.ERROR_DATA_CORRUPT, "La fecha de nacimiento no puede ser mayor a la fecha actual");
        }
        Pet pet = new Pet();
        pet.setName(requestAddPetDTO.getName());
        pet.setSpecies(specie);
        pet.setBreed(breed);
        pet.setColor(requestAddPetDTO.getColor());
        pet.setSex(requestAddPetDTO.getSex());
        pet.setDateBirth(requestAddPetDTO.getDateBirth());
        pet.setParticularSigns(requestAddPetDTO.getParticularSigns());
        pet.setOrigin(requestAddPetDTO.getOrigin());
        pet.setCustomer(customer);
        pet.setCreateAt(new Date());
        pet.setUpdateAt(new Date());
        pet.setPhoto(requestAddPetDTO.getPhoto());

        return toPetDto(petRepositoryFacade.savePet(pet));
    }

    private Integer getAge(Date date) {
        LocalDate now = LocalDate.now();
        LocalDate dateNac = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        Period period = Period.between(dateNac, now);
        return period.getYears();
    }

    private ResponsePetDTO toPetDto(Pet pet) {

        Pet getPet = petRepositoryFacade.validateAndGetPetById(pet.getId());

        Set<RequestPatientHistoryVaccinations> vaccinations = getPet.getVaccinations();
        Set<RequestPatientHistoryDeworming> dewormingsInternal = pet.getDewormingInternal();
        Set<RequestPatientHistoryDeworming> dewormingsExternal = pet.getDewormingExternal();

        Integer age = getAge(pet.getDateBirth());

        ResponsePetDTO responsePetDTO = new ResponsePetDTO();

        responsePetDTO.setId(pet.getId());
        responsePetDTO.setName(pet.getName());
        responsePetDTO.setSpecies(pet.getSpecies());
        responsePetDTO.setBreed(pet.getBreed());
        responsePetDTO.setColor(pet.getColor());
        responsePetDTO.setSex(pet.getSex());
        responsePetDTO.setDateBirth(Util.converterDate(pet.getDateBirth()));
        responsePetDTO.setAge(age);
        responsePetDTO.setParticularSigns(pet.getParticularSigns());
        responsePetDTO.setOrigin(pet.getOrigin());
        responsePetDTO.setCustomer(pet.getCustomer());
        responsePetDTO.setCreateAt(Util.converterDate(pet.getCreateAt()));
        responsePetDTO.setUpdateAt(Util.converterDate(pet.getUpdateAt()));
        responsePetDTO.setPhoto(pet.getPhoto());
        responsePetDTO.setVaccinations(vaccinations);
        responsePetDTO.setDewormingExternal(dewormingsExternal);
        responsePetDTO.setDewormingInternal(dewormingsInternal);

        return responsePetDTO;
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
        pet.setParticularSigns(defaultIfNull(requestUpdatePetDTO.getParticularSigns(), pet.getParticularSigns()));
        pet.setOrigin(defaultIfNull(requestUpdatePetDTO.getOrigin(), pet.getOrigin()));
        pet.setCustomer(customer);
        pet.setCreateAt(pet.getCreateAt());
        pet.setUpdateAt(new Date());
        return toPetDto(petRepositoryFacade.savePet(pet));
    }

    @Override
    public void deletePet(String id) {
        petRepositoryFacade.deletePet(id);
    }

    @Override
    public ResponsePetDTO updateVaccination(String id, RequestPatientHistoryVaccinations requestPatientHistory) {

        Pet pet = petRepositoryFacade.validateAndGetPetById(id);

        Set<RequestPatientHistoryVaccinations> vaccinations = pet.getVaccinations();

        Set<ResponseVaccination> responseVaccinations = new HashSet<>();

        requestPatientHistory.getVaccinations().forEach(vaccination -> {

            if (vaccination.getVaccinationDate() == null) {
                throw new DataCorruptedPersistenceException(LogRefServices.ERROR_DATA_CORRUPT, "fecha de vacunación obligatoria");
            }

            if (vaccination.getVaccinationDate().after(new Date())) {
                throw new DataCorruptedPersistenceException(LogRefServices.ERROR_DATA_CORRUPT, "La fecha de vacunación no puede ser superior a la fecha actual");
            }
            Vaccination vaccinationValidate = vaccinationRepositoryFacade.validateAndGetById(vaccination.getVaccination().getId());

            ResponseVaccination responseVaccination = new ResponseVaccination();
            responseVaccination.setVaccination(vaccinationValidate);
            responseVaccination.setVaccinationDate((vaccination.getVaccinationDate()));

            responseVaccinations.add(responseVaccination);

        });

        RequestPhysiologicalConstants requestPhysiologicalConstants = getPhysiologicalConstants(requestPatientHistory.getPhysiologicalConstants());

        RequestPatientHistoryVaccinations requestPatientHistoryVaccinations = new RequestPatientHistoryVaccinations();
        requestPatientHistoryVaccinations.setVaccinations(responseVaccinations);
        requestPatientHistoryVaccinations.setPhysiologicalConstants(requestPhysiologicalConstants);

        vaccinations.add(requestPatientHistoryVaccinations);
        pet.setVaccinations(vaccinations);

        return toPetDto(petRepositoryFacade.savePet(pet));
    }

    private RequestPhysiologicalConstants getPhysiologicalConstants(RequestPhysiologicalConstants physiologicalConstants) {
        RequestPhysiologicalConstants requestPhysiologicalConstants = new RequestPhysiologicalConstants();
        requestPhysiologicalConstants.setCapillaryFillTime(physiologicalConstants.getCapillaryFillTime());
        requestPhysiologicalConstants.setHeartRate(physiologicalConstants.getHeartRate());
        requestPhysiologicalConstants.setRespiratoryFrequency(physiologicalConstants.getRespiratoryFrequency());
        requestPhysiologicalConstants.setPulse(physiologicalConstants.getPulse());
        requestPhysiologicalConstants.setTemperature(physiologicalConstants.getTemperature());
        requestPhysiologicalConstants.setWeight(physiologicalConstants.getWeight());
        return requestPhysiologicalConstants;
    }

    @Override
    public ResponsePetDTO updateDewormingInternal(String id, RequestPatientHistoryDeworming requestPatientHistory) {
        Pet pet = petRepositoryFacade.validateAndGetPetById(id);
        Set<RequestPatientHistoryDeworming> dewormings = pet.getDewormingInternal();


        getRequestDeworming(requestPatientHistory, dewormings);
        pet.setDewormingInternal(dewormings);

        pet.setDewormingInternal(dewormings);
        return toPetDto(petRepositoryFacade.savePet(pet));
    }

    private void getRequestDeworming(RequestPatientHistoryDeworming requestPatientHistory, Set<RequestPatientHistoryDeworming> dewormings) {
        RequestDeworming requestDeworming = new RequestDeworming();
        if (requestPatientHistory.getDeworming().getDewormingDate() == null) {
            throw new DataCorruptedPersistenceException(LogRefServices.ERROR_DATA_CORRUPT, "fecha de desparasitación obligatoria");
        }
        if (requestPatientHistory.getDeworming().getOption() == Option.SI) {
            requestDeworming.setDescription(requestPatientHistory.getDeworming().getDescription());
            requestDeworming.setDewormingDate(requestPatientHistory.getDeworming().getDewormingDate());
            requestDeworming.setProduct(requestPatientHistory.getDeworming().getProduct());
        } else {
            requestDeworming.setDescription("N/A");
            requestDeworming.setProduct("N/A");
        }

        RequestPhysiologicalConstants requestPhysiologicalConstants = getPhysiologicalConstants(requestPatientHistory.getPhysiologicalConstants());


        RequestPatientHistoryDeworming requestPatientHistoryVaccinations = new RequestPatientHistoryDeworming();
        requestPatientHistoryVaccinations.setDeworming(requestDeworming);
        requestPatientHistoryVaccinations.setPhysiologicalConstants(requestPhysiologicalConstants);

        dewormings.add(requestPatientHistoryVaccinations);
    }


    @Override
    public ResponsePetDTO updateDewormingExternal(String id, RequestPatientHistoryDeworming requestPatientHistory) {
        Pet pet = petRepositoryFacade.validateAndGetPetById(id);
        Set<RequestPatientHistoryDeworming> dewormings = pet.getDewormingExternal();

        getRequestDeworming(requestPatientHistory, dewormings);
        pet.setDewormingExternal(dewormings);

        pet.setDewormingExternal(dewormings);
        return toPetDto(petRepositoryFacade.savePet(pet));
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
        return toPetDto(petRepositoryFacade.savePet(pet));

    }

    @Override
    public ResponsePetDTO updateReproductiveStatus(String id, ReproductiveStatus requestPatientHistory) {
        Pet pet = petRepositoryFacade.validateAndGetPetById(id);
        pet.setReproductiveStatus(requestPatientHistory);
        return toPetDto(petRepositoryFacade.savePet(pet));
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
        return toPetDto(petRepositoryFacade.savePet(pet));
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
        return toPetDto(petRepositoryFacade.savePet(pet));
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

        return toPetDto(petRepositoryFacade.savePet(pet));
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
        return toPetDto(petRepositoryFacade.savePet(pet));
    }

    @Override
    public ResponsePetDTO updateHabitat(String id, Habitat requestPatientHistory) {
        Pet pet = petRepositoryFacade.validateAndGetPetById(id);
        pet.setHabitat(requestPatientHistory);
        return toPetDto(petRepositoryFacade.savePet(pet));
    }


}
