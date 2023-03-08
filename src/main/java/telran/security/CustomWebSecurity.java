package telran.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import telran.accounting.domain.entities.UserAccount;
import telran.accounting.domain.repo.UserAccountsRepository;

@Service
public class CustomWebSecurity {
    @Autowired
    UserAccountsRepository repository;

    public boolean checkOwner(String login){
        UserAccount account = repository.findById(login).orElse(null);
        return account != null;
    }
}
