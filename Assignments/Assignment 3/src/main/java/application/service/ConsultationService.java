package application.service;

import application.model.Consultation;
import application.model.Patient;
import application.model.User;
import application.repository.ConsultationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ConsultationService implements IConsultationService {

    private ConsultationRepository consultationRepository;

    @Autowired
    public ConsultationService(ConsultationRepository consultationRepository) {
        this.consultationRepository = consultationRepository;
    }

    @Override
    public List<Consultation> findAll() {
        Iterable<Consultation> consultationIterable = consultationRepository.findAll();
        ArrayList<Consultation> consultations = new ArrayList<>();
        consultationIterable.forEach(consultations::add);
        return consultations;
    }

    @Override
    public Consultation findById(Long id) {
        Optional<Consultation> consultationOptional = consultationRepository.findById(id);
        if (consultationOptional.isPresent()) {
            return consultationOptional.get();
        }
        return null;
    }

    @Override
    public List<Consultation> findByPatient(Patient patient) {
        Iterable<Consultation> consultationIterable = consultationRepository.findAllByPatient(patient);
        ArrayList<Consultation> consultations = new ArrayList<>();
        consultationIterable.forEach(consultations::add);
        return consultations;
    }

    @Override
    public List<Consultation> findByDoctor(User doctor) {
        Iterable<Consultation> consultationIterable = consultationRepository.findAllByDoctor(doctor);
        ArrayList<Consultation> consultations = new ArrayList<>();
        consultationIterable.forEach(consultations::add);
        return consultations;
    }

    @Override
    public List<Consultation> findByPatientAndDoctor(Patient patient, User doctor) {
        Iterable<Consultation> consultationIterable = consultationRepository.findAllByPatientAndDoctor(patient, doctor);
        ArrayList<Consultation> consultations = new ArrayList<>();
        consultationIterable.forEach(consultations::add);
        return consultations;
    }

    @Override
    public Consultation create(Consultation consultation) {
        return consultationRepository.save(consultation);
    }

    @Override
    public Consultation update(Consultation consultation) {
        if (consultationRepository.existsById(consultation.getId())) {
            return consultationRepository.save(consultation);
        }
        return null;
    }

    @Override
    public boolean delete(Consultation consultation) {
        if (consultationRepository.existsById(consultation.getId())) {
            consultationRepository.delete(consultation);
            return true;
        }
        return false;
    }
}
