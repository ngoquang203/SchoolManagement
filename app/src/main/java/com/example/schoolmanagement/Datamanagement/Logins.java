package com.example.schoolmanagement.Datamanagement;


import com.example.schoolmanagement.SQLServerHelper.SQLServerManagement;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Logins {
    private String ID;
    private String Passwords;

    public Logins(){
    };
    public Logins(String ID, String passwords) {
        this.ID = ID;
        Passwords = passwords;
    }


    // hàm lấy tài khoản
    public static Logins getuserlist(String ID,String passWords) throws SQLException {
        Connection connection = SQLServerManagement.connectionSQLSever();
        Logins logins = new Logins();
        Statement statement = connection.createStatement();// Tạo đối tượng Statement.
        String sql = "select * from Logins where ID = '" + ID + "' and Passwords = '" + passWords +"'";
        // Thực thi câu lệnh SQL trả về đối tượng ResultSet. // Mọi kết quả trả về sẽ được lưu trong ResultSet
        ResultSet rs = statement.executeQuery(sql);
        if(rs.next()){
            logins = new Logins(
                    rs.getString(1).trim(),
                    rs.getString(2).trim());// Đọc dữ liệu từ ResultSet)
        }
        statement.close();
        connection.close();// Đóng kết nối
        return logins;
    }

    // hàm ínsert logins
    public static void insertList(String ID, String passWords) throws SQLException{
        Connection connection = SQLServerManagement.connectionSQLSever(); // Kết nối với SQL Server
        Statement statement = connection.createStatement(); // Tạo đối tượng Statement.
        String sql = "insert into Logins(ID,Passwords) values ('" + ID + "','" + passWords + "')"; // Câu lênh SQL Server thêm hàng mới trong bảng Logins
        statement.execute(sql); // Thực thi câu lệnh
        statement.close(); // Đóng đối tượng Statement
        connection.close(); // Đóng kết nối
    }


    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getPasswords() {
        return Passwords;
    }

    public void setPasswords(String passwords) {
        Passwords = passwords;
    }
}
