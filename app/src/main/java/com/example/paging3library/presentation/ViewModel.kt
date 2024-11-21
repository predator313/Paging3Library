package com.example.paging3library.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.paging3library.data.local.BeerEntity
import com.example.paging3library.mapper.toBeer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class ViewModel @Inject constructor(
    pager: Pager<Int, BeerEntity>
): ViewModel() {
    val beerPaggingFlow = pager.flow.map {pagingData->
        pagingData.map {
            it.toBeer()
        }

    }.cachedIn(viewModelScope)
}