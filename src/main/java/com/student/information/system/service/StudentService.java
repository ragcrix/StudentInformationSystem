package com.student.information.system.service;

import com.student.information.system.model.Student;

import java.util.List;

/**
 * @author regcrix
 */
public interface StudentService {

    List<Student> findAll();

    Student findByStudentNumber(long studentNumber);

    Student findByEmail(String email);

    List<Student> findAllByOrderByGpa();

    void saveOrUpdateStudent(Student student);

    void deleteStudent(String id);

}
