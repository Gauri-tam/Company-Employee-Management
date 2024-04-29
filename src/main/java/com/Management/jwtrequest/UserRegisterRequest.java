package com.Management.jwtrequest;

import com.Management.enamurate.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterRequest {

    @NotEmpty(message = "This Filed is Not to be Empty")
    @Size(min = 3, max = 20, message = "Characters Between 3 to 20")
    private String firstName;

    @NotEmpty(message = "This Filed is Not to be Empty")
    @Size(min = 3, max = 20, message = "Characters Between 3 to 20")
    private String lastName;

    /** for more pattern and restrictions for email
     * you can refer https://www.baeldung.com/java-email-validation-regex
     * */
    @NotEmpty(message = "This Filed is Not to be Empty")
    @Email(message = "Invalid Email!")
    private String email;

    @Size(min = 8, message = "Minimum 8 Character are required! ")
    @NotEmpty(message = "This Filed is Not to be Empty")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$", message = "Invalid Password! ")
    private String password;
    private Role role;
}
