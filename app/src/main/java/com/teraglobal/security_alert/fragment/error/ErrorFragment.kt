package com.teraglobal.security_alert.fragment.error

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.teraglobal.security_alert.databinding.FragmentErrorBinding
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class ErrorFragment : DaggerFragment() {

    private lateinit var viewDataBinding: FragmentErrorBinding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<ErrorViewModel> { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = FragmentErrorBinding.inflate(inflater)
        viewDataBinding.lifecycleOwner = viewLifecycleOwner
        viewDataBinding.viewModel = viewModel

        return viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getFragmentArguments()
        observeEvent()
    }

    private fun getFragmentArguments() {
        val args = ErrorFragmentArgs.fromBundle(requireArguments())
        viewModel.setLocalError(args.localError)
    }

    private fun observeEvent() {
        viewModel.result.observe(viewLifecycleOwner) {
            findNavController().navigateUp()
        }


    }

}