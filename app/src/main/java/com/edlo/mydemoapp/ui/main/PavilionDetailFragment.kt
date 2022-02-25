package com.edlo.mydemoapp.ui.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.LinearLayoutManager
import com.edlo.mydemoapp.R
import com.edlo.mydemoapp.databinding.FragmentPavilionDetailBinding
import com.edlo.mydemoapp.ui.adapter.PlantsAdapter
import com.edlo.mydemoapp.ui.base.BaseFragment
import com.edlo.mydemoapp.util.glideLoadUrl


class PavilionDetailFragment : BaseFragment<FragmentPavilionDetailBinding>() {

    private lateinit var viewModel: TaipeiZooViewModel
    private var adapter = PlantsAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initViewModelObserve()
    }

    override fun initViewBinding(inflater: LayoutInflater,
             container: ViewGroup?, savedInstanceState: Bundle?): FragmentPavilionDetailBinding {
        return FragmentPavilionDetailBinding.inflate(inflater, container, false)
    }

    override fun initViewModel() {
        viewModel = ViewModelProvider(requireActivity() as ViewModelStoreOwner).get(TaipeiZooViewModel::class.java)
        initToolbar(getString(R.string.titlePavilion))
        viewModel.listPlants()
    }

    private fun initViewModelObserve() {
        viewModel.getPlants().observe(requireActivity() as LifecycleOwner, Observer {
            adapter.data = it
        })
    }

    private fun initView() {
        binding.listView.layoutManager = LinearLayoutManager(activity)
        binding.listView.adapter = adapter
        adapter.onClick = { index, data ->
            viewModel.setCurrentSelectedPlant(data)
        }

        viewModel.currentSelectedPavilion.value?.let { pavilion ->
            binding.txtName.text = pavilion.name
            binding.txtInfo1.text = getString(R.string.detailCategory, pavilion.category)
            binding.txtInfo2.text = getString(R.string.detailGeo, pavilion.geo)
            binding.txtInfo3.text = pavilion.info
            binding.btnVisit.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(pavilion.url))
                startActivity(intent)
            }

            glideLoadUrl(pavilion.picURL, binding.imgMain)
        }
    }

    override fun addDisposable() {  }

    override fun onDetach() {
        super.onDetach()
        viewModel.setCurrentSelectedPavilion(null)
    }
}