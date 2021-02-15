package dev.ershov.vd.service;

import dev.ershov.vd.entities.UserTg;
import dev.ershov.vd.entities.UserTgRepository;
import org.springframework.stereotype.Service;

@Service
public class UsersTgService {
    private final UserTgRepository userTgRepository;

    public UsersTgService(UserTgRepository adminRepository) {
        this.userTgRepository = adminRepository;
    }

    public void addUserTg(int chatId) {
        userTgRepository.save(new UserTg(chatId, "null", 0, -1, true));
    }

    public boolean updateUserTg(int chatId, int role, int university) {
        UserTg admin = userTgRepository.findById(chatId).orElse(null);
        if (admin != null) {
            admin.setRole(role);
            admin.setUniversity(university);
            userTgRepository.save(admin);
            return true;
        }
        return false;
    }

    public boolean updateRole(int chatId, int role) {
        UserTg admin = userTgRepository.findById(chatId).orElse(null);
        if (admin != null) {
            admin.setRole(role);
            userTgRepository.save(admin);
            return true;
        }
        return false;
    }

    public boolean existById(int chatId) {
        return userTgRepository.existsById(chatId);
    }

    public UserTg findById(int chatId) {
        return userTgRepository.findById(chatId).orElse(null);
    }

    public boolean updateLastNameAndOne(int chatId, String name, boolean one) {
        UserTg admin = userTgRepository.findById(chatId).orElse(null);
        if (admin != null) {
            admin.setLastName(name);
            admin.setOne(one);
            userTgRepository.save(admin);
            return true;
        }
        return false;
    }
}
