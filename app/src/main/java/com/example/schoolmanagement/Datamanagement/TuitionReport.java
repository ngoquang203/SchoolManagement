package com.example.schoolmanagement.Datamanagement;

import com.example.schoolmanagement.SQLServerHelper.SQLServerManagement;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class TuitionReport {
    private String IdStudent;
    private String Department;
    private long Moneys;
    private int Semester;
    private String Schoolyear;

    public TuitionReport(String idStudent, String department, long moneys, int semester, String schoolyear) {
        IdStudent = idStudent;
        Department = department;
        Moneys = moneys;
        Semester = semester;
        Schoolyear = schoolyear;
    }

    // hàm in ra danh sách sinh viên đóng trọ vào kì nào năm nào
    public static ArrayList<TuitionReport> getuserlist(String semester,String schoolyear) throws SQLException { // Hàm lấy dữ liệu
        Connection connection = SQLServerManagement.connectionSQLSever(); // Kết nối với SQL server
        ArrayList<TuitionReport> list = new ArrayList<>(); // Tạo list để lưu dữ liệu
        Statement statement = connection.createStatement();// Tạo đối tượng Statement.
        String sql = "select Student.IdStudent,Department,Moneys,Semester,Schoolyear\n" +
                "from Student inner join TuitionReport\n" +
                "on Student.IdStudent = TuitionReport.IdStudent where Semester = " + semester + " and CONVERT(varchar,Schoolyear) = '" + schoolyear + "'"; // Câu lênh truy vấn SQL Server lấy ra dữ liệu trong bảng
        // Thực thi câu lệnh SQL trả về đối tượng ResultSet. // Mọi kết quả trả về sẽ được lưu trong ResultSet
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next()) {
            list.add(new TuitionReport(
                    rs.getString(1).trim(), // Lấy dữ liệu
                    rs.getString(2).trim(),
                    rs.getLong(3),
                    rs.getInt(4),
                    rs.getString(5)));// Đọc dữ liệu từ ResultSet
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

    public long getMoneys() {
        return Moneys;
    }

    public void setMoneys(long moneys) {
        Moneys = moneys;
    }

    public int getSemester() {
        return Semester;
    }

    public void setSemester(int semester) {
        Semester = semester;
    }

    public String getSchoolyear() {
        return Schoolyear;
    }

    public void setSchoolyear(String schoolyear) {
        Schoolyear = schoolyear;
    }
}
