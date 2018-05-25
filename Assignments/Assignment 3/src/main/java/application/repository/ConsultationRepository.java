package application.repository;

import application.model.Consultation;
import application.model.Patient;
import application.model.User;
import org.springframework.data.repository.CrudRepository;

public interface ConsultationRepository extends CrudRepository<Consultation, Long> {

    Iterable<Consultation> findAllByPatient(Patient patient);
    Iterable<Consultation> findAllByDoctor(User doctor);
    Iterable<Consultation> findAllByPatientAndDoctor(Patient patient, User doctor);
}
