package com.edlo.mydemoapp.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.*
import androidx.recyclerview.widget.GridLayoutManager
import com.edlo.mydemoapp.R
import com.edlo.mydemoapp.databinding.FragmentSimpleListBinding
import com.edlo.mydemoapp.ui.adapter.PavilionsAdapter
import com.edlo.mydemoapp.ui.base.BaseFragment

class PavilionListFragment : BaseFragment<FragmentSimpleListBinding>() {

    private lateinit var viewModel: TaipeiZooViewModel
    private var adapter = PavilionsAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initToolbar(getString(R.string.app_name))
        initView()
        initViewModelObserve()

        viewModel.listPavilions()
    }

    private fun initView() {
        binding.listView.layoutManager = GridLayoutManager(activity, 3)
        binding.listView.adapter = adapter
        adapter.emptyView = binding.txtListEmpty
        adapter.onClick = { index, data ->
            viewModel.setCurrentSelectedPavilion(data)
        }
    }

    private fun initViewModelObserve() {
        viewModel.getPavilions().observe(requireActivity() as LifecycleOwner, Observer {
            adapter.data = it
        })
    }

    override fun initViewModel() {
        viewModel = ViewModelProvider(requireActivity() as ViewModelStoreOwner).get(TaipeiZooViewModel::class.java)
    }

    override fun initViewBinding(inflater: LayoutInflater,
            container: ViewGroup?, savedInstanceState: Bundle?): FragmentSimpleListBinding {
        return FragmentSimpleListBinding.inflate(inflater, container, false)
    }

    override fun addDisposable() {
//        disposable.add(
//            viewModel.onScrollReachesEdge.subscribe {
//                when(it) {
////                    BaseViewModel.SCROLL_OVER_TOP -> { }
//                    BaseViewModel.SCROLL_OVER_BOTTOM -> {
//                        //todo: show loading and load next
//                        viewModel.getMorePavilions()
//                    }
//                    else -> { }
//                }
//            }
//        )
    }
}