package dev.ershov.vd.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "adapters")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Adapter {

    @Id
    @Column(name = "user_name")
    private String userName;

    @Column(name = "comment")
    private String comment;

}
