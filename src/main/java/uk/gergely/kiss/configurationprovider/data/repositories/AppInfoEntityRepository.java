package uk.gergely.kiss.configurationprovider.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import uk.gergely.kiss.configurationprovider.data.entities.AppInfoEntity;

public interface AppInfoEntityRepository extends JpaRepository<AppInfoEntity, Integer> {
}
