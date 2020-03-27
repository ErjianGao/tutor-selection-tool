package com.erjiangao.tutorselectiontool.repository;

import com.erjiangao.tutorselectiontool.entity.DirectionSelection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DirectionSelectionRepository extends BaseRepository<DirectionSelection, Integer> {
    @Query("SELECT ds FROM DirectionSelection ds WHERE ds.student.id=:sid")
    Optional<List<DirectionSelection>> list(int sid);
}
