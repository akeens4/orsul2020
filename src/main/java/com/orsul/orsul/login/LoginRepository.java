package com.orsul.orsul.login;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface LoginRepository extends JpaRepository<LoginForm,Long> {

    Optional<LoginForm> findByUsernameAndPassword(String username, String password);


}
