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
import com.gamebox.util.SimpleRecyclerViewAdapter;

public class DemosFragment extends Fragment implements OnItemClickListener {

    /**
     * Array of reference id's of the names of all the demos to show in the list.
     */
    private static final int[] DEMO_NAMES = new int[]{
            R.string.fall_detection_demo
//            R.string.pi_estimators_demo
    };

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_demos, container, false);

        // Create Recycler View
        RecyclerView recyclerView = root.findViewById(R.id.demos_recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(layoutManager);
        SimpleRecyclerViewAdapter demosAdapter = new SimpleRecyclerViewAdapter(this.getContext(),
                DEMO_NAMES, this,
                R.layout.recycler_view_item_demos, R.id.demos_recycler_item_name);
        recyclerView.setAdapter(demosAdapter);

        return root;
    }

    /**
     * Called when one of the items in the recycler view is clicked. This starts a new activity depending on which item was clicked.
     *
     * @param demoName The reference id of the name of the demo.
     */
    @Override
    public void onItemClicked(int demoName) {
        Class<?> c = null;
        if (R.string.fall_detection_demo == demoName) { // Fall detection algorithm
            c = FallDetectAlgoActivity.class;
        } else if (R.string.pi_estimators_demo == demoName) { // Pi estimators
            c = PiEstimationActivity.class;
            // Not working properly, so it shows a "under development" toast, but doesn't start the activity.
            Toast.makeText(this.getContext(), "This demo is currently under development...", Toast.LENGTH_LONG).show();
            return;
        }
        if (c != null) {
            Intent intent = new Intent(this.getContext(), c);
            startActivity(intent);
        }
    }
}