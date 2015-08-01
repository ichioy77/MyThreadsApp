package pe.area51.mythreadsapp;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class MainActivity extends ActionBarActivity {

    private AlertDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle(getString(R.string.progress_message));
        progressDialog.setCancelable(false);


        findViewById(R.id.activity_main_button_main_thread)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        executeOnMainThread();
                    }
                });
        findViewById(R.id.activity_main_button_main_async_task)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        executeAsynTask();
                    }
                });
        findViewById(R.id.activity_main_button_worker_thread)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        executeOnWorkerThread();
                    }
                });

    }



    private void longTask(){


        try {
            Thread.sleep(5000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    private void executeOnMainThread(){
        progressDialog.show();
        longTask();
        progressDialog.dismiss();
    }

    private void executeOnWorkerThread(){
        progressDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                longTask();
                progressDialog.dismiss();
            }
        }).start();
    }

    private void executeAsynTask(){
        new AsyncTask<Void,Void, Void >(){

            @Override
            protected void onPostExecute(Void aVoid) {
                progressDialog.dismiss();
            }

            @Override
            protected Void doInBackground(Void... params) {
                longTask();
                return null;
            }

            @Override
            protected void onPreExecute() {
                progressDialog.show();

            }
        }.execute();
    }
}
