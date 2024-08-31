package com.task.paymob.base


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar


abstract class BaseFragment<VDB : ViewBinding> : Fragment() {
    private var _binding: VDB? = null
    protected val binding get() = _binding!!
    private var snackBar: Snackbar? = null
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
        handleBars()
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

    private fun handleBars() {
        (requireActivity() as AppCompatActivity).supportActionBar?.hide()

        val window = requireActivity().window
        val decorView = window.decorView

        WindowInsetsControllerCompat(window, decorView).apply {
            // Show the status bar
            show(WindowInsetsCompat.Type.statusBars())
            // Show the navigation bar
            show(WindowInsetsCompat.Type.navigationBars())
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }



}