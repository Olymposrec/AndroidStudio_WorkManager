package com.melihakkose.javaworkmanager;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class LiveDataManager extends Worker {
    //BURAYA ERISEBILMEK ICIN CONTEXT OLUSTURUP CONSTRUCTOR' DA TANIMLIYORUZ
    Context myContext;

    public LiveDataManager(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);

        this.myContext=context;

    }

    @NonNull
    @Override
    public Result doWork() {
        //KULLANICIDAN ALINAN VERIYI ALMAK ICIN OLUSTURULAN Data Sınıfı
        Data data=getInputData();
        int myNumber=data.getInt("intKey",0);

        //FONSIYONU CAGIRIYORUZ
        refreshData(myNumber);

        //WORKER' IN RESULT' UNU DONDURUYORUZ
        return Result.success();
    }

    private void refreshData(int myNumber){

        //WORKER SINIFI ILE CALISIRKEN KULLANACAGIMIZ DATA ICIN FONKSIYON YAZIYORUZ
        SharedPreferences sharedPreferences= myContext.getSharedPreferences("com.melihakkose.javaworkmanager",Context.MODE_PRIVATE);
        int myData= sharedPreferences.getInt("myData",0);
        myData=myData+myNumber;
        System.out.println(myData);
        sharedPreferences.edit().putInt("myData",myData).apply();
    }

}
