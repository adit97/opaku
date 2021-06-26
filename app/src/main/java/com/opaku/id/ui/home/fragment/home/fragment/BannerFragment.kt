package com.opaku.id.ui.home.fragment.home.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.opaku.id.R
import com.opaku.id.databinding.FragmentBannerBinding

class BannerFragment : Fragment() {

    private var resource: Int = 0

    private var _binding: FragmentBannerBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBannerBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    private fun initView() {
        setupView()
    }

    private fun setupView() {
        binding.ivBanner.setImageResource(resource)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        fun newInstance(resource: Int) =
            BannerFragment().apply {
                this.resource = resource
            }
    }
}