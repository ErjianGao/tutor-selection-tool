package com.erjiangao.tutorselectiontool.service;

import com.erjiangao.tutorselectiontool.entity.Admin;
import com.erjiangao.tutorselectiontool.entity.User;
import com.erjiangao.tutorselectiontool.repository.AdminRepository;
import com.erjiangao.tutorselectiontool.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private PasswordEncoder encoder;

    public User getUser(String idNo) {
        return userRepository.findUserByIdentityNo(idNo).orElse(null);
    }

    // ----------------Admin CURD----------------
    public void addAdmin(Admin admin) {
        adminRepository.save(admin);
    }

    public Admin updatePassword(String idNo, String newPwd) {
        Admin admin = adminRepository.findAdminByIdentityNo(idNo)
                .orElseThrow();
        admin.setPassword(encoder.encode(newPwd));
        return admin;
    }
}
