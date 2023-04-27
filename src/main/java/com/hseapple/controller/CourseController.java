package com.hseapple.controller;

import com.hseapple.dao.CourseEntity;
import com.hseapple.dao.RequestEntity;
import com.hseapple.service.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@SecurityRequirement(name = "Authorization")
@Tag(description = "Api to manage courses",
        name = "Course Resource")
public class CourseController {

    @Autowired
    CourseService courseService;

    Logger logger = LoggerFactory.getLogger(CourseController.class);

    @Operation(summary = "Get courses",
            description = "Provides all available courses. Access roles - TEACHER, ASSIST, STUDENT")
    @PreAuthorize("hasAnyAuthority('TEACHER', 'STUDENT', 'ASSIST')")
    @RequestMapping(value = "/subject/courses", method = RequestMethod.GET)
    @ResponseBody
    public Iterable<CourseEntity> getCourses(){
        return courseService.findAllCourse();
    }

    @Operation(summary = "Get course requests",
            description = "Provides all available course requests. Access roles - TEACHER")
    @PreAuthorize("hasAuthority('TEACHER')")
    @RequestMapping(value = "/subject/course/{courseID}/application/list", method = RequestMethod.GET)
    @ResponseBody
    public Iterable<RequestEntity> getListRequests(@PathVariable(name = "courseID") Integer courseID, @RequestParam("approved") Boolean approved){
        return courseService.findAllRequests(courseID, approved);
    }

    @Operation(summary = "Create course",
            description = "Creates new course. Access roles - TEACHER")
    @PreAuthorize("hasAuthority('TEACHER')")
    @RequestMapping(value = "/subject/course", method = RequestMethod.POST)
    @ResponseBody
    public CourseEntity createCourse(@Valid @RequestBody CourseEntity courseEntity) {
        return courseService.createCourse(courseEntity);
    }

    @Operation(summary = "Update course",
            description = "Provides new updated course. Access roles - TEACHER")
    @PreAuthorize("hasAuthority('TEACHER')")
    @RequestMapping(value = "/subject/course/{courseID}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<?> updateCourse(@RequestBody CourseEntity newCourse, @PathVariable("courseID") Integer courseID){
        return courseService.updateCourse(newCourse, courseID);
    }

    @Operation(summary = "Delete course",
            description = "Delete course. Access roles - TEACHER")
    @PreAuthorize("hasAuthority('TEACHER')")
    @DeleteMapping(value = "/subject/course/{courseID}")
    public ResponseEntity<?> deleteCourse(@PathVariable Integer courseID) {
        return courseService.deleteCourse(courseID);
    }


}