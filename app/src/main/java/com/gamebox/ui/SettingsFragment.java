package com.gamebox.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.PreferenceFragmentCompat;

import com.gamebox.R;

public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
        loadSettings();
    }

    public void setDarkMode(boolean darkModeSetting) {
        if (darkModeSetting) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    public void setLanguage(String language) {

    }

    private void loadSettings() {

    }

    private void saveSettings() {

    }

    @Override
    public void onPause() {
        super.onPause();
        saveSettings();
    }
}