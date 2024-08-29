package com.task.paymob.base

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar
import com.task.paymob.MainActivity


abstract class BaseFragment<VDB : ViewBinding> : Fragment() {
    private var _binding: VDB? = null
    protected val binding get() = _binding!!
    var snackBar: Snackbar? = null
    private var notifyCount = 0
    private var offersCount = 0
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = getViewBinding(inflater, container)
        return _binding!!.root
    }

    protected abstract fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): VDB


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleBars(true)
        setData()
        initToolBar()
        onCreateInit()
        getFirebaseToken()
        initSetAdapter()
        initViewModel()
        initClicks()

    }

    override fun onResume() {
        super.onResume()
        setData()

    }

    private fun setData() {
        //USER_DATA.CURRENT_USER = PreferencesUtils(requireContext()).getUserData(USER_DATA_KEY)
    }

    private fun getFirebaseToken() {
//        FirebaseToken.getToken()
    }

    protected abstract fun initClicks()

    protected abstract fun initViewModel()

    protected abstract fun onCreateInit()

    protected abstract fun initSetAdapter()

    protected abstract fun initToolBar()

    fun checkCurrentDestination(currentFragment: Int): Boolean {
        return findNavController().currentDestination?.id == currentFragment
    }

    fun showSnackbar(message: String?) {
        snackBar = Snackbar.make(_binding!!.root, message!!, Snackbar.LENGTH_LONG)
        snackBar?.show()
    }

    override fun onPause() {
        super.onPause()
        if (snackBar != null) {
            snackBar?.dismiss()
        }
    }

    private fun handleBars(isHidden: Boolean) {

        when (isHidden) {
            true -> {
                (requireActivity() as AppCompatActivity).supportActionBar?.hide()
                activity?.window?.setFlags(
                    WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN
                )
            }

            else -> {
                (requireActivity() as AppCompatActivity).supportActionBar?.hide()
                activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
            }

        }

    }

    protected fun updateStatusBarColor(color: Int) {
        // Color must be in hexadecimal fromat
        val hexColor = Integer.toHexString(ContextCompat.getColor(requireContext(), color))
        val window: Window = (activity as MainActivity).window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = Color.parseColor("#$hexColor")
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }



    fun hideKeyboard() {
        val imm =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(requireView().windowToken,0)
    }
    fun adjustIconsColor(isWhite : Boolean) {
        if (!isWhite) {
            activity?.window?.getDecorView()?.setSystemUiVisibility(
                activity?.window?.getDecorView()
                    ?.getSystemUiVisibility()!! or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            )
        } else {

            activity?.window?.decorView?.systemUiVisibility = activity?.window?.decorView
                ?.systemUiVisibility!! and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
        }
    }
}