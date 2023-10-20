package com.ra.controller;

import com.ra.model.Student;
import com.ra.service.IStudentService;
import com.ra.serviceImp.StudentServiceImp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@WebServlet(name = "StudentController", value = "/StudentController")
public class StudentController extends HttpServlet {
    private IStudentService studentService;

    public StudentController() {
        studentService = new StudentServiceImp();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action != null && action.equals("initUpdate")) {
            //Lấy thông tin student theo id để hiển thị cho người dùng cập nhật
            //Bước 1: lấy studentId từ request
            int studentId = Integer.parseInt(request.getParameter("studentId"));
            //Bước 2: gọi studentService lấy student qua id
            Student studentUpdate = studentService.getStudentById(studentId);
            //Bước 3: Chuyển dữ liệu sang updateStudent.jsp để hiển thị
            request.setAttribute("updateStudent",studentUpdate);
            request.getRequestDispatcher("views/updateStudent.jsp").forward(request,response);
        } else if (action!=null && action.equals("delete")) {
            //Thực hiện xóa sinh viên
            //Bước 1: Lấy studentId từ request
            int studentId = Integer.parseInt(request.getParameter("studentId"));
            //Bước 2: Goi studentService thực hiện xóa
            boolean result = studentService.deleteStudent(studentId);
            //Bước 3: Gọi getallStudent để hiển thị dữ liệu
            if (result){
                getAllStudent(request,response);
            }else{
                request.getRequestDispatcher("error.jsp").forward(request,response);
            }
        } else {
            getAllStudent(request, response);
        }
    }

    public void getAllStudent(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
        //set request hỗ trợ utf-8
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        if (action.equals("create")) {
            //Thực hiện thêm mới 1 student
            //1. Lấy dữ liệu từ student.jsp và lưu vào 1 đối tượng student
            Student newStudent = new Student();
            newStudent.setStudentName(request.getParameter("studentName"));
            newStudent.setAge(Integer.parseInt(request.getParameter("age")));
            //String --> java.util.Date
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                newStudent.setBirthDate(sdf.parse(request.getParameter("birthDate")));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            newStudent.setStatus(Boolean.parseBoolean(request.getParameter("status")));
            //2. Gọi sang service thực hiện thêm mới
            boolean result = studentService.createStudent(newStudent);
            //3. gọi getAllStudent để lấy lại dữ liệu hiện tại trong db và hiển thị ra students.jsp
            if (result) {
                getAllStudent(request, response);
            } else {
                //Có lỗi trong quá trình thực hiện --> error.jsp để thông báo lỗi
                request.getRequestDispatcher("views/error.jsp").forward(request, response);
            }
        } else if (action.equals("update")) {
            //Thực hiện cập nhật thông tin sinh viên
            //Bước 1: lấy thông tin cập nhật từ updateStudent.jsp --> updateStudent
            //Thực hiện thêm mới 1 student
            //1. Lấy dữ liệu từ updateStudent.jsp và lưu vào 1 đối tượng student
            Student updateStudent = new Student();
            updateStudent.setStudentId(Integer.parseInt(request.getParameter("studentId")));
            updateStudent.setStudentName(request.getParameter("studentName"));
            updateStudent.setAge(Integer.parseInt(request.getParameter("age")));
            //String --> java.util.Date
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                updateStudent.setBirthDate(sdf.parse(request.getParameter("birthDate")));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            updateStudent.setStatus(Boolean.parseBoolean(request.getParameter("status")));
            //Bước 2: gọi sang studentService thực hiện cập nhật
            boolean result = studentService.updateStudent(updateStudent);
            //Bước 3: gọi getAllStudent hiển thị danh sách sinh viên sau cập nhật
            if (result){
                getAllStudent(request,response);
            }else{
                request.getRequestDispatcher("error.jsp").forward(request,response);
            }
        }
    }
}
