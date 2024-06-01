package com.example.schoolmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.schoolmanagement.Datamanagement.Logins;
import com.google.android.material.textfield.TextInputEditText;

import java.sql.SQLException;

public class SignUp extends AppCompatActivity {

    // khởi tạo biến
    private TextInputEditText ID,PassWords,ComfirmPassword;
    private Button buttonInsertUser;
    private ImageButton buttonBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Init(); // hàm khởi tạo giá trị
        clickButtonBack(); // hàm quay trở lại trang chính
        clickButtonInsertUser(); // hàm tạo người dùng mới
    }

    // hàm chuyển màn hình đăng nhập
    private void changeToPageLogin(){
        Intent intent = new Intent(SignUp.this,Login.class); // code chuyển màn hình
        startActivity(intent);
    }

    // hàm tạo người dùng mới
    private void clickButtonInsertUser() {
        buttonInsertUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = ID.getText().toString();
                String passWords = PassWords.getText().toString();
                String confirmPassword = ComfirmPassword.getText().toString();
                Logins logins = new Logins(); // tạo đối tượng login
                if (!id.isEmpty() && !passWords.isEmpty() && !confirmPassword.isEmpty()){ // kiểm tra nhập đầy đủ thông tin chưa
                    try {
                        logins = Logins.getuserlist(id,passWords); // gán login với dữ liệu lấy từ sql
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
                if(id.isEmpty() || passWords.isEmpty() || confirmPassword.isEmpty()){ // kiểm tra chưa có thông tin nhập vào thì thông báo
                    Toast.makeText(SignUp.this, "Bạn chưa nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();


                }else if(id.equals(logins.getID())){
                    Toast.makeText(SignUp.this, "Đã có tài khoản đăng kí ID này", Toast.LENGTH_SHORT).show();
                }else if(passWords.equals(confirmPassword)){ // kiểm tra mật khẩu và xác nhận mật khẩu giống nhau
                    try {
                        Logins.insertList(id,passWords); // Lưu tài khoản mới vào SQL
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    Toast.makeText(SignUp.this, "Tạo tài khoản thành công", Toast.LENGTH_SHORT).show(); // thông báo tạo tài khoản thành công
                    changeToPageLogin(); // hàm chuyển màn hình đăng nhập
                }else if(!passWords.equals(confirmPassword)){ // kiểm tra mật khẩu và xác nhận mật khẩu k giống nhau
                    Toast.makeText(SignUp.this, "Mật khẩu và mật khẩu xác nhận chưa đồng nhất", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    // hàm quay trở lại trang chính
    private void clickButtonBack() {
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeToPageLogin(); // hàm chuyển màn hình đăng nhập
            }
        });
    }

    // hàm khởi tạo giá trị
    private void Init() {
        // ánh xạ View
        ID = findViewById(R.id.signUp_user);
        PassWords = findViewById(R.id.signUp_password);
        ComfirmPassword = findViewById(R.id.signUp_ComfirmPassword);
        buttonInsertUser = findViewById(R.id.signUp_button);
        buttonBack = findViewById(R.id.signUp_back);
    }
}