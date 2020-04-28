package uk.gergely.kiss.configurationprovider.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uk.gergely.kiss.configurationprovider.repository.entity.ConfigurationEntity;

import java.util.List;

public interface ConfigurationEntityRepository extends JpaRepository<ConfigurationEntity, Integer> {

}
