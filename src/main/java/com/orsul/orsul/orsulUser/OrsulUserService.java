package com.orsul.orsul.orsulUser;

import com.orsul.orsul.login.LoginForm;
import com.orsul.orsul.registration.token.ConfirmationToken;
import com.orsul.orsul.registration.token.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class OrsulUserService implements UserDetailsService {

    private final static String USER_NOT_FOUND_MSG =
            "user with email %s not found";

    private final OrsulCustomerRepository customerRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenService confirmationTokenService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

            return customerRepository.findByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException(
                            String.format(USER_NOT_FOUND_MSG,email)));
    }

    public Optional<LoginForm> login(String username, String password) {
        Optional<LoginForm> loginForm = customerRepository.findByUsernameAndPassword(username,password);
        return loginForm;
    }

    public String signUpUser(OrsulUser orsulUser) {
        boolean userExists = customerRepository
                .findByEmail(orsulUser.getEmail())
                .isPresent();

        if(userExists){

            throw new IllegalStateException("email already taken");
        }

        String encodedPassword = bCryptPasswordEncoder
                .encode(orsulUser.getPassword());

        orsulUser.setPassword(encodedPassword);

        customerRepository.save(orsulUser);

        String token = UUID.randomUUID().toString();
        //TODO: Send confirmation token
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                orsulUser
        );

        confirmationTokenService.saveConfirmationToken(
                confirmationToken
        );

        // TODO SEND EMAIL

        return token;
    }

    public int enableAppUser(String email) {
        return customerRepository.enableAppUser(email);
    }

}
