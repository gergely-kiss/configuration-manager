package uk.gergely.kiss.configurationprovider.data.entities;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity(name = "app")
public class AppEntity {
    @Id
    @Column(name = "app_id")
    private String appId;
    @Column(name = "app_info")
    private String appInfo;

    @Column
    private String role;


}
