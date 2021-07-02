package com.opaku.id.ui.dashboard.fragment.home.fragment

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import com.opaku.id.core.domain.model.BannerModel
import com.opaku.id.core.utils.setImageDrawableFromServer
import com.opaku.id.databinding.FragmentBannerBinding
import com.opaku.id.ui.offerscreen.OfferScreenActivity

class BannerFragment : Fragment() {

    private lateinit var data: BannerModel

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
            setImageDrawableFromServer(data.image)
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

        binding.tvTitle.text = data.title

        object : CountDownTimer(86400000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val hours = millisUntilFinished / (60 * 60 * 1000) % 24
                val minutes = millisUntilFinished / (60 * 1000) % 60
                val seconds = millisUntilFinished / 1000 % 60

                binding.tvHour.text = hours.toString()
                binding.tvMinute.text = minutes.toString()
                binding.tvSecond.text = seconds.toString()
            }

            override fun onFinish() {}

        }.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        fun newInstance(data: BannerModel) =
            BannerFragment().apply {
                this.data = data
            }
    }
}