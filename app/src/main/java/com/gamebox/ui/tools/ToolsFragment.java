package com.gamebox.ui.tools;

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

    private static final String[] TOOL_NAMES = new String[]{
            "Tool 1",
            "Tool 2",
            "Tool 3"
    };

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_tools, container, false);

        // Recycler View
        RecyclerView recyclerView = root.findViewById(R.id.tools_recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(layoutManager);
        ToolsAdapter toolsAdapter = new ToolsAdapter(this.getContext(), TOOL_NAMES, this);
        recyclerView.setAdapter(toolsAdapter);

        return root;
    }

    @Override
    public void onItemClicked(String toolName) {
        if (TOOL_NAMES[0].equals(toolName)) {
            Toast.makeText(this.getContext(), toolName, Toast.LENGTH_LONG).show();
            // Open tool 1
        } else if (TOOL_NAMES[1].equals(toolName)) {
            Toast.makeText(this.getContext(), toolName, Toast.LENGTH_LONG).show();
            // Open tool 2
        } else if (TOOL_NAMES[2].equals(toolName)) {
            Toast.makeText(this.getContext(), toolName, Toast.LENGTH_LONG).show();
            // Open tool 3
        }
    }
}