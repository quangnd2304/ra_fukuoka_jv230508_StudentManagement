package com.ra.controller;

import com.ra.model.Student;
import com.ra.service.IStudentService;
import com.ra.serviceImp.StudentServiceImp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "StudentController", value = "/StudentController")
public class StudentController extends HttpServlet {
    private IStudentService studentService;

    public StudentController() {
        studentService = new StudentServiceImp();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //response.sendRedirect("StudentController)
        //Gọi sang service để lấy tất cả sinh viên
        List<Student> listStudents = studentService.getAllStudent();
        //Chuyển listStudent sang students.jsp - set listStudents vào request
        request.setAttribute("listStudents", listStudents);
        //Chuyển sang view - students.jsp
        RequestDispatcher rd = request.getRequestDispatcher("views/students.jsp");
        //Forward toàn bộ request và response sang trang students.jsp
        rd.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
