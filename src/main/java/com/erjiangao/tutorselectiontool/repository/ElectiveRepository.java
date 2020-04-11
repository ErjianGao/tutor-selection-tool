package com.erjiangao.tutorselectiontool.repository;

import com.erjiangao.tutorselectiontool.entity.Elective;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ElectiveRepository extends BaseRepository<Elective, Integer> {
    Optional<List<Elective>> findElectivesByCourse_Id(int cid);

    Optional<List<Elective>> findElectivesByStudent_Id(int sid);
}
