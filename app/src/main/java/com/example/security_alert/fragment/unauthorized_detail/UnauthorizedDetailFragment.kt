package com.example.security_alert.fragment.unauthorized_detail

import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.alexvas.rtsp.widget.RtspSurfaceView
import com.example.security_alert.databinding.FragmentUnauthorizedDetailBinding
import com.example.security_alert.utils.NavUtil
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class UnauthorizedDetailFragment : DaggerFragment() {

    private lateinit var viewDataBinding: FragmentUnauthorizedDetailBinding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<UnauthorizedDetailViewModel> { viewModelFactory }

    private val rtspStatusListener = object: RtspSurfaceView.RtspStatusListener {
        override fun onRtspStatusConnecting() {

            viewDataBinding.pbLoading.visibility = View.VISIBLE
            viewDataBinding.vShutter.visibility = View.VISIBLE
        }

        override fun onRtspFirstFrameRendered() {
            viewDataBinding.vShutter.visibility = View.GONE
        }

        override fun onRtspStatusConnected() {
            viewDataBinding.pbLoading.visibility = View.GONE
            viewDataBinding.btStartRtsp.text = "Stop Video"
        }

        override fun onRtspStatusDisconnected() {
            viewDataBinding.btStartRtsp.text = "Start Video"
            viewDataBinding.pbLoading.visibility = View.GONE
            viewDataBinding.vShutter.visibility = View.VISIBLE
        }

        override fun onRtspStatusFailed(message: String?) {
            viewDataBinding.pbLoading.visibility = View.GONE
        }

        override fun onRtspStatusFailedUnauthorized() {
            if (context == null) return
            viewDataBinding.pbLoading.visibility = View.GONE
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = FragmentUnauthorizedDetailBinding.inflate(inflater)
        viewDataBinding.lifecycleOwner = viewLifecycleOwner
        viewDataBinding.viewModel = viewModel

        handleBackButton()

        return viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getFragmentArguments()
        setupVideoRtsp()
    }

    private fun getFragmentArguments() {
        val args = UnauthorizedDetailFragmentArgs.fromBundle(requireArguments())
        viewModel.setUnauthorized(args.content)
    }

    private fun setupVideoRtsp() {
        val svVideo = viewDataBinding.svVideo
        svVideo.setStatusListener(rtspStatusListener)
        val btStartRtsp = viewDataBinding.btStartRtsp

        btStartRtsp.setOnClickListener {
            if (svVideo.isStarted()) {
                svVideo.stop()
            } else {
                val rtspUrl = viewModel.getRtspUrl()
                var rtspUrlFormatted = rtspUrl.replace("admin:admin@", "")
                val uri = Uri.parse(rtspUrlFormatted)
                svVideo.init(uri, viewModel.rtspUsername, viewModel.rtspPassword, "rtsp-client-android")
                svVideo.start(true, requestAudio = false)
            }
        }
    }

    private fun handleBackButton() {
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    val svVideo = viewDataBinding.svVideo
                    svVideo.stop()
                    val args = UnauthorizedDetailFragmentArgs.fromBundle(requireArguments())
                    setFragmentResult(
                        args.requestKey,
                        bundleOf(NavUtil.RESULT to NavUtil.RESULT_OK)
                    )
                    findNavController().navigateUp()
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }
}