package com.gamebox.ui.demos.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.gamebox.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PiBuffonFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PiBuffonFragment extends Fragment {

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment PiBuffonFragment.
     */
    public static PiBuffonFragment newInstance() {
        return new PiBuffonFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pi_buffon, container, false);
    }

    // use custom drawing https://developer.android.com/training/custom-views/custom-drawing#java
}