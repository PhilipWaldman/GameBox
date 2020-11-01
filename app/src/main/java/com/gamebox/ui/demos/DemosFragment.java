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

    private static final String[] DEMO_NAMES = new String[]{
            "Fall detection algorithm",
            "Pi estimators",
            "Rolling shutter effect"
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
    public void onItemClicked(String demoName) {
        if (DEMO_NAMES[0].equals(demoName)) {
            // Fall detection algorithm
            Intent intent = new Intent(this.getContext(), FallDetectAlgoActivity.class);
            startActivity(intent);
        }
    }
}