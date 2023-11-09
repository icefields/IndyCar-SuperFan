package org.hungrytessy.indycarsuperfan.ui.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import org.hungrytessy.indycarsuperfan.IndyFragment
import org.hungrytessy.indycarsuperfan.databinding.FragmentNewsBinding
import org.hungrytessy.indycarsuperfan.ui.home.HomeViewModel

class NewsFragment : IndyFragment() {
    private var _binding: FragmentNewsBinding? = null
    private val binding get() = _binding!!
    private lateinit var newsViewModel: NewsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        newsViewModel = ViewModelProvider(this)[NewsViewModel::class.java]
        _binding = FragmentNewsBinding.inflate(inflater, container, false)
        initObservables()
        newsViewModel.fetchFeed()
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
