package com.programmers.kmooc.activities.detail

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.ViewModelProvider
import com.programmers.kmooc.KmoocApplication
import com.programmers.kmooc.R
import com.programmers.kmooc.databinding.ActivityKmookDetailBinding
import com.programmers.kmooc.models.Lecture
import com.programmers.kmooc.network.ImageLoader
import com.programmers.kmooc.utils.DateUtil
import com.programmers.kmooc.utils.toVisibility
import com.programmers.kmooc.viewmodels.KmoocDetailViewModel
import com.programmers.kmooc.viewmodels.KmoocDetailViewModelFactory
import java.net.URI

class KmoocDetailActivity : AppCompatActivity() {

    companion object {
        const val INTENT_PARAM_COURSE_ID = "param_course_id"
    }


    private lateinit var binding: ActivityKmookDetailBinding
    private lateinit var viewModel: KmoocDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val kmoocRepository = (application as KmoocApplication).kmoocRepository
        viewModel = ViewModelProvider(this, KmoocDetailViewModelFactory(kmoocRepository)).get(
            KmoocDetailViewModel::class.java
        )

        binding = ActivityKmookDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.detail(intent.getStringExtra(INTENT_PARAM_COURSE_ID))

        viewModel.progressLiveData.observe(this,{ visible ->
            binding.progressBar.visibility = visible.toVisibility()
        })

        viewModel.lectureLiveData.observe(this, { lecture ->
            val array = applicationContext.resources.getStringArray(R.array.lecture)
            binding.apply {
                lectureNumber.setDescription(array[0], lecture.number.toString())
                lectureType.setDescription(array[1], lecture.classfyName.toString())
                lectureOrg.setDescription(array[2], lecture.orgName.toString())
                lectureTeachers.setDescription(array[3], lecture.teachers.toString())
                lectureDue.setDescription(array[4], lecture.dateFormat())

                ImageLoader.loadImage(lecture.courseImageLarge.toString()) {
                    lectureImage.setImageBitmap(it)
                }
            }

            lecture.overview?.let { url ->
                binding.webView.loadData(url,"text/html; charset=utf-8","UTF - 8")
            }
        })
    }
}