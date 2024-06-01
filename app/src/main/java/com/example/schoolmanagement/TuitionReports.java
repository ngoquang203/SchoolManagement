package com.example.schoolmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.schoolmanagement.Datamanagement.Student;
import com.example.schoolmanagement.Datamanagement.TuitionReport;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class TuitionReports extends AppCompatActivity {
    // tạo biến
    private TextView sumStudent,submitted,noSubmitted,cntt,tcnh,qtkd;
    private ImageButton buttonBack;
    private Spinner spinner;

    private ArrayList<Student> studentArrayList;
    private ArrayList<TuitionReport> reportsArrayList;
    private ArrayList<String> stringArrayList;
    private ArrayAdapter<String> adapter;
    private double sumCNTT,sumTCNH,sumQTKD;
    private double sumCNTTSubmit,sumTCNHSubmit,sumQTKDSubmit;
    private DecimalFormat df = new DecimalFormat("#.##");;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tuition_reports);
        Init(); // hàm khởi tạo giá trị
        clickBackPage(); // hàm quay về trang chủ
        clickSpiner(); // hàm thay đổi báo cáo khi chọn spinner
    }

    private void clickSpiner() {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    reportsArrayList = TuitionReport.getuserlist(
                            getSemesterInSpinner(stringArrayList.get(position)),
                            getSchoolYearInSpinner(stringArrayList.get(position)));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                submitted.setText(String.valueOf(reportsArrayList.size()));
                noSubmitted.setText(String.valueOf(studentArrayList.size() - reportsArrayList.size()));
                setDataStudentSubmitNumber();
                setTextPerCentDepartment();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    // hàm quay về trang chủ
    private void clickBackPage() {
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TuitionReports.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }


    // hàm lấy ra kì học
    private String getSemesterInSpinner(String str){
        String semester = String.valueOf(str.charAt(3));
        return semester;
    }
    // hàm lấy ra năm học
    private String getSchoolYearInSpinner(String str){
        String SchoolYear = str.substring(str.length() - 4);
        return SchoolYear;
    }

    // hàm tính số lượng sinh viên các khoa
    private void setDataStudentNumber(){
        sumCNTT = 0;
        sumQTKD = 0;
        sumTCNH = 0;
        for (int i = 0;i<studentArrayList.size();++i){
            if(studentArrayList.get(i).getDepartment().equals("CNTT")) ++sumCNTT;
            else if(studentArrayList.get(i).getDepartment().equals("TCNH")) ++sumTCNH;
            if(studentArrayList.get(i).getDepartment().equals("QTKH")) ++sumQTKD;
        }
    }

    // hàm tính số lượng sinh viên đã đóng học phí
    private void setDataStudentSubmitNumber(){
        sumCNTTSubmit = 0;
        sumTCNHSubmit = 0;
        sumQTKDSubmit = 0;
        for(int i = 0;i<reportsArrayList.size();++i){
            if(reportsArrayList.get(i).getDepartment().equals("CNTT")) ++sumCNTTSubmit;
            else if(reportsArrayList.get(i).getDepartment().equals("TCNH")) ++sumTCNHSubmit;
            else if(reportsArrayList.get(i).getDepartment().equals("QTKH")) ++sumQTKDSubmit;

        }
    }
    // hiển thị phân chăm tỉ lệ sinh viên của các khoa đã đóng
    private void setTextPerCentDepartment(){
        cntt.setText(df.format(((sumCNTTSubmit/sumCNTT)*100)) + "%" );
        tcnh.setText(df.format(((sumTCNHSubmit/sumTCNH)*100)) + "%" );
        qtkd.setText(df.format(((sumQTKDSubmit/sumQTKD)*100)) + "%" );
    }
    private void Init() {
        // ánh xạ View
        spinner = findViewById(R.id.tuitionReport_spinner);
        sumStudent = findViewById(R.id.tuitionReport_sumStudent);
        submitted = findViewById(R.id.tuitionReport_submitted);
        noSubmitted = findViewById(R.id.tuitionReport_noSubmitted);
        cntt = findViewById(R.id.tuitionReport_cntt);
        tcnh = findViewById(R.id.tuitionReport_tcnh);
        qtkd = findViewById(R.id.tuitionReport_qtkd);
        buttonBack = findViewById(R.id.tuitionReport_back);

        stringArrayList = new ArrayList<>();
        stringArrayList.add("Kì 1, năm học 2024");
        stringArrayList.add("Kì 2, năm học 2024");
        stringArrayList.add("Kì 1, năm học 2025");
        stringArrayList.add("Kì 2, năm học 2025");
        stringArrayList.add("Kì 1, năm học 2026");
        stringArrayList.add("Kì 2, năm học 2026");
        stringArrayList.add("Kì 1, năm học 2027");
        stringArrayList.add("Kì 2, năm học 2027");

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,stringArrayList);
        spinner.setAdapter(adapter);
        try {
            studentArrayList = Student.getuserlist();
            reportsArrayList = TuitionReport.getuserlist(
                    getSemesterInSpinner(stringArrayList.get(0)),
                    getSchoolYearInSpinner(stringArrayList.get(0)));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        sumStudent.setText(String.valueOf(studentArrayList.size()));
        submitted.setText(String.valueOf(reportsArrayList.size()));
        noSubmitted.setText(String.valueOf(studentArrayList.size() - reportsArrayList.size()));

        setDataStudentNumber(); // hàm tính tổng sinh sinh đang có của các khoa
        setDataStudentSubmitNumber(); // hàm tính tổng sinh viên đã đóng học phí của các khoa
        setTextPerCentDepartment(); // hiển thị phân chăm tỉ lệ sinh viên của các khoa đã đóng
    }
}