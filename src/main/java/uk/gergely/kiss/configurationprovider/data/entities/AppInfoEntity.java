package uk.gergely.kiss.configurationprovider.data.entities;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity(name = "app_info")
public class AppInfoEntity {

    @Id
    @Column(name = "app_info")
    private String appInfo;

}
