package dev.ershov.vd.entities;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface UserWebRepository extends JpaRepository<UserWeb, Long> {
    UserDetails findByUsername(String username);
}
