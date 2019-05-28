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

    List<Student> findAllByOrderByGpaDesc();

    Student saveOrUpdateStudent(Student student);

    void deleteStudentById(String id);

}
