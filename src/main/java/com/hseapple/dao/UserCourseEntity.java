package com.hseapple.dao;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "user_course", schema = "public")
public class UserCourseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Column(name = "courseid")
    private Integer courseID;
    @NotNull
    @Column(name = "userid")
    private Long userID;
    @NotNull
    @Column(name = "roleid")
    private Integer roleID;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCourseID() {
        return courseID;
    }

    public void setCourseID(Integer courseID) {
        this.courseID = courseID;
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public Integer getRoleID() {
        return roleID;
    }

    public void setRoleID(Integer roleID) {
        this.roleID = roleID;
    }


    //getters and setters
}
