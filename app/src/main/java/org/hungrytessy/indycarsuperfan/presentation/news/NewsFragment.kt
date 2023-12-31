package org.hungrytessy.indycarsuperfan.presentation.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import org.hungrytessy.indycarsuperfan.presentation.IndyFragment
import org.hungrytessy.indycarsuperfan.databinding.FragmentNewsBinding

@AndroidEntryPoint
class NewsFragment : IndyFragment() {
    private var _binding: FragmentNewsBinding? = null
    private val binding get() = _binding!!
    private val newsViewModel: NewsViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentNewsBinding.inflate(inflater, container, false)
        initObservables()
        return binding.root
    }

    private fun initObservables() {
        newsViewModel.feed.observe(viewLifecycleOwner) { feed ->
            val adapter = RssFeedAdapter(feed)
            binding.rssFeedList.adapter = adapter
            binding.rssFeedList.layoutManager = LinearLayoutManager(requireContext())
            binding.progressView.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
