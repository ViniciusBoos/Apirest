package com.github.viniciusboos.apirest.repositories;

import com.github.viniciusboos.apirest.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
