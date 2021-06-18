package com.equinox.example.rahul;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.equinox.example.rahul.database.Note;
import com.equinox.example.rahul.database.NoteDatabase;
import com.equinox.example.rahul.databinding.ActivityMainBinding;
import com.equinox.example.rahul.model.Data;
import com.equinox.example.rahul.viewmodel.DataViewModel;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;


public class MainActivity extends AppCompatActivity {

    public DataViewModel viewModel;
    private ActivityMainBinding activityBinding;
    private NoteDatabase noteDatabase;
    private Note note;
    private List<Note> notes;
    private ArrayList<Data> dataList1 = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupBindings(savedInstanceState);
    }

    private void setupBindings(Bundle savedInstanceState) {
        activityBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        viewModel = ViewModelProviders.of(this).get(DataViewModel.class);
        noteDatabase = NoteDatabase.getInstance(MainActivity.this);
        if (savedInstanceState == null) {
            viewModel.init();
        }
        activityBinding.setModel(viewModel);

        if (Methods.isOnline(MainActivity.this)) {
            setupListUpdate();
        } else {
            new RetrieveTask(this).execute();
        }


        activityBinding.etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                viewModel.filterList(editable.toString());
            }
        });

    }

    private class RetrieveTask extends AsyncTask<Void, Void, List<Note>> {

        private WeakReference<MainActivity> activityReference;

        // only retain a weak reference to the activity
        RetrieveTask(MainActivity context) {
            activityReference = new WeakReference<>(context);
        }

        @Override
        protected List<Note> doInBackground(Void... voids) {
            if (activityReference.get() != null)
                return activityReference.get().noteDatabase.getNoteDao().getAll();
            else
                return null;
        }

        @Override
        protected void onPostExecute(List<Note> notes) {
            if (notes != null && notes.size() > 0) {
                activityReference.get().notes = notes;

                for (int i = 0; i < notes.size(); i++) {
                    dataList1.add(new Data(notes.get(i).getContent(), notes.get(i).getTitle()));
                }
                viewModel.showEmpty.set(View.GONE);
                viewModel.offlineList(dataList1);
            }
        }

    }


    private void setupListUpdate() {
        viewModel.loading.set(View.VISIBLE);
        viewModel.fetchList();
        viewModel.getBreeds().observe(this, new Observer<List<Data>>() {
            @Override
            public void onChanged(List<Data> dataList) {
                viewModel.loading.set(View.GONE);
                if (dataList.size() == 0) {
                    viewModel.showEmpty.set(View.VISIBLE);
                } else {


                    viewModel.showEmpty.set(View.GONE);
                    viewModel.setDataInAdapter(dataList);
                    noteDatabase.getNoteDao().deleteTable();

                    for (int i = 0; i < dataList.size(); i++) {
                        note = new Note(dataList.get(i).getName(),
                                dataList.get(i).getDept_name());

                        new InsertTask(MainActivity.this, note).execute();
                    }


                }
            }
        });

    }

    private void setResult(Note note, int flag) {

    }

    private static class InsertTask extends AsyncTask<Void, Void, Boolean> {

        private WeakReference<MainActivity> activityReference;
        private Note note;

        // only retain a weak reference to the activity
        InsertTask(MainActivity context, Note note) {
            activityReference = new WeakReference<>(context);
            this.note = note;
        }

        // doInBackground methods runs on a worker thread
        @Override
        protected Boolean doInBackground(Void... objs) {
            activityReference.get().noteDatabase.getNoteDao().insert(note);
            return true;
        }

        // onPostExecute runs on main thread
        @Override
        protected void onPostExecute(Boolean bool) {
            if (bool) {
                activityReference.get().setResult(note, 1);
            }
        }

    }
}
