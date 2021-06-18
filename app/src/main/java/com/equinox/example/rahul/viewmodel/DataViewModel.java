package com.equinox.example.rahul.viewmodel;

import android.view.View;
import android.widget.Toast;

import com.equinox.example.rahul.R;
import com.equinox.example.rahul.adapter.DataAdapter;
import com.equinox.example.rahul.model.Data;
import com.equinox.example.rahul.model.DataLists;

import java.util.ArrayList;
import java.util.List;

import androidx.databinding.ObservableInt;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


public class DataViewModel extends ViewModel {

    private DataLists dogBreeds;
    private DataAdapter adapter;
    public MutableLiveData<Data> selected;
    public ObservableInt loading;
    public ObservableInt showEmpty;
    List<Data> dataArrayListOld = new ArrayList<>();

    public void init() {
        dogBreeds = new DataLists();
        selected = new MutableLiveData<>();
        adapter = new DataAdapter(R.layout.item_list, this);
        loading = new ObservableInt(View.GONE);
        showEmpty = new ObservableInt(View.GONE);
    }

    public void fetchList() {
        dogBreeds.fetchList();
    }

    public MutableLiveData<List<Data>> getBreeds() {
        return dogBreeds.getBreeds();
    }

    public DataAdapter getAdapter() {
        return adapter;
    }

    public void setDataInAdapter(List<Data> breeds) {
        this.adapter.setDogBreeds(breeds);
        this.adapter.notifyDataSetChanged();
        dataArrayListOld = breeds;
    }

    public MutableLiveData<Data> getSelected() {
        return selected;
    }

    public void onItemClick(Integer index) {
        Data db = getDogBreedAt(index);
        selected.setValue(db);
    }

    public Data getDogBreedAt(Integer index) {
        if (dogBreeds.getBreeds().getValue() != null &&
                index != null &&
                dogBreeds.getBreeds().getValue().size() > index) {
            return dogBreeds.getBreeds().getValue().get(index);
        }
        return null;
    }

    public void filterList(String text) {

        ArrayList<Data> dataArrayListFilter = new ArrayList<>();

        for (int i = 0; i < dataArrayListOld.size(); i++) {

            if (dataArrayListOld.get(i).getName().toLowerCase().contains(text.toLowerCase().trim())) {
                //adding the element to filtered list
                dataArrayListFilter.add(dataArrayListOld.get(i));
            }
        }

        adapter.filterList(dataArrayListFilter);

    }


    public  void  offlineList(ArrayList<Data> list)
    {
        adapter.filterList(list);
    }



}
