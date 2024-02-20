package com.mytablayout.controls.verticalviewpager

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.mytablayout.R
import com.mytablayout.controls.verticalviewpager.modals.RankDetail
import com.mytablayout.controls.verticalviewpager.modals.VerticalRankPriceDetails
import com.mytablayout.databinding.CustomRankPopupControlBinding

class CustomRankPopupControl @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var binding =
        CustomRankPopupControlBinding.inflate(LayoutInflater.from(context), this, true)

    fun setData(
        verticalViewPagerData: ArrayList<VerticalRankPriceDetails>?,
        rankDetails: RankDetail?,
    ) {

        if (verticalViewPagerData != null) {
            binding.llRankPopupRoot.weightSum = 3f
            binding.vvCustomViewPagerControl.visibility = View.VISIBLE
            binding.vvCustomViewPagerControl.setDataList(verticalViewPagerData)
            binding.clExpertDetail.setBackgroundResource(R.drawable.expert_back_right_up_round)
            binding.clMissionView.setBackgroundResource(R.drawable.mission_back_right_down_round)
        } else {

            if (rankDetails != null) {
                binding.tvTitle.text = rankDetails?.rankName
                binding.tvRankDesp.text = rankDetails?.rankDesp
                binding.tvMissionText.text = rankDetails?.missionDesp

                binding.clExpertDetail.setBackgroundResource(R.drawable.expert_back)
                binding.clMissionView.setBackgroundResource(R.drawable.mission_back_below_round)
                binding.llRankPopupRoot.weightSum = 2f
                binding.vvCustomViewPagerControl.visibility = View.GONE
            }

        }

    }

}