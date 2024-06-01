package com.example.schoolmanagement.Datamanagement;

import androidx.annotation.NonNull;

import com.example.schoolmanagement.SQLServerHelper.SQLServerManagement;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Student {
    private String IdStudent;
    private String Department;
    private String Classes;

    public Student(String idStudent, String department, String aClass) {
        IdStudent = idStudent;
        Department = department;
        Classes = aClass;
    }

    // hàm lấy ra sinh viên
    public static ArrayList<Student> getuserlist() throws SQLException { // Hàm lấy dữ liệu
        Connection connection = SQLServerManagement.connectionSQLSever(); // Kết nối với SQL server
        ArrayList<Student> list = new ArrayList<>(); // Tạo list để lưu dữ liệu
        Statement statement = connection.createStatement();// Tạo đối tượng Statement.
        String sql = "select * from Student"; // Câu lênh truy vấn SQL Server lấy ra dữ liệu trong bảng
        // Thực thi câu lệnh SQL trả về đối tượng ResultSet. // Mọi kết quả trả về sẽ được lưu trong ResultSet
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next()) {
            list.add(new Student(
                    rs.getString(1).trim(), // Lấy dữ liệu
                    rs.getString(2).trim(),
                    rs.getString(3)));// Đọc dữ liệu từ ResultSet
        }
        statement.close(); // Đóng đối tương statement
        connection.close();// Đóng kết nối
        return list; // Trả về list
    }

    public String getIdStudent() {
        return IdStudent;
    }

    public void setIdStudent(String idStudent) {
        IdStudent = idStudent;
    }

    public String getDepartment() {
        return Department;
    }

    public void setDepartment(String department) {
        Department = department;
    }

    public String getClasses() {
        return Classes;
    }

    public void setClasses(String aClass) {
        Classes = aClass;
    }
}
