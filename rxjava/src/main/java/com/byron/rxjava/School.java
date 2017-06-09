package com.byron.rxjava;

import java.util.List;

/**
 * Created by company-ios on 2017/6/6.
 */

public class School {

    public String name;
    public List<Student> students;

    public School(String name, List<Student> students) {
        this.name = name;
        this.students = students;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }
}
