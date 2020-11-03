package com.gamebox.ui.tools;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gamebox.MainActivity.OnItemClickListener;
import com.gamebox.R;

public class ToolsFragment extends Fragment implements OnItemClickListener {

    // TODO: change the names and order
    private static final int[] TOOL_NAMES = new int[]{
//            "Sketch pad",
//            "Flash light morse code",
//            "Enigma machine + other encodings",
//            "Animation maker (images to video)",
//            "Calculator (RPN and traditional)"
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
    public void onItemClicked(int toolName) {

    }
}