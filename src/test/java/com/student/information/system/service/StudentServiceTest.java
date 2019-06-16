package com.student.information.system.service;

import com.student.information.system.model.Student;
import com.student.information.system.repository.StudentRepository;
import com.student.information.system.service.impl.StudentServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author ragcrix
 */
@RunWith(SpringRunner.class)
public class StudentServiceTest {

    @TestConfiguration
    static class StudentServiceImplTestContextConfiguration {
        @Bean
        public StudentService studentService() {
            return new StudentServiceImpl();
        }
    }

    @Autowired
    private StudentService studentService;

    @MockBean
    private StudentRepository studentRepository;

    private Student ragcrix;
    private Student yigit;

    private final Long ragcrixStudentNumber = 23L;
    private final Long yigitStudentNumber = 91L;
    private final String ragcrixEmail = "ragcrix@rg.com";
    private final String yigitEmail = "yigit@ygt.com";
    private final List<Student> students = new ArrayList<>();

    @Before
    public void setup() {
        ragcrix = new Student();
        ragcrix.setId("aBc123");
        ragcrix.setName("ragcrix");
        ragcrix.setEmail(ragcrixEmail);
        ragcrix.setStudentNumber(ragcrixStudentNumber);
        ragcrix.setCourseList(Arrays.asList("Math", "Science"));
        ragcrix.setGpa(3.37f);

        yigit = new Student();
        yigit.setId("dEf345");
        yigit.setName("yigit");
        yigit.setEmail(yigitEmail);
        yigit.setStudentNumber(yigitStudentNumber);
        yigit.setCourseList(Arrays.asList("Turkish", "German"));
        yigit.setGpa(3.58f);

        students.add(ragcrix);
        students.add(yigit);

        Mockito.when(studentRepository.findAll()).thenReturn(students);

        Mockito.when(studentRepository.findByStudentNumber(ragcrixStudentNumber))
                .thenReturn(ragcrix);

        Mockito.when(studentRepository.findByEmail(yigitEmail))
                .thenReturn(yigit);

        Mockito.when(studentRepository.findAllByOrderByGpaDesc())
                .thenReturn(students.stream().sorted(
                        Comparator.comparing(Student::getGpa).reversed()).collect(Collectors.toList()));

        Mockito.when(studentRepository.save(ragcrix)).thenReturn(ragcrix);
    }

    @Test
    public void testFindAll_thenStudentListShouldBeReturned() {
        List<Student> foundStudents = studentService.findAll();

        assertNotNull(foundStudents);
        assertEquals(2, foundStudents.size());
    }

    @Test
    public void testFindByStudentNumber23_thenRagcrixShouldBeReturned() {
        Student found = studentService.findByStudentNumber(ragcrixStudentNumber);

        assertNotNull(found);
        assertEquals(ragcrix.getName(), found.getName());
        assertEquals(ragcrix.getId(), found.getId());
    }

    @Test
    public void testFindByEmail_thenYigitShouldBeReturned() {
        Student found = studentService.findByEmail(yigitEmail);

        assertNotNull(found);
        assertEquals(yigit.getName(), found.getName());
        assertEquals(yigit.getId(), found.getId());
    }

    @Test
    public void testFindAllByOrderByGpaDesc_thenStudentsShouldBeReturned_byGPADesc() {
        List<Student> foundStudents = studentService.findAllByOrderByGpaDesc();

        assertNotNull(foundStudents);
        assertEquals(2, foundStudents.size());
        assertEquals(yigit.getName(), foundStudents.get(0).getName());
        assertEquals(yigit.getId(), foundStudents.get(0).getId());
    }

    @Test
    public void testSaveOrUpdateStudent_thenStudentShouldBeReturned() {
        Student found = studentService.saveOrUpdateStudent(ragcrix);

        assertNotNull(found);
        assertEquals(ragcrix.getName(), found.getName());
        assertEquals(ragcrix.getId(), found.getId());
    }

    @Test
    public void testDeleteStudentById() {
        studentService.deleteStudentById(ragcrix.getId());

        Mockito.verify(studentRepository, Mockito.times(1))
                .deleteById(ragcrix.getId());
    }

}
