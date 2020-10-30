package com.gamebox.ui.demos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.gamebox.R;

public class DemosFragment extends Fragment {

    private DemosViewModel demosViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        demosViewModel = new ViewModelProvider(this).get(DemosViewModel.class);
        View root = inflater.inflate(R.layout.fragment_demos, container, false);
        final TextView textView = root.findViewById(R.id.text_demos);
        demosViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }
}