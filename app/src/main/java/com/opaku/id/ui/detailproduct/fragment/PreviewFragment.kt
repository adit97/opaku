package com.opaku.id.ui.detailproduct.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.opaku.id.core.utils.setImageDrawableFromServer
import com.opaku.id.databinding.FragmentPreviewBinding

class PreviewFragment : Fragment() {

    private lateinit var resource: String

    private var _binding: FragmentPreviewBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPreviewBinding.inflate(layoutInflater, container, false)
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
        binding.ivPreview.setImageDrawableFromServer(resource)
    }

    companion object {
        fun newInstance(resource: String) =
            PreviewFragment().apply {
                this.resource = resource
            }
    }
}