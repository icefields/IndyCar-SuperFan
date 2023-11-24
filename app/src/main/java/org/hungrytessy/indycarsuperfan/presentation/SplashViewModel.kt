package org.hungrytessy.indycarsuperfan.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.hungrytessy.indycarsuperfan.data.IndyDataStore
import org.hungrytessy.indycarsuperfan.domain.repository.IndyRepository
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val indyRepository: IndyRepository
): ViewModel() {
    private val _dataReady = MutableLiveData<Boolean>()
    val dataReady: LiveData<Boolean> = _dataReady

    fun generate() {
        viewModelScope.launch {
            indyRepository.generate()
            _dataReady.value = true
        }
    }
}
