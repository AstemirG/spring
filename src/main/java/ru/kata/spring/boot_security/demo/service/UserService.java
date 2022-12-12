package ru.kata.spring.boot_security.demo.service;

import jakarta.annotation.PostConstruct;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;


    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public User findById(Long id){ return userRepository.getById(id); }

    public List<User> findAll(){
        return userRepository.findAll();
    }

    public void saveUser(User user){
        User editedOrNewUser = userRepository.findByUsername(user.getUsername());
        editedOrNewUser.setRoles(Collections.singletonList(new Role(2L, "ROLE_USER")));
        editedOrNewUser.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(editedOrNewUser);
    }

    public void deleteById(Long id){
        userRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null)
            throw new UsernameNotFoundException("User not found");
        return user;
    }

    public User findUserByUserName(String userName) {
        return findAll().stream().filter(user -> user.getUsername().equals(userName)).findAny().orElse(null);
    }

    @PostConstruct
    private void postConstruct() {
        Role admin = new Role(1L, "ROLE_ADMIN");
        Role user = new Role(2L, "ROLE_USER");

        List<Role> rolesOfAdmin = new ArrayList<>();
        List<Role> rolesOfUser = new ArrayList<>();

        Collections.addAll(rolesOfAdmin, admin, user);
        Collections.addAll(rolesOfUser, user);

        User adminUser = new User(1L, "Anatolii", "Khitrov",
                "tt-7495@mail.ru", "admin", passwordEncoder.encode("admin1"), rolesOfAdmin);
        User normalUser = new User(2L, "Alexandr", "Russkin",
                "AlexandrRusskin@mail.ru", "user", passwordEncoder.encode("user1"), rolesOfUser);

        userRepository.save(adminUser);
        userRepository.save(normalUser);
    }



}
