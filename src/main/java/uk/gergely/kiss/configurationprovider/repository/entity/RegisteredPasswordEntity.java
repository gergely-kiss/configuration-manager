package uk.gergely.kiss.configurationprovider.repository.entity;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity(name = "registered_password")
public class RegisteredPasswordEntity {

    @Id
    @Column(name = "application_info")
    private String applicationInfo;

}
