package com.gamebox.ui.tools.encryption;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import com.gamebox.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

public class SubstitutionCipherActivity extends AppCompatActivity {

    private TextView outputTV, keyErrorTV;
    private Button copyKeyAndOutput; // https://developer.android.com/guide/topics/text/copy-paste
    private ImageButton copyOutput;
    private EditText keyInput;
    private boolean encrypt = true, isValidKey = true;
    private String key = "", message = "", outputMessage = "";
    private HashMap<Character, Character> dictionary;

    /**
     * Substitutes every letter in the message by the letter specified in the dictionary.
     *
     * @param message    The message to substitute the letters in.
     * @param dictionary A HashMap of which letter should be converted into which letter.
     * @return The message with the letter substituted.
     */
    @NotNull
    public static String substituteLetters(@NotNull String message, HashMap<Character, Character> dictionary) {
        StringBuilder encrypted = new StringBuilder();
        for (int i = 0; i < message.length(); i++) {
            char letter = message.charAt(i);
            if (dictionary != null && dictionary.containsKey(letter)) {
                letter = dictionary.get(letter);
            }
            encrypted.append(letter);
        }
        return encrypted.toString();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_substitution_cipher);

        // Find views
        outputTV = findViewById(R.id.encrypted_message_text_view);
        keyErrorTV = findViewById(R.id.key_error_message);
        SwitchCompat encryptSwitch = findViewById(R.id.encrypt_switch);
        Button clearAll = findViewById(R.id.clear_all_button);
        copyOutput = findViewById(R.id.copy_output_button);
        copyKeyAndOutput = findViewById(R.id.copy_key_output_button);
        keyInput = findViewById(R.id.key_input);
        EditText messageInput = findViewById(R.id.message_input);
        Button randomKey = findViewById(R.id.random_key);

        // Set change listener for switch
        encryptSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            encrypt = isChecked;
            buttonView.setText(isChecked ? R.string.encrypt : R.string.decrypt);
            updateViews();
        });

        // Add change listener to keyInput
        keyInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                key = s.toString();
                generateDictionaryFromKey();
                updateViews();
            }
        });

        // Add change listener to messageInput
        messageInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                message = s.toString();
                updateViews();
            }
        });

        // Clear all views
        clearAll.setOnClickListener(v -> {
            keyInput.setText("");
            messageInput.setText("");
        });

        // Generate random key button
        randomKey.setOnClickListener(v -> generateRandomKey());

        // Copy only the output
    }

    private void updateViews() {
        if (encrypt) {
            encrypt();
        } else {
            decrypt();
        }
        outputTV.setText(outputMessage);
    }

    /**
     * Encrypt the message.
     */
    private void encrypt() {
        if (isValidKey) {
            outputMessage = substituteLetters(message, dictionary);
        }
    }

    /**
     * Decrypt the message.
     */
    private void decrypt() {
        if (isValidKey) {
            HashMap<Character, Character> reverseDict = new HashMap<>();
            for (char k : dictionary.keySet()) {
                reverseDict.put(dictionary.get(k), k);
            }
            outputMessage = substituteLetters(message, reverseDict);
        }
    }

    /**
     * Generates a dictionary {@code Map} from the {@code String} key.
     */
    private void generateDictionaryFromKey() {
        isValidKey = isValidKey();
        if (isValidKey) {
            dictionary = new HashMap<>();
            for (int i = 0; i < key.length() - 1; i += 2) {
                char from = key.charAt(i);
                char to = key.charAt(i + 1);
                if (Character.isLetter(from) && Character.isLetter(to)) {
                    dictionary.put(Character.toLowerCase(from), Character.toLowerCase(to));
                    dictionary.put(Character.toUpperCase(from), Character.toUpperCase(to));
                } else if (Character.isLetter(from) && !Character.isLetter(to)) {
                    dictionary.put(Character.toLowerCase(from), to);
                    dictionary.put(Character.toUpperCase(from), to);
                } else if (!Character.isLetter(from) && Character.isLetter(to)) {
                    dictionary.put(from, Character.toLowerCase(to));
                } else {
                    dictionary.put(from, to);
                }
            }
        }
    }

    /**
     * Checks whether the key is valid. If the key is not valid, the reason will be shown.
     *
     * @return Whether the key is valid.
     */
    private boolean isValidKey() {
        if (key.length() % 2 != 0) {
            String error = "Key length should be even";
            keyErrorTV.setText(error);
            return false;
        }
        HashSet<Character> keys = new HashSet<>();
        HashSet<Character> vals = new HashSet<>();
        for (int i = 0; i < key.length(); i += 2) {
            if (!keys.add(key.charAt(i))) {
                String error = "The even places cannot contain duplicate values";
                keyErrorTV.setText(error);
                return false;
            }
            if (i + 1 < key.length() && !vals.add(key.charAt(i + 1))) {
                String error = "The odd places cannot contain duplicate values";
                keyErrorTV.setText(error);
                return false;
            }
        }
        keyErrorTV.setText("");
        return true;
    }

    /**
     * Generates and assigns a random key. The key is 2 permutations of the alphabet (a-z) combined,
     * alternating the letters from which permutation they come from.
     * This way it is guaranteed to be a valid key.
     */
    private void generateRandomKey() {
        StringBuilder newKey = new StringBuilder();
        char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        ArrayList<Character> keys = new ArrayList<>();
        ArrayList<Character> vals = new ArrayList<>();
        for (char let : alphabet) {
            keys.add(let);
            vals.add(let);
        }
        Collections.shuffle(keys);
        Collections.shuffle(vals);
        for (int i = 0; i < keys.size(); i++) {
            newKey.append(keys.get(i));
            newKey.append(vals.get(i));
        }
        System.out.println(newKey.toString());
        keyInput.setText(newKey.toString());
    }
}