package com.task.paymob.utils

import android.content.Context
import com.google.android.material.textfield.TextInputLayout
import com.hbb20.CountryCodePicker
import com.task.paymob.R
import java.util.Locale
import java.util.regex.Matcher
import java.util.regex.Pattern


object EditTextValidation {


    fun validName(
        textInputLayout: TextInputLayout,context: Context
    ): Boolean {
        val validName = "^(?![ .]*$)[\\w\\d\\p{L} .]*$"
        val mName = textInputLayout.editText?.text.toString().trim().lowercase(Locale.ROOT)
        val matcherName: Matcher = Pattern.compile(validName).matcher(mName)
        if (matcherName.matches() && textInputLayout.editText?.text.toString()
                .trim().isNotEmpty()
        ) {
            textInputLayout.error = null
            return true
        } else {
            textInputLayout.error =context.getString(R.string.please_enter_valid_name)

            textInputLayout.editText?.requestFocus()
            return false
        }
    }

    fun validPhone(mPhone: CountryCodePicker, mPhoneEdit: TextInputLayout): Boolean {
        var isValid = false
        mPhone.setPhoneNumberValidityChangeListener {
            if (mPhone.isValidFullNumber) {
                mPhoneEdit.error = null
                isValid = true
            }

            else {
                mPhoneEdit.error = "Please enter a valid phone number"
                mPhone.requestFocus()
                isValid = false
            }
        }
        return isValid
    }


    fun validAge(
        textInputLayout: TextInputLayout
    ): Boolean {
        val mAge = textInputLayout.editText?.text.toString().trim()
        return if (mAge.toInt()>12) {
            textInputLayout.error = null
            true
        } else {
            textInputLayout.error =
                "You have to be older than 12 to continue"
            textInputLayout.editText?.requestFocus()
            false
        }
    }

}
