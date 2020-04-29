package uk.gergely.kiss.configurationprovider.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uk.gergely.kiss.configurationprovider.repository.entity.RegisteredPasswordEntity;

public interface RegisteredPasswordEntityRepository extends JpaRepository<RegisteredPasswordEntity, Integer> {
}
