package software.kasunkavinda.sentura_test.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class User {

    private String uid;
    private String name;
    private String email;

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }


}
