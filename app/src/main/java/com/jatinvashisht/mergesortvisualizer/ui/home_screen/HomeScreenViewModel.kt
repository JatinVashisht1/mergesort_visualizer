package com.jatinvashisht.mergesortvisualizer.ui.home_screen

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jatinvashisht.mergesortvisualizer.util.MergesortHelper
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@HiltViewModel
class HomeScreenViewModel @Inject constructor(private val mergesortHelper: MergesortHelper)
    : ViewModel(){
    private var _intListState = mutableStateOf(listOf(5, 4, 6, 7, 1, 10, 15, 12))
    val intListState = _intListState

    private var phaseList: MutableList<List<Int>> = mutableListOf()
    private var currentIndex = 0
    private val _currentList = mutableStateOf(_intListState.value)
    val currentList = _currentList as State<List<Int>>

    private val _currentPhasesList = mutableStateListOf<List<Int>>(listOf<Int>())
    val currentPhasesList  = _currentPhasesList
    init {
        phaseList.add(_intListState.value)
        _currentPhasesList.add(_intListState.value)
        viewModelScope.launch{
            mergesortHelper.mergeSort(_intListState.value) {
                Log.d("viewmodel", "updated list is $it")
                phaseList.add(it)
            }
        }
    }

    fun start(){
        viewModelScope.launch {
            _currentPhasesList.removeRange(1, _currentPhasesList.size-1)
            for(i in currentIndex until phaseList.size){
//                _currentList.value = phaseList[i]
                _currentPhasesList.add(phaseList[i])
                delay(1000)
            }
            currentIndex = 0
        }
    }


}