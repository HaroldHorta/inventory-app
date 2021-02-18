package com.company.storeapi.web.api;

import com.company.storeapi.model.enums.Habitat;
import com.company.storeapi.model.enums.ReproductiveStatus;
import com.company.storeapi.model.payload.request.clinichistory.RequestFeeding;
import com.company.storeapi.model.payload.request.pet.*;
import com.company.storeapi.model.payload.response.pet.ResponsePetDTO;
import com.company.storeapi.services.pet.PetService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "/api/pet")
@CrossOrigin({"*"})
public class PetRestApi {

    private final PetService service;

    public PetRestApi(PetService service) {
        this.service = service;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ResponsePetDTO> getAllPet() {
        return service.getAllPet();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponsePetDTO> getById(@PathVariable("id") String id) {
        ResponsePetDTO entity = service.validateAndGetPetById(id);
        return new ResponseEntity<>(entity, new HttpHeaders(), HttpStatus.OK);
    }


    @GetMapping(value = "customer/{nroDocument}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ResponsePetDTO> getByCustomer(@PathVariable("nroDocument") String nroDocument) {
        return service.findPetByCustomerNroDocument(nroDocument);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponsePetDTO> create(@RequestBody RequestAddPetDTO requestAddPetDTO) {
        ResponsePetDTO created = service.savePet(requestAddPetDTO);
        return new ResponseEntity<>(created, new HttpHeaders(), HttpStatus.CREATED);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponsePetDTO> update(@RequestBody RequestUpdatePetDTO requestUpdatePetDTO) {
        ResponsePetDTO update = service.updatePet(requestUpdatePetDTO);
        return new ResponseEntity<>(update, new HttpHeaders(), HttpStatus.OK);
    }

    @PatchMapping(value = "vaccination/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponsePetDTO> vaccination(@PathVariable String id, @RequestBody RequestPatientHistoryVaccinations requestPatientHistory) {
        ResponsePetDTO update = service.updateVaccination(id, requestPatientHistory);
        return new ResponseEntity<>(update, new HttpHeaders(), HttpStatus.OK);
    }

    @PatchMapping(value = "dewormingInternal/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponsePetDTO> updateDewormingInternal(@PathVariable String id, @RequestBody RequestPatientHistoryDeworming requestPatientHistory) {
        ResponsePetDTO update = service.updateDewormingInternal(id, requestPatientHistory);
        return new ResponseEntity<>(update, new HttpHeaders(), HttpStatus.OK);
    }

    @PatchMapping(value = "dewormingExternal/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponsePetDTO> updateDewormingExternal(@PathVariable String id, @RequestBody RequestPatientHistoryDeworming requestPatientHistory) {
        ResponsePetDTO update = service.updateDewormingExternal(id, requestPatientHistory);
        return new ResponseEntity<>(update, new HttpHeaders(), HttpStatus.OK);
    }

    @PatchMapping(value = "feeding/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponsePetDTO> updateFeeding(@PathVariable String id, @RequestBody RequestFeeding requestPatientHistory) {
        ResponsePetDTO update = service.updateFeeding(id, requestPatientHistory);
        return new ResponseEntity<>(update, new HttpHeaders(), HttpStatus.OK);
    }

    @PatchMapping(value = "reproductiveStatus/{id}/{reproductiveStatus}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponsePetDTO> updateReproductiveStatus(@PathVariable String id, @PathVariable ReproductiveStatus reproductiveStatus) {
        ResponsePetDTO update = service.updateReproductiveStatus(id, reproductiveStatus);
        return new ResponseEntity<>(update, new HttpHeaders(), HttpStatus.OK);
    }

    @PatchMapping(value = "previousIllnesses/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponsePetDTO> updatePreviousIllnesses(@PathVariable String id, @RequestBody String requestPatientHistory) {
        ResponsePetDTO update = service.updatePreviousIllnesses(id, requestPatientHistory);
        return new ResponseEntity<>(update, new HttpHeaders(), HttpStatus.OK);
    }

    @PatchMapping(value = "surgeries/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponsePetDTO> updateSurgeries(@PathVariable String id, @RequestBody String requestPatientHistory) {
        ResponsePetDTO update = service.updateSurgeries(id, requestPatientHistory);
        return new ResponseEntity<>(update, new HttpHeaders(), HttpStatus.OK);
    }

    @PatchMapping(value = "allergy/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponsePetDTO> updateAllergy(@PathVariable String id, @RequestBody String requestPatientHistory) {
        ResponsePetDTO update = service.updateAllergy(id, requestPatientHistory);
        return new ResponseEntity<>(update, new HttpHeaders(), HttpStatus.OK);
    }

    @PatchMapping(value = "familyBackground/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponsePetDTO> updateFamilyBackground(@PathVariable String id, @RequestBody String requestPatientHistory) {
        ResponsePetDTO update = service.updateFamilyBackground(id, requestPatientHistory);
        return new ResponseEntity<>(update, new HttpHeaders(), HttpStatus.OK);
    }

    @PatchMapping(value = "habitat/{id}/{habitat}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponsePetDTO> updateHabitat(@PathVariable String id, @PathVariable Habitat habitat) {
        ResponsePetDTO update = service.updateHabitat(id, habitat);
        return new ResponseEntity<>(update, new HttpHeaders(), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable String id) {
        service.deletePet(id);
    }
}
