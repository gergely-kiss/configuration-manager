package uk.gergely.kiss.configurationprovider.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import uk.gergely.kiss.configurationprovider.data.entities.PropertyEntity;

public interface PropertyEntityRepository extends JpaRepository<PropertyEntity, Integer> {
}
