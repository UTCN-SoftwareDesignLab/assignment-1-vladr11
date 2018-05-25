package application.repository;

import application.model.Patient;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PatientRepository extends CrudRepository<Patient, Long> {

    Iterable<Patient> findAllByNameContains(String name);
    Optional<Patient> findByIdCardNumber(String idCardNumber);
    Optional<Patient> findByCnp(String cnp);

}
