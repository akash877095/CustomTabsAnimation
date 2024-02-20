package com.mytablayout.controls

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import com.mytablayout.databinding.CustomViewMfsBinding
import com.mytablayout.modals.MFSModal

class CustomControlViewMFS @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {

    private var binding: CustomViewMfsBinding = CustomViewMfsBinding.inflate(
        LayoutInflater.from(context), this, true
    )

    fun setData(data: MFSModal) {

        val count = data.list.size

        binding.imageView.setImageResource(data.image)
        binding.tvTitle.text = data.title
        binding.tvDescription.text = data.description
        binding.tvPayDueText.text = data.pay_due_text

        if (data.pay_due_text.isNotEmpty()) binding.llRedText.visibility = View.VISIBLE
        else binding.llRedText.visibility = View.GONE

        for (i in 0 until count) {
            when (i) {
                0 -> {
                    Log.e("dsdsdsdsdsdsd", " i = $i")
                    binding.tvTotalTitle.text = data.list[i].title
                    binding.tvTotalValue.text = data.list[i].value
                    binding.llDetailsView.weightSum = 1f
                    binding.vvTotalSaldo.visibility = GONE
                    binding.vvLimit.visibility = GONE

                    binding.llTotalSaldo.visibility = VISIBLE
                    binding.llLimit.visibility = GONE
                    binding.llValue3.visibility = GONE
                }

                1 -> {
                    Log.e("dsdsdsdsdsdsd", " i = $i")
                    binding.tvLimitTitle.text = data.list[i].title
                    binding.tvLimitValue.text = data.list[i].value
                    binding.llDetailsView.weightSum = 2f
                    binding.vvTotalSaldo.visibility = VISIBLE
                    binding.vvLimit.visibility = GONE

                    binding.llTotalSaldo.visibility = VISIBLE
                    binding.llLimit.visibility = VISIBLE
                    binding.llValue3.visibility = GONE
                }

                else -> {
                    Log.e("dsdsdsdsdsdsd", " i = $i")
                    binding.tvValue3.text = data.list[i].title
                    binding.tvValue3Value.text = data.list[i].value
                    binding.llDetailsView.weightSum = 3f
                    binding.vvTotalSaldo.visibility = VISIBLE
                    binding.vvLimit.visibility = VISIBLE

                    binding.llTotalSaldo.visibility = VISIBLE
                    binding.llLimit.visibility = VISIBLE
                    binding.llValue3.visibility = VISIBLE
                }
            }
        }

    }


}