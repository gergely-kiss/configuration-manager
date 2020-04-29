package uk.gergely.kiss.configurationprovider.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uk.gergely.kiss.configurationprovider.repository.entity.RegisteredApplicationEntity;

public interface PropertyEntityRepository extends JpaRepository<PropertyEntity, Integer> {
}
