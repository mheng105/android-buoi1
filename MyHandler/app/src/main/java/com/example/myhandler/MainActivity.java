package com.example.myhandler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnStart;
    private TextView txtNumber;
    private static final int NUM_UP=100;
    private static final int NUM_DONE=101;
    private Handler handler;
    private boolean isUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getViews();
        processHandler();
    }
    private void getViews(){
        btnStart=findViewById(R.id.btnStart);
        txtNumber=findViewById(R.id.txtNumber);
        btnStart.setOnClickListener(this);
    }
    private void processHandler(){
        handler=new Handler(getMainLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case NUM_UP:
                        //xu ly viec cap nhat gia tri len UI
                        isUpdate=true;
                        //cap nhat UI vs gia tri moi
                        txtNumber.setText(String.valueOf(msg.arg1));
                        break;
                    case NUM_DONE:
                        //cap nhat lai giao dien hien thi de ket thuc viec cap nhat
                        txtNumber.setText("DONE!");
                        isUpdate=false;
                        break;
                    default: break;
                }
            }
        };
    }

    @Override
    public void onClick(View view) {
        //thuc thi cong viec khi click vao btnStart
        switch (view.getId()){
            case R.id.btnStart:
                if(!isUpdate){
                    updateNumber();
                }
                break;
            default:
                break;
        }
    }
    private void updateNumber(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                //cap nhat gia tri o luong nay
                for(int i=0; i<=10; i++){
                    //khoi tao doi tuong message chua nd tin nhan can dua vao message pool
                    Message msg=new Message();
                    //gan cong viec cho doi tuong msg
                    msg.what=NUM_UP;
                    msg.arg1=i;
                    //gui tin nhan vao msg pool
                    handler.sendMessage(msg);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                //gui tin nhan hoan thanh thuc hien cong viec
                handler.sendEmptyMessage(NUM_DONE);
            }
        }).start();
    }
}