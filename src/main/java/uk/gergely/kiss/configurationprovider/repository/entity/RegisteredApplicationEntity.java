package uk.gergely.kiss.configurationprovider.repository.entity;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity(name = "registered_application")
public class RegisteredApplicationEntity {
    @Id
    @Column(name = "application_id")
    private String applicationId;
    @Column
    private String password;

    @Column
    private String role;


}
