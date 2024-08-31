package com.task.paymob.di


import com.task.paymob.viewmodel.home.HomeViewModel
import com.task.paymob.viewmodel.movies_details.MoviesDetailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val viewModelModule = module {

    viewModel {
        HomeViewModel(repository = get() , sharedRepository = get() )
    }

    viewModel {
        MoviesDetailsViewModel(repository = get(),sharedRepository = get())
    }

}