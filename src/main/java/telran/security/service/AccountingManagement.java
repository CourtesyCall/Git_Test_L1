package telran.security.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import telran.accounting.domain.entities.UserAccount;
import telran.accounting.domain.repo.UserAccountsRepository;
import telran.accounting.dto.RolesResponseDto;
import telran.accounting.dto.UserAccountResponseDto;
import telran.accounting.dto.UserRegisterDto;
import telran.accounting.dto.UserUpdateDto;
import telran.accounting.dto.exceptions.UserAlreadyExistException;
import telran.accounting.dto.exceptions.WrongPasswordException;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.LinkedList;

@Service
public class AccountingManagement implements IAccounting, CommandLineRunner {

    @Autowired
    UserAccountsRepository repository;

    @Autowired
    PasswordEncoder encoder;

    @Value("${password_length:5}")
    private int password_length;

    @Override
    public UserAccountResponseDto registration(UserRegisterDto user) {
        if(repository.existsById(user.getLogin()))
            throw new UserAlreadyExistException(user.getLogin());
        if(!isPasswordValid(user.getPassword()))
            throw new WrongPasswordException(user.getPassword());
        UserAccount account = new UserAccount(user.getLogin(), getHashCode(user.getPassword()),
                user.getFirstName(), user.getLastName());
        repository.save(account);
        return new UserAccountResponseDto(user.getLogin(), user.getFirstName(), user.getLastName(),
                account.getRoles());
    }

    private String getHashCode(String password) {
        return encoder.encode(password);
    }

    private boolean isPasswordValid(String password) {
        return password.length() >= password_length;
    }

    @Override
    public UserAccountResponseDto removeUser(String login) {
        return null;
    }

    @Override
    public UserAccountResponseDto getUser(String login) {
        return null;
    }

    @Override
    public UserAccountResponseDto updateUser(String login, UserUpdateDto user) {
        return null;
    }

    @Override
    public boolean updatePassword(String login, String password) {

        UserAccount account = repository.findById(login).orElseThrow(() -> new UsernameNotFoundException(login));

        if(encoder.matches(password,account.getHashCode()))
            throw new WrongPasswordException(password);


        //TODO
        account.setHashCode(encoder.encode(password));
        account.setActivationDate(LocalDateTime.now());
        repository.save(account);
        return true;
    }

    private boolean isPasswordFromLast(LinkedList<String> lastHashCodes,String password){
        return lastHashCodes.
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

    @Override
    public void run(String... args) throws Exception {
        if(!repository.existsById("admin")){
            UserAccount account = new UserAccount("admin", encoder.encode("admin"),"","");
            account.setRoles(new HashSet<>());
        }
    }
}
