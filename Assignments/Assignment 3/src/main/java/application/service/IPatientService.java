package application.service;

import application.model.Patient;

import java.util.List;

public interface IPatientService {

    List<Patient> findAll();
    Patient findById(Long id);
    List<Patient> findByName(String name);
    Patient findByIdCardNumber(String idCardNumber);
    Patient findByCNP(String cnp);
    Patient create(Patient patient);
    Patient update(Patient patient);
    boolean delete(Patient patient);
}
