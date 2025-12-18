
package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.StudentEntity;

public interface StudentService {

    public StudentEntity addStudents(StudentEntity student);

    public List<StudentEntity> getStudents();

    public StudentEntity getStudentById(Long id);

    public void deleteStudentById(Long id);

}






