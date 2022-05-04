package com.programmers.kmooc.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.programmers.kmooc.models.Lecture
import com.programmers.kmooc.models.LectureList
import com.programmers.kmooc.repositories.KmoocRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Collections.addAll

class KmoocListViewModel(private val repository: KmoocRepository) : ViewModel() {

    val lectureLiveData : MutableLiveData<LectureList> = MutableLiveData()
    var progressVisible = MutableLiveData<Boolean>()

    fun list() {
        progressVisible.postValue(true)
        repository.list { lectureList ->
            lectureLiveData.postValue(lectureList)
            progressVisible.postValue(false)
        }
    }

    fun next() {
        progressVisible.postValue(true)
        val liveData = lectureLiveData.value ?: return
             repository.next(liveData) { lectureList ->
                 val list = liveData.results.toMutableList().apply {
                     addAll(lectureList.results)
                 }
                 lectureList.results = list
                 lectureLiveData.postValue(lectureList)
                 progressVisible.postValue(false)
        }
    }
}

class KmoocListViewModelFactory(private val repository: KmoocRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(KmoocListViewModel::class.java)) {
            return KmoocListViewModel(repository) as T
        }
        throw IllegalAccessException("Unkown Viewmodel Class")
    }
}