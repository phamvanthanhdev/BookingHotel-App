package com.example.hotelbookingapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hotelbookingapplication.asynctask.AllHotelsAsyncTask;

public class AsyncTaskActivity extends AppCompatActivity {

    private ProgressBar mProgressBar;
    private TextView textNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_task);

        mProgressBar = findViewById(R.id.progress_bar);
        textNumber = findViewById(R.id.text_number);
        findViewById(R.id.button_start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //new ProgressAsyncTask(AsyncTaskActivity.this, mProgressBar).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                //new ProgressAsyncTask1().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                new AllHotelsAsyncTask(AsyncTaskActivity.this, mProgressBar, textNumber).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            }
        });
    }

    private class ProgressAsyncTask1 extends AsyncTask<Void, Integer, String> {
        @Override
        protected String doInBackground(Void... voids) {
            for (int i = 0; i <= 10; i++) {
                publishProgress(i);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return "DONE";
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            textNumber.setText(values[0]+"");
        }

        @Override
        protected void onPostExecute(String result) {
            textNumber.setText("Done!");
        }
    }


}