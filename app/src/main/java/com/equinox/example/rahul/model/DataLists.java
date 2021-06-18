package com.equinox.example.rahul.model;

import android.util.Log;

import com.equinox.example.rahul.Methods;
import com.equinox.example.rahul.net.Api;

import java.util.ArrayList;
import java.util.List;

import androidx.databinding.BaseObservable;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataLists extends BaseObservable {
    private String status;
    private List<Data> dataListList = new ArrayList<>();
    private MutableLiveData<List<Data>> dataLists = new MutableLiveData<>();

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void addData(Data bd) {
        dataListList.add(bd);
    }

    public MutableLiveData<List<Data>> getBreeds() {
        return dataLists;
    }

    public void fetchList() {
        Callback<DataLists> callback = new Callback<DataLists>() {
            @Override
            public void onResponse(Call<DataLists> call, Response<DataLists> response) {
                DataLists body = response.body();
                status = body.status;
                dataLists.setValue(body.dataListList);
            }

            @Override
            public void onFailure(Call<DataLists> call, Throwable t) {
                Log.e("Test", t.getMessage(), t);
            }
        };

           Api.getApi().getBreeds().enqueue(callback);
    }
}
