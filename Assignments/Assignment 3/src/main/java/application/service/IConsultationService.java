package application.service;

import application.model.Consultation;
import application.model.Patient;
import application.model.User;

import java.util.List;

public interface IConsultationService {

    List<Consultation> findAll();
    Consultation findById(Long id);
    List<Consultation> findByPatient(Patient patient);
    List<Consultation> findByDoctor(User doctor);
    List<Consultation> findByPatientAndDoctor(Patient patient, User doctor);
    Consultation create(Consultation consultation);
    Consultation update(Consultation consultation);
    boolean delete(Consultation consultation);
}
