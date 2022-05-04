package com.programmers.kmooc.activities.list

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.programmers.kmooc.KmoocApplication
import com.programmers.kmooc.activities.detail.KmoocDetailActivity
import com.programmers.kmooc.databinding.ActivityKmookListBinding
import com.programmers.kmooc.models.Lecture
import com.programmers.kmooc.models.LectureList
import com.programmers.kmooc.utils.toVisibility
import com.programmers.kmooc.viewmodels.KmoocListViewModel
import com.programmers.kmooc.viewmodels.KmoocListViewModelFactory

class KmoocListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityKmookListBinding
    private lateinit var viewModel: KmoocListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val kmoocRepository = (application as KmoocApplication).kmoocRepository
        viewModel = ViewModelProvider(this, KmoocListViewModelFactory(kmoocRepository)).get(
            KmoocListViewModel::class.java
        )

        binding = ActivityKmookListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = LecturesAdapter()
            .apply { onClick = this@KmoocListActivity::startDetailActivity }

        binding.pullToRefresh.setOnRefreshListener {
            viewModel.list()
        }

        binding.lectureList.adapter = adapter

        binding.lectureList.setItemViewCacheSize(adapter.itemCount)

        viewModel.lectureLiveData.observe(this){ response ->
            adapter.updateLectures(response.results)
            binding.pullToRefresh.isRefreshing = false
        }

        viewModel.progressVisible.observe(this,{ visible ->
            binding.progressBar.visibility = visible.toVisibility()
        })

        binding.lectureList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val lastVisibleItem = (binding.lectureList.layoutManager as LinearLayoutManager)
                    .findLastCompletelyVisibleItemPosition()

                if(viewModel.progressVisible.value == false)
                 if(adapter.itemCount <= lastVisibleItem.plus(5)) {
                    viewModel.next()
                 }
            }
        })

        viewModel.list()
    }

    private fun startDetailActivity(lecture: Lecture) {
        startActivity(
            Intent(this, KmoocDetailActivity::class.java)
                .apply { putExtra(KmoocDetailActivity.INTENT_PARAM_COURSE_ID, lecture.id) }
        )
    }
}
