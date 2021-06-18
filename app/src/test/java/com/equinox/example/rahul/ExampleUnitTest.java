package com.equinox.example.rahul;

import com.equinox.example.rahul.model.Data;
import com.equinox.example.rahul.viewmodel.DataViewModel;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Observer;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Rule
    public InstantTaskExecutorRule rule = new InstantTaskExecutorRule();

    @Mock
    private Observer<Data> observer;

    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void test_clickReturnCorrectObject() {
        List<Data> dogBreeds = generateDogBreeds();
        DataViewModel viewModel = new DataViewModel();
        viewModel.init();

        viewModel.getSelected().observeForever(observer);
        // When
        viewModel.getBreeds().setValue(dogBreeds);
        viewModel.onItemClick(0);
        // then
        verify(observer).onChanged(dogBreeds.get(0));
    }

    private List<Data> generateDogBreeds() {
        List<Data> dogBreeds = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Data db = new Data();
            db.setName("Dog Breed " + i);
            dogBreeds.add(db);
        }
        return dogBreeds;
    }
}