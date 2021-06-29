package com.opaku.id.ui.dashboard.fragment.home.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import com.opaku.id.databinding.FragmentBannerBinding
import com.opaku.id.ui.offerscreen.OfferScreenActivity

class BannerFragment : Fragment() {

    private var resource: Int = 0

    private var _binding: FragmentBannerBinding? = null
    private val binding get() = _binding!!
    private lateinit var firebaseAnalytics: FirebaseAnalytics

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
        setupAnalytic()
        setupView()
    }

    private fun setupAnalytic() {
        firebaseAnalytics = Firebase.analytics
    }

    private fun setupView() {
        binding.ivBanner.apply {
            setImageResource(resource)
            setOnClickListener {

                val promoParams = Bundle().apply {
                    putString(FirebaseAnalytics.Param.PROMOTION_ID, "SUMMER_FUN")
                    putString(FirebaseAnalytics.Param.PROMOTION_NAME, "Summer Sale")
                    putString(FirebaseAnalytics.Param.CREATIVE_NAME, "summer2020_promo.jpg")
                    putString(FirebaseAnalytics.Param.CREATIVE_SLOT, "featured_app_1")
                    putString(FirebaseAnalytics.Param.LOCATION_ID, "HERO_BANNER")
//                    putParcelableArray(FirebaseAnalytics.Param.ITEMS, arrayOf<Parcelable>(itemJeggings))
                }

                firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_PROMOTION, promoParams)

                startActivity(Intent(requireActivity(), OfferScreenActivity::class.java))
            }
        }
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