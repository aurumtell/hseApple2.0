package com.hseapple.service;

import com.hseapple.dao.CourseDao;
import com.hseapple.dao.CourseEntity;
import com.hseapple.dao.RequestDao;
import com.hseapple.dao.RequestEntity;
import com.hseapple.dao.SubjectEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    @Autowired
    CourseDao courseDao;

    @Autowired
    RequestDao requestDao;

    public CourseService(CourseDao courseDao){
        this.courseDao = courseDao;
    }
    public List<CourseEntity> findAllCourse() { return courseDao.findAll(); };

    public Iterable<RequestEntity> findAllRequests(Integer courseID, Boolean approved) {
        return requestDao.findAllByCourseIDAndApproved(courseID, approved);
    }

    public CourseEntity createCourse(CourseEntity courseEntity) {
        return courseDao.save(courseEntity);
    }

    public ResponseEntity<?> updateCourse(CourseEntity newCourse, Integer courseID) {
        Optional<CourseEntity> course = courseDao.findById(courseID);
        if (course.isEmpty()){
            return ResponseEntity.badRequest().body("Course not found");
        }
        course.ifPresent(c -> {
            c.setDescription(newCourse.getDescription());
            c.setSubjectID(newCourse.getSubjectID());
            c.setTitle(newCourse.getTitle());
            courseDao.save(c);
        });
        return ResponseEntity.ok("Course updated");
    }

    public ResponseEntity<?> deleteCourse(Integer courseID) {
        Optional<CourseEntity> course = courseDao.findById(courseID);
        if (course.isEmpty()){
            return ResponseEntity.badRequest().body("Course not found");
        }
        course.ifPresent(c -> {
            courseDao.deleteCourseById(courseID);
        });
        return ResponseEntity.ok("Course deleted");
    }

}