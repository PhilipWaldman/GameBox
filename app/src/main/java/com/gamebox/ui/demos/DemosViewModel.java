package com.gamebox.ui.demos;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DemosViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public DemosViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is demos fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}