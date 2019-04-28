package insat.gl4.cookme.repositories;

import insat.gl4.cookme.models.Quantity;
import org.springframework.data.repository.CrudRepository;

public interface QuantityRepository extends CrudRepository<Quantity, Integer> {
}
