package com.example.progressasynctask;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnShow;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getViews();
    }
    private void getViews(){
        btnShow=findViewById(R.id.btnShow);
        progressBar=findViewById(R.id.progressBar);
        btnShow.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnShow:
                new ProgressAsyncTask().execute();
                break;
            default: break;
        }
    }

    private class ProgressAsyncTask extends AsyncTask<Void, Integer, String>{
        @Override
        protected String doInBackground(Void... voids) {
            for(int i=0; i<=100;i++){
                publishProgress(i);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return "DONE!";
        }

        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(MainActivity.this,s,Toast.LENGTH_LONG).show();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            progressBar.setProgress(values[0]);
        }
    }
}