package ru.myrosmol.conductor.data.repository

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class PreferenceRepository(private val sharedPreferences: SharedPreferences) {

    var nightMode: Int
        get() = sharedPreferences.getInt(PREFERENCE_NIGHT_MODE, PREFERENCE_NIGHT_MODE_DEF_VAL)
        set(value) {
            sharedPreferences.edit().putInt(PREFERENCE_NIGHT_MODE, value).apply()
            AppCompatDelegate.setDefaultNightMode(value)
            _nightModeLive.postValue(value)
        }

    private val _nightModeLive: MutableLiveData<Int> = MutableLiveData()
    val nightModeLive: LiveData<Int>
        get() = _nightModeLive

    var token: String
        get() = sharedPreferences.getString(PREFERENCE_TOKEN, "")!!
        set(value) {
            sharedPreferences.edit().putString(PREFERENCE_TOKEN, value).apply()
            _tokenLive.postValue(value)
        }

    private val _tokenLive: MutableLiveData<String> = MutableLiveData()
    val tokenLive: LiveData<String>
        get() = _tokenLive

    companion object {
        private const val PREFERENCE_NIGHT_MODE = "preference_night_mode"
        private const val PREFERENCE_NIGHT_MODE_DEF_VAL = AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM

        private const val PREFERENCE_TOKEN = "preference_token"
    }
}