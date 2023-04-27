package com.hseapple.service;

import com.hseapple.app.security.UserAndRole;
import com.hseapple.dao.SubjectDao;
import com.hseapple.dao.SubjectEntity;
import com.hseapple.dao.TaskEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class SubjectService {
    @Autowired
    SubjectDao subjectDao;

    public Iterable<SubjectEntity> findAllSubjects() {
        return subjectDao.findAll();
    }

    public SubjectEntity createSubject(SubjectEntity subjectEntity) {
        return subjectDao.save(subjectEntity);
    }

    public ResponseEntity<?> updateSubject(SubjectEntity newSubject, Long subjectID) {
        Optional<SubjectEntity> subject = subjectDao.findById(subjectID);
        if (subject.isEmpty()){
            return ResponseEntity.badRequest().body("Subject not found");
        }
        subject.ifPresent(s -> {
            s.setSubjectName(newSubject.getSubjectName());
            subjectDao.save(s);
        });
        return ResponseEntity.ok("Subject updated");
    }

    public ResponseEntity<?> deleteSubject(Long subjectID) {
        Optional<SubjectEntity> subject = subjectDao.findById(subjectID);
        if (subject.isEmpty()){
            return ResponseEntity.badRequest().body("Subject not found");
        }
        subject.ifPresent(s -> {
            subjectDao.deleteSubjectById(subjectID);
        });
        return ResponseEntity.ok("Subject deleted");
    }

}