package com.example.accountmanager.model.account.authorized;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorizedRepository extends JpaRepository<Authorized, Long> {
    Optional<Authorized> getAuthorizedById(long id);
    Optional<Authorized> getAuthorizedByUserId(long id);
}
