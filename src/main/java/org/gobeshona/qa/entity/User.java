package org.gobeshona.qa.entity;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class User
{
    private static final long serialVersionUID = 1L;
    private Long id;
    private String name;
    private String username;
    private String email;
    private String password;
    private List<Role> roles = new ArrayList<>();

}
