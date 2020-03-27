package com.erjiangao.tutorselectiontool.service;

import com.erjiangao.tutorselectiontool.entity.Direction;
import com.erjiangao.tutorselectiontool.entity.DirectionSelection;
import com.erjiangao.tutorselectiontool.entity.Student;
import com.erjiangao.tutorselectiontool.repository.DirectionRepository;
import com.erjiangao.tutorselectiontool.repository.DirectionSelectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DirectionService {
    @Autowired
    private DirectionService directionService;
    @Autowired
    private DirectionRepository directionRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private DirectionSelectionRepository directionSelectionRepository;

    // Direction CURD
    public Direction addDirection(Direction direction) {
        directionRepository.save(direction);
        return direction;
    }

    public Direction getDirection(int id) {
        return directionRepository.findById(id)
                .orElse(null);
    }

    public List<Direction> listDirections() {
        return directionRepository.list()
                .orElse(null);
    }

    public List<Direction> listDirections(int sid) {
        return directionRepository.list()
                .orElse(null);
    }

    public Direction updateDirection(Direction direction) {
        directionRepository.save(direction);
        return direction;
    }

    // Direction Selection CURD
    public DirectionSelection addDirectionSelection(int sid, int directionId) {
        Direction direction = directionService.getDirection(directionId);
        Student student = userService.getStudent(sid);
        DirectionSelection directionSelection = new DirectionSelection();
        directionSelection.setDirection(direction);
        directionSelection.setStudent(student);
        directionSelectionRepository.save(directionSelection);
        return directionSelection;
    }

    public void deleteDirectionSelection(int id) {
        directionSelectionRepository.deleteById(id);
    }

    public DirectionSelection updateDirectionSelection(DirectionSelection directionSelection) {
        directionSelectionRepository.save(directionSelection);
        return directionSelection;
    }
}
