package com.orsul.orsul.login;

import lombok.*;

import javax.persistence.*;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@EqualsAndHashCode
@ToString
@Table(name="login")
public class LoginForm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String orsulId;

    private String username;

    private String password;

}
