package uk.gergely.kiss.configurationprovider.repository;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "app_property")
@Getter
@Setter
@NoArgsConstructor
public class PropertyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "property_key")
    private String propertyKey;
    @Column(name = "property_value")
    private String propertyValue;
    @Column(name = "app_id")
    private String appId;

}
