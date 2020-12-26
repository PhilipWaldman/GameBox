package com.gamebox.ui.tools;

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

public class ToolsFragment extends Fragment implements OnItemClickListener {

    /**
     * Array of reference id's of the names of all the tools to show in the list.
     */
    private static final int[] TOOL_NAMES = new int[]{
            R.string.morse_code_tool
    };

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_tools, container, false);

        // Create Recycler View
        RecyclerView recyclerView = root.findViewById(R.id.tools_recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(layoutManager);
        ToolsAdapter toolsAdapter = new ToolsAdapter(this.getContext(), TOOL_NAMES, this);
        recyclerView.setAdapter(toolsAdapter);

        return root;
    }

    /**
     * Called when one of the items in the recycler view is clicked. This starts a new activity depending on which item was clicked.
     *
     * @param toolName The reference id of the name of the tool.
     */
    @Override
    public void onItemClicked(int toolName) {
        Class<?> c = null;
        if (R.string.morse_code_tool == toolName) { // Morse Code
            c = MorseCodeActivity.class;
            // Not working properly, so it shows a "under development" toast, but doesn't start the activity.
            Toast.makeText(this.getContext(), "This tool is currently under development...", Toast.LENGTH_LONG).show();
            return;
        }
        Intent intent = new Intent(this.getContext(), c);
        startActivity(intent);
    }
}