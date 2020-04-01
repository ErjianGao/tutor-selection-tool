package com.erjiangao.tutorselectiontool.repository;

import com.erjiangao.tutorselectiontool.entity.Admin;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends BaseRepository<Admin, Integer> {
    @Query("SELECT admin FROM Admin admin WHERE admin.username=:username")
    Optional<Admin> findAdminByUsername(@Param("username") String username);
}
