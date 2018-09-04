package org.michiganhackers.michiganhackers;

import android.app.Activity;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;

import static android.content.Context.MODE_PRIVATE;


public class ThemeHandler {
    private static final String TAG = "ThemeHandler";
    private Activity activity;
    public static final String PREFS_NAME = "prefs";
    public static final String PREF_THEME = "theme";
    public static String theme;


    public ThemeHandler(Activity activity) {
        this.activity = activity;
    }
    public void themeSelected(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        SharedPreferences.Editor editor = activity.getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit();

        switch(view.getId()) {
            case R.id.lightTheme:
                if (checked) {
                    Log.i(TAG, "Light Theme Selected");
                    editor.putString(PREF_THEME, "Light");
                }
                break;
            case R.id.darkTheme:
                if (checked) {
                    Log.i(TAG, "Dark Theme Selected");
                    editor.putString(PREF_THEME, "Dark");
                }
                break;


        }
        editor.apply();
        activity.recreate();
    }
    public void setTheme() {

        SharedPreferences preferences = activity.getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        theme = preferences.getString(PREF_THEME, "Light");
        switch(theme) {
            case "Light":
                activity.setTheme(R.style.BaseTheme);
                break;
            case "Dark":
                activity.setTheme(R.style.DarkTheme);
                break;
        }
    }
}