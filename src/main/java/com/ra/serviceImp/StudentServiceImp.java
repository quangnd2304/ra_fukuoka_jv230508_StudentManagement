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
}
