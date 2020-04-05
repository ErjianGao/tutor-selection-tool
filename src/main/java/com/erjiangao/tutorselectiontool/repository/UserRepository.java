package com.erjiangao.tutorselectiontool.repository;

import com.erjiangao.tutorselectiontool.entity.User;

import java.util.Optional;

public interface UserRepository extends BaseRepository<User, Integer> {
    Optional<User> findUserByIdentityNo(String identityNo);
}
