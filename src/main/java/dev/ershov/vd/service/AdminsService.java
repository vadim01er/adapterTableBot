package dev.ershov.vd.service;

import dev.ershov.vd.entities.Admin;
import dev.ershov.vd.entities.AdminRepository;
import org.springframework.stereotype.Service;

@Service
public class AdminsService {
    private final AdminRepository adminRepository;

    public AdminsService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    public void addAdmin(int chatId) {
        adminRepository.save(new Admin(chatId, "null"));
    }

    public boolean existById(int chatId) {
        return adminRepository.existsById(chatId);
    }

    public Admin findById(int chatId) {
        return adminRepository.findById(chatId).orElse(null);
    }

    public boolean updateLastName(int chatId, String name) {
        Admin admin = adminRepository.findById(chatId).orElse(null);
        if (admin != null) {
            admin.setLastName(name);
            adminRepository.save(admin);
            return true;
        }
        return false;
    }
}
