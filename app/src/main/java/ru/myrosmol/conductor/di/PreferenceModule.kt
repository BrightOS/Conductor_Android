package ru.myrosmol.conductor.di

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.myrosmol.conductor.data.repository.PreferenceRepository

@Module
@InstallIn(SingletonComponent::class)
class PreferenceModule {
    @Provides
    @Reusable
    fun providePreferenceRepository(@ApplicationContext context: Context): PreferenceRepository =
        PreferenceRepository(
            context.getSharedPreferences(
                "conductor_prefs",
                Context.MODE_PRIVATE
            )
        )
}