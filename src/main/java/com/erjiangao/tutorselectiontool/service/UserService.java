package com.erjiangao.tutorselectiontool.service;

import com.erjiangao.tutorselectiontool.entity.Admin;
import com.erjiangao.tutorselectiontool.entity.User;
import com.erjiangao.tutorselectiontool.repository.AdminRepository;
import com.erjiangao.tutorselectiontool.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AdminRepository adminRepository;

    public User getUser(String idNo) {
        return userRepository.findUserByIdentityNo(idNo).orElse(null);
    }

    // ----------------Admin CURD----------------
    public void addAdmin(Admin admin) {
        adminRepository.save(admin);
    }
}
