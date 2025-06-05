package com.example.catapp.presentation.fav.ViewModel

import androidx.lifecycle.ViewModel
import com.example.catapp.data.CatInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavViewModel @Inject constructor(private val catService: CatInterface): ViewModel(){

}