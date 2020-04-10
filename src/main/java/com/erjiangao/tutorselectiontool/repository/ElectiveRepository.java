package com.erjiangao.tutorselectiontool.repository;

import com.erjiangao.tutorselectiontool.entity.Elective;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ElectiveRepository extends BaseRepository<Elective, Integer> {
    @Query("SELECT e FROM Elective e WHERE e.student.id=:sid")
    Optional<List<Elective>> list(int sid);

    Optional<List<Elective>> findElectivesByCourse(int cid);
}
