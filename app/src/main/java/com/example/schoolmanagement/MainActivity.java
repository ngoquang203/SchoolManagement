package com.example.schoolmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    // khơi tạo biến
    private Button watchTuitionReport,logOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Init(); // hàm khởi tạo giá trị
        clickButton(); // hàm xử lí sự kiện click
    }

    private void clickButton() {
        watchTuitionReport.setOnClickListener(new View.OnClickListener() { // chuyển màn hình sang xem báo cáo học phí
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TuitionReports.class); // tạo intent chuyển màn hình
                startActivity(intent);
            }
        });
        logOut.setOnClickListener(new View.OnClickListener() { // chuyển màn hình về đăng nhập
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Login.class); // tạo intent chuyển màn hình
                startActivity(intent);
            }
        });
    }

    private void Init() {
        // ánh xạ View
        watchTuitionReport = findViewById(R.id.main_watchTuitionReport);
        logOut = findViewById(R.id.main_logOut);
    }

}