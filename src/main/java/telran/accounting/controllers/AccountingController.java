package telran.accounting.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;
import telran.accounting.dto.RolesResponseDto;
import telran.accounting.dto.UserAccountResponseDto;
import telran.accounting.dto.UserRegisterDto;
import telran.accounting.dto.UserUpdateDto;
import telran.security.service.IAccounting;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Base64;

@RestController
@RequestMapping("/account")
@EnableMethodSecurity// SSv5 -> @EnableGlobalMethodSecurity(prePostEnabled = true)
public class AccountingController {
    @Autowired
    IAccounting service;

    @PostMapping("/register")
    public UserAccountResponseDto registration(@RequestBody UserRegisterDto user) {
        return service.registration(user);
    }

    @PreAuthorize("hasRole('OWNER')")
    @DeleteMapping
    public UserAccountResponseDto removeUser(String login) {
        return null;
    }

    @PostMapping("/login")
    public UserAccountResponseDto login(Principal principal) {

        return service.getUser(principal.getName());
    }

    private String[] getCreedentials(String token) {
        String[] basic = token.split("");
        String decode= new String(Base64.getDecoder().decode(basic[1]));
        return decode.split(":");
    }

    @Override
    public UserAccountResponseDto updateUser(String login, UserUpdateDto user) {
        return null;
    }

    @PutMapping("/password/")
    public boolean updatePassword(Principal principal
                                  ,@RequestHeader("New-Password") String password) {
        return service.updatePassword(principal.getName(),password);
    }

    @Override
    public boolean revokeAccount(String login) {
        return false;
    }

    @Override
    public boolean activateAccount(String login) {
        return false;
    }

    @Override
    public String getPasswordHash(String login) {
        return null;
    }

    @Override
    public LocalDateTime getActivationDate(String login) {
        return null;
    }

    @Override
    public RolesResponseDto getRoles(String login) {
        return null;
    }

    @Override
    public RolesResponseDto addRole(String login, String role) {
        return null;
    }

    @Override
    public RolesResponseDto removeRole(String login, String role) {
        return null;
    }
}
