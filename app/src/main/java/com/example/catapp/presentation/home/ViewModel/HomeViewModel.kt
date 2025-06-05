package com.example.catapp.presentation.home.ViewModel

import androidx.lifecycle.ViewModel
import com.example.catapp.data.CatInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val catService: CatInterface
): ViewModel(){

}
