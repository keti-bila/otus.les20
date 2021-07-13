package dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@Data
@Builder
@AllArgsConstructor
@JsonSerialize
public class UserOtus {

    private int id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phone;
    private int userStatus;
}
