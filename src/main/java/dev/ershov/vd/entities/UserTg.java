package dev.ershov.vd.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "users_tg")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserTg {

    @Id
    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "role")
    private int role;

    @Column(name = "university")
    private int university;

    @Column(name = "one")
    private boolean one;
}
