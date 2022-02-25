package com.edlo.mydemoapp.ui.main

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.IBinder
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.edlo.mydemoapp.R
import com.edlo.mydemoapp.databinding.ActivityMainBinding
import com.edlo.mydemoapp.helper.DialogHelper
import com.edlo.mydemoapp.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.disposables.Disposable
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : BaseActivity() {

    @Inject lateinit var dialogHelper: DialogHelper

    private lateinit var binding: ActivityMainBinding

    lateinit var viewModel: TaipeiZooViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViewModel()

        changeFragment(PavilionListFragment::class.java)
    }

    override fun initViewModel() {
        viewModel = ViewModelProvider(this).get(TaipeiZooViewModel::class.java)
    }

    override fun addDisposable() {
        disposable.addAll(
            subscribeLoading(),
            subscribeSelectedPavilion(),
            subscribeSelectedPlant()
        )
    }

    private fun subscribeLoading(): Disposable {
        return viewModel.onLoading.subscribe { isLoading ->
            if(isLoading) {
                dialogHelper.showProgressDialog(this)
            } else {
                dialogHelper.hideProgressDialog(this)
            }
        }
    }

    private fun subscribeSelectedPavilion(): Disposable {
        return viewModel.onPavilionSelected.subscribe { pavilion ->
            pushFragment(PavilionDetailFragment::class.java)
        }
    }

    private fun subscribeSelectedPlant(): Disposable {
        return viewModel.onPlantSelected.subscribe { plant ->
            pushFragment(PlantDetailFragment::class.java)
        }
    }

    fun hideKeyboard(token: IBinder?): Boolean {
        if (token != null) {
            val im = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            return im.hideSoftInputFromWindow(
                token,
                InputMethodManager.HIDE_NOT_ALWAYS
            )
        }
        return false
    }
}