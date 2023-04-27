package com.hseapple.controller;

import com.hseapple.dao.TaskEntity;
import com.hseapple.dao.UserTaskEntity;
import com.hseapple.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
@Tag(description = "Api to manage tasks",
        name = "Task Resource")
public class TaskController {

    @Autowired
    TaskService taskService;

    Logger logger = LoggerFactory.getLogger(TaskController.class);

    @Operation(summary = "Get task for course",
            description = "Provides task for course")
    @RequestMapping(value = "/task/{taskID}", method = RequestMethod.GET)
    @ResponseBody
    @PreAuthorize("hasAnyAuthority('TEACHER', 'STUDENT', 'ASSIST')")
    public TaskEntity getTaskForCourse(@PathVariable("taskID") Long taskID){
        return taskService.getTaskForCourse(taskID);
    }

    @Operation(summary = "Update task",
            description = "Provides new updated task. Access roles - TEACHER")
    @PreAuthorize("hasAuthority('TEACHER')")
    @RequestMapping(value = "/task/{taskID}", method = RequestMethod.PUT)
    @ResponseBody
    public TaskEntity updateTask(@RequestBody TaskEntity newTask, @PathVariable("taskID") Long taskID){
        return taskService.updateTask(newTask, taskID);
    }

    @Operation(summary = "Delete task",
            description = "Delete task. Access roles - TEACHER")
    @PreAuthorize("hasAuthority('TEACHER')")
    @DeleteMapping(value = "task/{taskID}")
    public void deleteTask(@PathVariable Long taskID) {
         taskService.deleteTask(taskID);
    }

    @Operation(summary = "Get tasks for course",
            description = "Provides all available tasks for course")
    @RequestMapping(value = "/course/{courseID}/task", method = RequestMethod.GET)
    @ResponseBody
    @PreAuthorize("hasAnyAuthority('TEACHER', 'STUDENT', 'ASSIST')")
    public Iterable<TaskEntity> getTasks(@PathVariable Integer courseID, @RequestParam("start") Long start){
        return taskService.findTasks(courseID, start);
    }

    @Operation(summary = "Create task",
            description = "Creates new task. Access roles - TEACHER")
    @PreAuthorize("hasAuthority('TEACHER')")
    @RequestMapping(value = "/course/task", method = RequestMethod.POST)
    @ResponseBody
    public TaskEntity createTask(@Valid @RequestBody TaskEntity taskEntity) {
        return taskService.createTask(taskEntity);
    }

    @Operation(summary = "Get answer tasks",
            description = "Provides all available student task answers. Access roles - TEACHER, ASSIST")
    @PreAuthorize("hasAnyAuthority('TEACHER', 'ASSIST')")
    @RequestMapping(value = "course/task/{taskID}", method = RequestMethod.GET)
    @ResponseBody
    public Iterable<UserTaskEntity> getAnswerTasks(@PathVariable Long taskID,
                                         @RequestParam("status") Boolean state_answer,
                                       @RequestParam(value = "form", required = false) String form){
        return taskService.findAnswerTasks(taskID, state_answer, form);
    }


    @Operation(summary = "Create answer",
            description = "Creates new answer. Access roles - TEACHER, ASSIST, STUDENT")
    @PreAuthorize("hasAnyAuthority('TEACHER', 'STUDENT', 'ASSIST')")
    @RequestMapping(value = "course/task/{taskID}/answer", method = RequestMethod.POST)
    @ResponseBody
    public UserTaskEntity createAnswer(@PathVariable("taskID") Long taskID, @Valid @RequestBody UserTaskEntity userTaskEntity) {
        return taskService.createAnswer(taskID, userTaskEntity);
    }

    @Operation(summary = "Put a student's grade on a task",
            description = "Provides new updated user task. Access roles - TEACHER, ASSIST")
    @PreAuthorize("hasAnyAuthority('TEACHER', 'ASSIST')")
    @RequestMapping(value = "/task/score", method = RequestMethod.PUT)
    @ResponseBody
    public UserTaskEntity updateUserTask(@RequestBody UserTaskEntity newUserTask){
        return taskService.updateUserTask(newUserTask);
    }

}