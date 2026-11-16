package projet.dsi31.usersmicroservice.service;

import projet.dsi31.usersmicroservice.entities.Role;
import projet.dsi31.usersmicroservice.entities.User;
import projet.dsi31.usersmicroservice.register.RegistationRequest;

import java.util.List;

public interface UserService {
    User saveUser(User user);
    User findUserByUsername(String username);
    Role addRole(Role role);
    User addRoleToUser(String username, String rolename);
    List<User> findAllUsers();
    User registerUser(RegistationRequest request);
    User validateToken(String code);
}

