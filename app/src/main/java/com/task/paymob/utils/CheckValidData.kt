package com.task.paymob.utils

import android.content.Context
import com.google.android.material.textfield.TextInputLayout
import com.hbb20.CountryCodePicker

object CheckValidData {


    fun checkName(inputLayout: TextInputLayout,context: Context): Boolean {
        return EditTextValidation.validName(inputLayout, context)
    }

    fun checkPhone(countryCode: CountryCodePicker, inputLayout: TextInputLayout): Boolean {
        return EditTextValidation.validPhone(countryCode, inputLayout)
    }
    fun checkAge(inputLayout: TextInputLayout): Boolean {
        return EditTextValidation.validAge(inputLayout)
    }

}
