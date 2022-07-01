package com.example.security_alert.fragment.unauthorized

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.clearFragmentResultListener
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.security_alert.databinding.FragmentUnauthorizedBinding
import com.example.security_alert.fragment.unauthorized.model.UnauthorizedContent
import com.example.security_alert.utils.EventObserver
import com.example.security_alert.utils.NavUtil
import dagger.android.support.DaggerFragment
import timber.log.Timber
import javax.inject.Inject


class UnauthorizedFragment : DaggerFragment() {

    private lateinit var viewDataBinding: FragmentUnauthorizedBinding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<UnauthorizedViewModel> { viewModelFactory }

    private lateinit var adapter: UnauthorizedAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = FragmentUnauthorizedBinding.inflate(inflater)
        viewDataBinding.lifecycleOwner = this
        viewDataBinding.viewModel = viewModel

        return viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
        setupScrollview()
        observeViewModel()
    }

    private fun setupScrollview() {
        val svUnauthorized = viewDataBinding.svUnauthorized
        svUnauthorized.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            if (scrollY ==  v.getChildAt(0).measuredHeight - v.measuredHeight) {
                if (!viewModel.isScrolled()) {
                    viewModel.setNextPage(1)
                    viewModel.requestUnauthorizedReport(false)
                }

            }
        })
    }

    private fun setupAdapter() {
        val viewModel = viewDataBinding.viewModel
        viewModel.let {
            adapter = createAdapter()
            val rvUnauthorized = viewDataBinding.rvAlertUser
            rvUnauthorized.addItemDecoration(
                DividerItemDecoration(
                    context,
                    DividerItemDecoration.VERTICAL
                )
            )
            rvUnauthorized.adapter = adapter
        }
    }

    private fun createAdapter() : UnauthorizedAdapter {
        return UnauthorizedAdapter(object : UnauthorizedAdapterClickListener {
            override fun onItemClick(item: UnauthorizedContent) {
                viewModel.sendReadUnauthorizedReport(item)
            }

        })
    }

    private fun observeViewModel() {
        viewModel.errorEvent.observe(viewLifecycleOwner, EventObserver {
            val action = UnauthorizedFragmentDirections.actionUnauthorizedFragmentToErrorFragment(
                it
            )
            findNavController().navigate(action)
        })

        viewModel.unauthorizedContent.observe(viewLifecycleOwner, EventObserver{
            setFragmentResultListener(NavUtil.UNAUTHORIZED_DETAIL_KEY) { requestKey, bundle ->
                clearFragmentResultListener(requestKey)
                val result = bundle.getInt(NavUtil.RESULT)
                if (result == NavUtil.RESULT_OK) {
                    viewModel.requestUnauthorizedReport(true)
                }

            }
            val action = UnauthorizedFragmentDirections.actionUnauthorizedFragmentToUnauthorizedDetailFragment(
                NavUtil.UNAUTHORIZED_DETAIL_KEY,
                it
            )
            findNavController().navigate(action)
        })
    }
}