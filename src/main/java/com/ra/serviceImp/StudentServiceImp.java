package com.ra.serviceImp;

import com.ra.model.Student;
import com.ra.repository.IStudentRepository;
import com.ra.repositoryImp.StudentRepositoryImp;
import com.ra.service.IStudentService;

import java.util.List;

public class StudentServiceImp implements IStudentService {
    //Khởi tạo đối tượng Repository để làm việc
    private IStudentRepository studentRepository;

    public StudentServiceImp() {
        studentRepository = new StudentRepositoryImp();
    }

    @Override
    public List<Student> getAllStudent() {
        return studentRepository.getAllStudent();
    }

    @Override
    public boolean createStudent(Student student) {
        return studentRepository.createStudent(student);
    }

    @Override
    public Student getStudentById(int studentId) {
        return studentRepository.getStudentById(studentId);
    }

    @Override
    public boolean updateStudent(Student student) {
        return studentRepository.updateStudent(student);
    }

    @Override
    public boolean deleteStudent(int studentId) {
        return studentRepository.deleteStudent(studentId);
    }
}
