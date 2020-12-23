package com.gamebox.ui.demos;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gamebox.MainActivity.OnItemClickListener;
import com.gamebox.R;

public class DemosFragment extends Fragment implements OnItemClickListener {

    private static final int[] DEMO_NAMES = new int[]{
            R.string.fall_detection_demo,
            R.string.pi_estimators_demo
    };

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_demos, container, false);

        // Recycler View
        RecyclerView recyclerView = root.findViewById(R.id.demos_recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(layoutManager);
        DemosAdapter demosAdapter = new DemosAdapter(this.getContext(), DEMO_NAMES, this);
        recyclerView.setAdapter(demosAdapter);

        return root;
    }

    @Override
    public void onItemClicked(int demoName) {
        Class<?> c = null;
        if (R.string.fall_detection_demo == demoName) { // Fall detection algorithm
            c = FallDetectAlgoActivity.class;
        } else if (R.string.pi_estimators_demo == demoName) { // Pi estimators
            c = PiEstimationActivity.class;
            Toast.makeText(this.getContext(), "This demo is currently under development...", Toast.LENGTH_LONG).show();
            return;
        }
        Intent intent = new Intent(this.getContext(), c);
        startActivity(intent);
    }
}