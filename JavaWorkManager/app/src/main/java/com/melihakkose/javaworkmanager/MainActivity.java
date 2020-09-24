package com.melihakkose.javaworkmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import android.os.Bundle;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Data data=new Data.Builder().putInt("intKey",1).build();

        Constraints constraints= new Constraints.Builder()
                //.setRequiredNetworkType(NetworkType.CONNECTED)
                .setRequiresCharging(false)
                .build();

            /*
        //WORKMANAGER KULLANABILMEK ICIN WorkRequest OLUSTURUYORUZ. BIR DEFA CALISACAK WorkRequest
        WorkRequest workRequest=new OneTimeWorkRequest.Builder(LiveDataManager.class)
                .setConstraints(constraints)
                .setInputData(data)
                //.setInitialDelay(5, TimeUnit.MINUTES)
                //.addTag("myTag")
                .build();

        //BASLATMA KODU
        WorkManager.getInstance(this).enqueue(workRequest);
          */

            //PERIYODIK CALISAN WorkRequests
        WorkRequest workRequest=new PeriodicWorkRequest.Builder(LiveDataManager.class,15,TimeUnit.MINUTES)
                    .setConstraints(constraints)
                    .setInputData(data)
                    .build();
        WorkManager.getInstance(this).enqueue(workRequest);

        //YAPILAN ISIN NE OLDUGUNU, NE DURUMDA OLDUGUNU GORMEK ICIN GEREKLI ISLEMLER (IZLEME_GOZLEMLEME)
        WorkManager.getInstance(this).getWorkInfoByIdLiveData(workRequest.getId()).observe(this, new Observer<WorkInfo>() {
            @Override
            public void onChanged(WorkInfo workInfo) {
                if(workInfo.getState()== WorkInfo.State.RUNNING){
                    System.out.println("running");
                }else if(workInfo.getState() == WorkInfo.State.SUCCEEDED){
                    System.out.println("succeded");
                }else if(workInfo.getState() == WorkInfo.State.FAILED){
                    System.out.println("failed");
                }
            }
        });

        //TUM ISLERI IPTAL EDER
        //WorkManager.getInstance(this).cancelAllWork();

        //Chaining (ZINZIRLEME ISLER)
        /*
        OneTimeWorkRequest oneTimeWorkRequest= new OneTimeWorkRequest.Builder(LiveDataManager.class)
                .setInputData(data)
                .setConstraints(constraints)
                .build();

        WorkManager.getInstance(this).beginWith(oneTimeWorkRequest)
                .then(oneTimeWorkRequest)
                .then(oneTimeWorkRequest)
                .enqueue();
        */




    }
}