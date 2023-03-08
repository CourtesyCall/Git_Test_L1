package telran.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import telran.accounting.domain.entities.UserAccount;
import telran.accounting.domain.repo.UserAccountsRepository;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Configuration
public class AuthenticationConfigurations implements UserDetailsService {
    @Autowired
    UserAccountsRepository repository;

    @Value("${activationPeriod:3000}")
    int activationPeriod;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserAccount account = repository.findById(username).
                orElseThrow(() -> new UsernameNotFoundException(username));



        String password = account.getHashCode();
        boolean passwordIsNotExpired = ChronoUnit.MINUTES.between(account.getActivationDate(), LocalDateTime.now()) < activationPeriod;

        String[] roles = account.getRoles().stream().map(r-> "ROLE_" + r).toArray(String[]::new);
        return new UserProfile(username,password, AuthorityUtils.createAuthorityList(roles),passwordIsNotExpired);
    }
}
