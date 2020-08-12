package com.zju.entity;

import lombok.Data;
import javax.persistence.*;
import java.util.Date;

@Data
@Entity(name = "user")
public class User {
    // We recommend that you declare consistently-named identifier properties on
    // persistent classes and that you use a nullable (i.e., non-primitive) type.
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer uid;
    private Boolean isadmin;
    @Column(unique = true)
    private String username;
    private String password;
    @Column(unique = true)
    private String email;
    private Date signdate;
}
