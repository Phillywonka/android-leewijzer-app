package philip.com.cache

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Singleton

/**
 * General Preferences Helper class, used for storing preference values using the Preference API
 */
@Singleton
open class PreferencesHelper(context: Context) {

    companion object {
        private val PREF_APP_PACKAGE_NAME = "com.philip.leeswijzer_app.preferences"

        private val PREF_KEY_LAST_CACHE = "last_cache"
    }

    private val appPref: SharedPreferences

    init {
        appPref = context.getSharedPreferences(PREF_APP_PACKAGE_NAME, Context.MODE_PRIVATE)
    }

    /**
     * Store and retrieve the last time data was cached
     */
    var lastCacheTime: Long
        get() = appPref.getLong(PREF_KEY_LAST_CACHE, 0)
        set(lastCache) = appPref.edit().putLong(PREF_KEY_LAST_CACHE, lastCache).apply()

}
