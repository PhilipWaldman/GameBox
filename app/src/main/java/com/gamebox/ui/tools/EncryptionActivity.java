package com.gamebox.ui.tools;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gamebox.MainActivity.OnItemClickListener;
import com.gamebox.R;
import com.gamebox.util.SimpleRecyclerViewAdapter;


public class EncryptionActivity extends AppCompatActivity implements OnItemClickListener {

    /**
     * Array of reference id's of the names of all the encryptions to show in the list.
     */
    private static final int[] ENCRYPTION_NAMES = new int[]{
            R.string.caesar_cipher_encryption,
            R.string.substitution_cipher_encryption,
            R.string.transposition_cipher_encryption,
            R.string.enigma_encryption
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encryption);

        // Create Recycler View
        RecyclerView recyclerView = findViewById(R.id.encryptions_recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        SimpleRecyclerViewAdapter encryptionsAdapter = new SimpleRecyclerViewAdapter(this,
                ENCRYPTION_NAMES, this,
                R.layout.recycler_view_item_encryptions, R.id.encryptions_recycler_item_name);
        recyclerView.setAdapter(encryptionsAdapter);
    }


    /**
     * Called when one of the items in the recycler view is clicked. This starts a new activity depending on which item was clicked.
     *
     * @param encryptionName The reference id of the name of the encryption.
     */
    @Override
    public void onItemClicked(int encryptionName) {
        Class<?> c = null;
        if (R.string.caesar_cipher_encryption == encryptionName) { // Caesar cipher
            c = null;
            // Not working properly, so it shows a "under development" toast, but doesn't start the activity.
            Toast.makeText(this, "This encryption is currently under development...", Toast.LENGTH_LONG).show();
            return;
        } else if (R.string.substitution_cipher_encryption == encryptionName) { // Substitution cipher
            c = null;
            // Not working properly, so it shows a "under development" toast, but doesn't start the activity.
            Toast.makeText(this, "This encryption is currently under development...", Toast.LENGTH_LONG).show();
            return;
        } else if (R.string.transposition_cipher_encryption == encryptionName) { // Transposition cipher
            c = null;
            // Not working properly, so it shows a "under development" toast, but doesn't start the activity.
            Toast.makeText(this, "This encryption is currently under development...", Toast.LENGTH_LONG).show();
            return;
        } else if (R.string.enigma_encryption == encryptionName) { // Enigma
            c = null;
            // Not working properly, so it shows a "under development" toast, but doesn't start the activity.
            Toast.makeText(this, "This encryption is currently under development...", Toast.LENGTH_LONG).show();
            return;
        }
        if (c != null) {
            Intent intent = new Intent(this, c);
            startActivity(intent);
        }
    }
}