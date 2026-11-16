package projet.dsi31.usersmicroservice.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import projet.dsi31.usersmicroservice.entities.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    Optional<User> findByEmail(String email);
}

