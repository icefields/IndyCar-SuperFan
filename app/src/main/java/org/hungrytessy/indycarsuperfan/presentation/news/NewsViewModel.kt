package org.hungrytessy.indycarsuperfan.presentation.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.hungrytessy.indycarsuperfan.domain.model.IndyRssItem
import org.hungrytessy.indycarsuperfan.domain.repository.IndyRepository
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val indyRepository: IndyRepository
): ViewModel()  {
    private val _feed = MutableLiveData<List<IndyRssItem>>()
    val feed: LiveData<List<IndyRssItem>> = _feed

    init {
        viewModelScope.launch {
            _feed.value = indyRepository.fetchNews()
        }
    }
}
