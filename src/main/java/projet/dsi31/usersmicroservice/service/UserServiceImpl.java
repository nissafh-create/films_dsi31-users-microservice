package projet.dsi31.usersmicroservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projet.dsi31.usersmicroservice.entities.Role;
import projet.dsi31.usersmicroservice.entities.User;
import projet.dsi31.usersmicroservice.entities.VerificationToken;
import projet.dsi31.usersmicroservice.exception.EmailAlreadyExistsException;
import projet.dsi31.usersmicroservice.exception.ExpiredTokenException;
import projet.dsi31.usersmicroservice.exception.InvalidTokenException;
import projet.dsi31.usersmicroservice.register.RegistationRequest;
import projet.dsi31.usersmicroservice.repos.RoleRepository;
import projet.dsi31.usersmicroservice.repos.UserRepository;
import projet.dsi31.usersmicroservice.repos.VerificationTokenRepository;
import projet.dsi31.usersmicroservice.util.EmailSender;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Transactional
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRep;

    @Autowired
    RoleRepository roleRep;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    VerificationTokenRepository verificationTokenRepo;

    @Autowired
    EmailSender emailSender;

    @Override
    public User saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userRep.save(user);
    }

    @Override
    public User addRoleToUser(String username, String rolename) {
        User usr = userRep.findByUsername(username);
        Role r = roleRep.findByRole(rolename);
        usr.getRoles().add(r);
        return usr;
    }

    @Override
    public Role addRole(Role role) {
        return roleRep.save(role);
    }

    @Override
    public User findUserByUsername(String username) {
        return userRep.findByUsername(username);
    }

    @Override
    public List<User> findAllUsers() {
        return userRep.findAll();
    }

    @Override
    public User registerUser(RegistationRequest request) {
        Optional<User> optionaluser = userRep.findByEmail(request.getEmail());
        if (optionaluser.isPresent()) {
            throw new EmailAlreadyExistsException("Email already exists!");
        }

        User newUser = new User();
        newUser.setUsername(request.getUsername());
        newUser.setEmail(request.getEmail());
        newUser.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));
        newUser.setEnabled(false);
        userRep.save(newUser);

        // Add default role USER to newUser
        Role r = roleRep.findByRole("USER");
        List<Role> roles = new ArrayList<>();
        roles.add(r);
        newUser.setRoles(roles);

        userRep.save(newUser);

        // Generate secret code
        String code = this.generateCode();

        VerificationToken token = new VerificationToken(code, newUser);
        verificationTokenRepo.save(token);

        System.out.println(
                ">>> VERIFICATION CODE FOR " + newUser.getUsername() + " (" + newUser.getEmail() + "): " + code);

        sendEmailUser(newUser, code);
        return newUser;
    }

    public String generateCode() {
        Random random = new Random();
        Integer code = 100000 + random.nextInt(900000);
        return code.toString();
    }

    public void sendEmailUser(User u, String code) {
        String emailBody = "Hello <h1>" + u.getUsername() + "</h1>" +
                " Your validation code is <h1>" + code + "</h1>";
        emailSender.sendEmail(u.getEmail(), emailBody);
    }

    @Override
    public User validateToken(String code) {
        VerificationToken token = verificationTokenRepo.findByToken(code);
        if (token == null) {
            throw new InvalidTokenException("Invalid Token");
        }

        User user = token.getUser();
        Calendar calendar = Calendar.getInstance();
        if ((token.getExpirationTime().getTime() - calendar.getTime().getTime()) <= 0) {
            verificationTokenRepo.delete(token);
            throw new ExpiredTokenException("Expired Token");
        }
        user.setEnabled(true);
        userRep.save(user);
        return user;
    }
}
