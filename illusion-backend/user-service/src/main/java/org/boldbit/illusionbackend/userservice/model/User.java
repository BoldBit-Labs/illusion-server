package org.boldbit.illusionbackend.userservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "UsersCollection")
public class User {

    @Id
    String userId;
    String firstName;
    String lastName;

    @Indexed(unique = true)
    String email;
    String password;

    private List<Project> projects;
}
