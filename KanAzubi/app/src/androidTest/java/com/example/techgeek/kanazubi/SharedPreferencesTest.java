package com.example.techgeek.kanazubi;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.test.InstrumentationRegistry;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SharedPreferencesTest {
    private static final String PREFS_NAME = "KanAzubi";
    private static final String KEY_PREF = "prefs";
    private SharedPreferences sharedPreferences;

    @Before
    public void before() {
        Context context = InstrumentationRegistry.getTargetContext();
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    @Test
    public void putAndGetID() throws Exception {
        int putInt = 3;
        sharedPreferences.edit().putInt(KEY_PREF, putInt).apply();
        int getInt = sharedPreferences.getInt(KEY_PREF, 0);
        assertEquals(putInt, getInt);
    }

    @Test
    public void putAndGetEmail() throws Exception {
        String putString = "test@dataport.de";
        sharedPreferences.edit().putString(KEY_PREF, putString).apply();
        String getString = sharedPreferences.getString(KEY_PREF, "");
        assertEquals(putString, getString);
    }

    @Test
    public void putAndGetName() throws Exception {
        String putString = "testMe";
        sharedPreferences.edit().putString(KEY_PREF, putString).apply();
        String getString = sharedPreferences.getString(KEY_PREF, "");
        assertEquals(putString, getString);
    }

    @After
    public void after() {
        sharedPreferences.edit().putString(KEY_PREF, null).apply();
    }
}