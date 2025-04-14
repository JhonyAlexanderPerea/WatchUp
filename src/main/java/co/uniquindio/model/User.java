package co.uniquindio.model;


import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class User {
    private String fullName;
    private String city;
    private String email;
    private String phone;
    private String address;
    private String password;
}
