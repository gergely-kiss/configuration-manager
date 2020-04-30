package uk.gergely.kiss.configurationprovider.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import uk.gergely.kiss.configurationprovider.data.entities.AppEntity;

public interface AppEntityRepository extends JpaRepository<AppEntity, String> {
}
