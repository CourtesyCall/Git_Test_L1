package telran.accounting.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserAccountResponseDto {
    private String login;
    private String firstName;
    private String lastName;
    private Set<String> roles;
}
