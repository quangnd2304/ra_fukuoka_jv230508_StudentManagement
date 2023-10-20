package com.ra.repositoryImp;

import com.ra.model.Student;
import com.ra.repository.IStudentRepository;
import com.ra.util.ConnectionDB;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class StudentRepositoryImp implements IStudentRepository {
    @Override
    public List<Student> getAllStudent() {
        List<Student> listStudent = null;
        Connection conn = null;
        CallableStatement callSt = null;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{call get_all_student()}");
            ResultSet rs = callSt.executeQuery();
            listStudent = new ArrayList<>();
            while (rs.next()){
                Student student = new Student();
                student.setStudentId(rs.getInt("student_id"));
                student.setStudentName(rs.getString("student_name"));
                student.setAge(rs.getInt("age"));
                student.setBirthDate(rs.getDate("birth_date"));
                student.setStatus(rs.getBoolean("student_status"));
                listStudent.add(student);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            ConnectionDB.closeConnection(conn,callSt);
        }
        return listStudent;
    }

    @Override
    public boolean createStudent(Student student) {
        Connection conn = null;
        CallableStatement callSt = null;
        boolean result = false;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{call create_student(?,?,?,?)}");
            callSt.setString(1,student.getStudentName());
            callSt.setInt(2,student.getAge());
            //convert java.util.Date to java.sql.Date
            callSt.setDate(3,new Date(student.getBirthDate().getTime()));
            callSt.setBoolean(4,student.isStatus());
            callSt.executeUpdate();
            result = true;
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            ConnectionDB.closeConnection(conn,callSt);
        }
        return result;
    }

    @Override
    public Student getStudentById(int studentId) {
        Student student = null;
        Connection conn = null;
        CallableStatement callSt = null;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{call get_student_by_id(?)}");
            callSt.setInt(1,studentId);
            ResultSet rs = callSt.executeQuery();
            student = new Student();
            if (rs.next()){
                student.setStudentId(rs.getInt("student_id"));
                student.setStudentName(rs.getString("student_name"));
                student.setAge(rs.getInt("age"));
                student.setBirthDate(rs.getDate("birth_date"));
                student.setStatus(rs.getBoolean("student_status"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            ConnectionDB.closeConnection(conn,callSt);
        }
        return student;
    }

    @Override
    public boolean updateStudent(Student student) {
        Connection conn = null;
        CallableStatement callSt = null;
        boolean result = false;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{call update_student(?,?,?,?,?)}");
            callSt.setInt(1,student.getStudentId());
            callSt.setString(2,student.getStudentName());
            callSt.setInt(3,student.getAge());
            //convert java.util.Date to java.sql.Date
            callSt.setDate(4,new Date(student.getBirthDate().getTime()));
            callSt.setBoolean(5,student.isStatus());
            callSt.executeUpdate();
            result = true;
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            ConnectionDB.closeConnection(conn,callSt);
        }
        return result;
    }

    @Override
    public boolean deleteStudent(int studentId) {
        Connection conn = null;
        CallableStatement callSt = null;
        boolean result = false;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{call delete_student(?)}");
            callSt.setInt(1,studentId);
            callSt.executeUpdate();
            result = true;
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            ConnectionDB.closeConnection(conn,callSt);
        }
        return result;
    }
}
