package org.gobeshona.qa.entity;


import lombok.*;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role
{
    private Long id;
    private String name;
    private List<User> users;
}
