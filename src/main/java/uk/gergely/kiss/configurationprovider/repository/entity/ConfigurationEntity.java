package uk.gergely.kiss.configurationprovider.repository.entity;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity(name = "configuration")
public class ConfigurationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer id;
    @Column(name = "application_id")
    private String applicationId;
    @Column
    private String key;
    @Column
    private String value;

}
