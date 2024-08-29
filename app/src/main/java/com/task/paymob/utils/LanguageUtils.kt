package com.task.paymob.utils

import android.content.Context
import android.content.res.Configuration
import android.os.Build
import timber.log.Timber
import java.util.Locale


object LanguageUtils {

    @JvmStatic
    fun onAttach(context: Context): Context? {
        val lang = getPersistedData(context)
        return setLocale(context, lang)
    }

    fun setLocale(context: Context, language: String?): Context? {
        persist(context, language)
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            updateResources(context, language)
        } else updateResourcesLegacy(context, language)
    }

    private fun persist(language: String?, sharedPreferencesUtils: PreferencesUtils) {
        sharedPreferencesUtils.putString("", language!!)
    }


    private fun getPersistedData(context: Context): String {
        return ""
    }


    fun persist(context: Context, language: String?) {
        PreferencesUtils(context).getInstance()?.putString("LANGUAGE_KEY", language!!)
    }

    private fun updateResources(context: Context, language: String?): Context? {
        Timber.d("Locale.. ${language}")
        val locale = Locale(language)
        Locale.setDefault(locale)
        //val configuration = context.resources.configuration
        val configuration = Configuration()
        configuration.setLocale(locale)
        configuration.setLayoutDirection(locale)
        return context.createConfigurationContext(configuration)
    }

    private fun updateResourcesLegacy(context: Context, language: String?): Context {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val resources = context.resources
        val configuration = resources.configuration
        configuration.locale = locale
        configuration.setLayoutDirection(locale)
        resources.updateConfiguration(configuration, resources.displayMetrics)
        return context
    }

}