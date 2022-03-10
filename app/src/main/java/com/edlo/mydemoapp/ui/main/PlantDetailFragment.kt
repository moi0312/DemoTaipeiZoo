package com.edlo.mydemoapp.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.edlo.mydemoapp.R
import com.edlo.mydemoapp.databinding.FragmentPlantDetailBinding
import com.edlo.mydemoapp.ui.base.BaseFragment
import com.edlo.mydemoapp.util.glideLoadUrl

class PlantDetailFragment : BaseFragment<FragmentPlantDetailBinding>() {

    private lateinit var viewModel: TaipeiZooViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initViewModelObserve()
    }

    override fun initViewBinding(inflater: LayoutInflater,
             container: ViewGroup?, savedInstanceState: Bundle?): FragmentPlantDetailBinding {
        return FragmentPlantDetailBinding.inflate(inflater, container, false)
    }

    override fun initViewModel() {
        viewModel = ViewModelProvider(requireActivity() as ViewModelStoreOwner).get(TaipeiZooViewModel::class.java)
    }

    private fun initViewModelObserve() { }

    private fun initView() {
        initToolbar(viewModel.currentSelectedPlant.value!!.nameCh)

        viewModel.currentSelectedPlant.value?.let { item ->
            binding.txtName.text = "${item.nameCh}\n${item.nameLatin}\n${item.nameEn}"
            binding.txtInfo1.text = "${item.family}\n${item.genus}\n${getString(R.string.detailAlsoKnown, item.alsoKnown)}"
            binding.txtInfo2.text = getString(R.string.detailLocation, item.location)
            binding.txtInfo3.text = item.brief
            binding.txtInfo4.text = item.feature
            if(!item.functionAndApplication.isNullOrEmpty()){
                binding.txtInfo5.text = getString(R.string.detailApplication, item.functionAndApplication)
            }

            glideLoadUrl(item.pic01URL, binding.imgMain)
        }
    }

    override fun addDisposable() { }

    override fun onDetach() {
        super.onDetach()
        viewModel.setCurrentSelectedPlant(null)
    }
}