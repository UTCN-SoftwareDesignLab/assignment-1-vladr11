package application.service;

import application.model.Patient;
import application.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PatientService implements IPatientService {

    private PatientRepository patientRepository;

    @Autowired
    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    public List<Patient> findAll() {
        Iterable<Patient> patientIterable = patientRepository.findAll();
        ArrayList<Patient> patients = new ArrayList<>();
        patientIterable.forEach(patients::add);
        return patients;
    }

    @Override
    public Patient findById(Long id) {
        Optional<Patient> patientOptional = patientRepository.findById(id);
        if (patientOptional.isPresent()) {
            return patientOptional.get();
        }
        return null;
    }

    @Override
    public List<Patient> findByName(String name) {
        Iterable<Patient> patientIterable = patientRepository.findAllByNameContains(name);
        ArrayList<Patient> patients = new ArrayList<>();
        patientIterable.forEach(patients::add);
        return patients;
    }

    @Override
    public Patient findByIdCardNumber(String idCardNumber) {
        Optional<Patient> patientOptional = patientRepository.findByIdCardNumber(idCardNumber);
        if (patientOptional.isPresent()) {
            return patientOptional.get();
        }
        return null;
    }

    @Override
    public Patient findByCNP(String cnp) {
        Optional<Patient> patientOptional = patientRepository.findByCnp(cnp);
        if (patientOptional.isPresent()) {
            return patientOptional.get();
        }
        return null;
    }

    @Override
    public Patient create(Patient patient) {
        return patientRepository.save(patient);
    }

    @Override
    public Patient update(Patient patient) {
        if (patientRepository.existsById(patient.getId())) {
            return patientRepository.save(patient);
        }
        return null;
    }

    @Override
    public boolean delete(Patient patient) {
        if (patientRepository.existsById(patient.getId())) {
            patientRepository.delete(patient);
            return true;
        }
        return false;
    }
}
