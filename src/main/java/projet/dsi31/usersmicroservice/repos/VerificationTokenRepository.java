package projet.dsi31.usersmicroservice.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import projet.dsi31.usersmicroservice.entities.VerificationToken;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
    VerificationToken findByToken(String token);
}

