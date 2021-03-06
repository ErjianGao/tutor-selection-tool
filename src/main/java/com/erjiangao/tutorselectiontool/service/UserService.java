package com.erjiangao.tutorselectiontool.service;

import com.erjiangao.tutorselectiontool.entity.Admin;
import com.erjiangao.tutorselectiontool.entity.User;
import com.erjiangao.tutorselectiontool.repository.AdminRepository;
import com.erjiangao.tutorselectiontool.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private PasswordEncoder encoder;

    public User getUser(String identityNo) {
        return userRepository.findUserByIdentityNo(identityNo).orElse(null);
    }

    public User getUser(int uid) {
        return userRepository.findById(uid)
                .orElse(null);
    }

    public User updatePassword(int uid, String newPwd) {
        User user = getUser(uid);
        user.setPassword(encoder.encode(newPwd));
        return user;
    }

    // ----------------Admin CURD----------------
    public void addAdmin(Admin admin) {
        adminRepository.save(admin);
    }
}
