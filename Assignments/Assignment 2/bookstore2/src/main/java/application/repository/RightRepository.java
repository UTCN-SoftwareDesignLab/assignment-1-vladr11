package application.repository;

import application.model.Right;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RightRepository extends CrudRepository<Right, Long> {

    List<Right> findByName(String name);
}
