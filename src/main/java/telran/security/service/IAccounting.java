package telran.security.service;



import telran.accounting.dto.RolesResponseDto;
import telran.accounting.dto.UserAccountResponseDto;
import telran.accounting.dto.UserRegisterDto;
import telran.accounting.dto.UserUpdateDto;

import java.time.LocalDateTime;

public interface IAccounting {
    UserAccountResponseDto registration(UserRegisterDto user);
    UserAccountResponseDto removeUser(String login);
    UserAccountResponseDto getUser(String login);
    UserAccountResponseDto updateUser(String login, UserUpdateDto user);
    boolean updatePassword(String login, String password);

    boolean revokeAccount(String login);
    boolean activateAccount(String login);

    String getPasswordHash(String login);
    LocalDateTime getActivationDate(String login);

    RolesResponseDto getRoles(String login);
    RolesResponseDto addRole(String login, String role);
    RolesResponseDto removeRole(String login, String role);

}
