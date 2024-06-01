package com.example.schoolmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.schoolmanagement.Datamanagement.Logins;
import com.google.android.material.textfield.TextInputEditText;

import java.sql.SQLException;

public class Login extends AppCompatActivity {

    // khỏi tạo biến
    private TextInputEditText ID,Passwords;
    private Button buttonLogin;
    private TextView buttonSignUp;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Init(); // hàm khởi tạo giá trị
        clickButtonLogin(); // hàm xử lí sự kiện click button Login
        clickButtonSignUp(); // hàm xử lí sự kiện click textview SignUp
    }
    // hàm xử lí sự kiện click textview SignUp
    private void clickButtonSignUp() {
        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this,SignUp.class); // code chuyển màn hình sang SignUp
                startActivity(intent);
            }
        });
    }

    // hàm xử lí sự kiện click button Login
    private void clickButtonLogin() {
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = ID.getText().toString(); // lấy dữ liệu ở ô tài khoản
                String passWords = Passwords.getText().toString(); // lấy dữ liệu ở ô mật khẩu

                Logins login = new Logins(); // tạo đối tượng login
                if(!id.isEmpty() && !passWords.isEmpty()) { // kiểm tra nhập đầy đủ thông tin chưa
                    try {
                        login = Logins.getuserlist(id, passWords); // gán login với dữ liệu lấy từ sql
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
                if(id.isEmpty() || passWords.isEmpty()){ // kiểm tra chưa có thông tin nhập vào thì thông báo
                    Toast.makeText(Login.this, "Bạn chưa nhập tài khoản mật khẩu", Toast.LENGTH_SHORT).show();
                }
                else if(id.equals(login.getID()) && passWords.equals(login.getPasswords())){ // nhập chính xác chuyển sang màn hình main
                    sharedPreferences.edit().putString("ID",id).commit();
                    Intent intent = new Intent(Login.this, MainActivity.class);
                    startActivity(intent);
                }else{ // kiểm tra ID với password là sai
                    Toast.makeText(Login.this, "Tài khoản mật khẩu của bạn chưa chính xác", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // hàm khởi tạo giá trị
    private void Init() {
        // ánh xạ View
        sharedPreferences = getSharedPreferences("loginData",MODE_PRIVATE);
        ID = findViewById(R.id.login_user);
        Passwords = findViewById(R.id.login_password);
        buttonLogin = findViewById(R.id.login_button);
        buttonSignUp = findViewById(R.id.login_signUp);
    }
}