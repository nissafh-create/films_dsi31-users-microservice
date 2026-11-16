package projet.dsi31.usersmicroservice.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import projet.dsi31.usersmicroservice.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByRole(String role);
}

