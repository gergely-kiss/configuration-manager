package uk.gergely.kiss.configurationprovider.data.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity(name = "app_property")
@Getter
@Setter
@NoArgsConstructor
@ToString
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
