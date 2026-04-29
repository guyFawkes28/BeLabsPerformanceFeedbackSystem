package org.belabs.feedback.service;

import org.belabs.feedback.model.AdminUser;
import org.belabs.feedback.repository.AdminUserRepository;
import org.belabs.feedback.util.PasswordUtil;

public class AuthService {
    private final AdminUserRepository adminUserRepository;

    public AuthService(AdminUserRepository adminUserRepository) {
        this.adminUserRepository = adminUserRepository;
    }

    public boolean login(String email, String password) {
        AdminUser adminUser = adminUserRepository.findByEmail(email);
        return adminUser != null && PasswordUtil.checkPassword(password, adminUser.getPasswordHash());
    }
}
