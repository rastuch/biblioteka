package com.studia.biblioteka.manager;

import com.studia.biblioteka.dao.UserRepo;
import com.studia.biblioteka.dao.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserManager {
    private final UserRepo userRepo;
    @Autowired
    public UserManager(UserRepo userRepo){
        this.userRepo = userRepo;
    }

    public Optional<User> findById(Long id) {
        return userRepo.findById(id);
    }
    public Iterable<User> findAll(String search, List<String> role) {
        if ((search == null || search.isEmpty()) && (role == null || role.isEmpty())) {
            return userRepo.findAll();
        } else if (search == null || search.isEmpty()) {
            return userRepo.findAllByRoleIsIn(role);
        } else if (role == null || role.isEmpty()) {
            return userRepo.findAllByEmailContainingOrFirstNameContainingOrLastNameContainingOrPhoneNumberContaining(search);
        } else {
            return userRepo.findAllByEmailContainingOrFirstNameContainingOrLastNameContainingOrPhoneNumberContainingOrRoleIsIn(search,role);
        }
    }

    public User save(User user) {
        return userRepo.save(user);
    }
    public void delete(Long id) {
        userRepo.deleteById(id);
    }

    public Optional<User> authenticate(String email, String password) {
        return userRepo.findByEmail(email)
                .filter(user -> user.getPassword().equals(password));
    }

    public Boolean isEmailExist(String email) {
        return userRepo.findByEmail(email).isPresent();
    }

    @EventListener(ApplicationReadyEvent.class)
    public void fillDbHelper() {
        save(new User(1L, "admin", "", "admin@admin.pl", "ADMIN","admin","0000000", null, null, null ));
    }


}
