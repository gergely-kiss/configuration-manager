package uk.gergely.kiss.configurationprovider.data;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AppEntityRepository extends JpaRepository<AppEntity, String> {
}
