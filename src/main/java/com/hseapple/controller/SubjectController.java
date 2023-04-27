package com.hseapple.controller;

import com.hseapple.dao.CourseEntity;
import com.hseapple.dao.SubjectEntity;
import com.hseapple.dao.TaskEntity;
import com.hseapple.service.SubjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@SecurityRequirement(name = "Authorization")
@Tag(description = "Api to manage subjects",
        name = "Subject Resource")
public class SubjectController {

    @Autowired
    SubjectService subjectService;

    @Operation(summary = "Get subjects",
            description = "Provides all available subjects. Access roles - TEACHER, ASSIST, STUDENT")
    @PreAuthorize("hasAnyAuthority('TEACHER', 'STUDENT', 'ASSIST')")
    @RequestMapping(value = "/subjects", method = RequestMethod.GET)
    @ResponseBody
    public Iterable<SubjectEntity> getSubjects(){
        return subjectService.findAllSubjects();
    }

    @Operation(summary = "Create subject",
            description = "Creates new subject. Access roles - TEACHER")
    @PreAuthorize("hasAuthority('TEACHER')")
    @RequestMapping(value = "/subject", method = RequestMethod.POST)
    @ResponseBody
    public SubjectEntity createSubject(@Valid @RequestBody SubjectEntity subjectEntity) {
        return subjectService.createSubject(subjectEntity);
    }

    @Operation(summary = "Update subject",
            description = "Provides new updated subject. Access roles - TEACHER")
    @PreAuthorize("hasAuthority('TEACHER')")
    @RequestMapping(value = "/subject/{subjectID}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<?> updateSubject(@RequestBody SubjectEntity newSubject, @PathVariable("subjectID") Long subjectID){
        return subjectService.updateSubject(newSubject, subjectID);
    }

    @Operation(summary = "Delete subject",
            description = "Delete subject. Access roles - TEACHER")
    @PreAuthorize("hasAuthority('TEACHER')")
    @DeleteMapping(value = "/subject/{subjectID}")
    public ResponseEntity<?> deleteSubject(@PathVariable Long subjectID) {
        return subjectService.deleteSubject(subjectID);
    }

}