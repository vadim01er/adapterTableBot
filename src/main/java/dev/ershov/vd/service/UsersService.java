package dev.ershov.vd.service;

import dev.ershov.vd.entities.User;
import dev.ershov.vd.entities.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersService {

    private final UserRepository userRepository;

    public UsersService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> listAll() {
        return List.of(new User());
    }

    public User getByName(String name) {
        return userRepository.findById(name).orElse(null);
    }

    public void updateComment(String name, String comment) {
        if (!userRepository.existsById(name)) {
            userRepository.save(new User(name, comment));
        } else {
            User user = userRepository.findById(name).get();
            user.setComment(user.getComment() + "\n" + comment);
            userRepository.save(user);
        }
    }
}
