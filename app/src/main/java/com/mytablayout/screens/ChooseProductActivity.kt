package com.mytablayout.screens

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.mytablayout.R
import com.mytablayout.adaptors.ProductsAdapter
import com.mytablayout.databinding.ActivityChooseProductBinding
import com.mytablayout.modals.MFSModal
import com.mytablayout.modals.ProductsData
import com.mytablayout.modals.TotalPriceData

class ChooseProductActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityChooseProductBinding

    private var adapter: ProductsAdapter? = null

    private var selectedPosition = 0

    private var productList = ArrayList<ProductsData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChooseProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()

    }

    private fun initView() {

        productList.add(ProductsData(R.drawable.allo_no_circle, "Allo", "blue", true))
        productList.add(ProductsData(R.drawable.allo_no_circle, "Allo", "orange", false))
        productList.add(ProductsData(R.drawable.allo_no_circle, "Allo", "yellow", false))
        productList.add(ProductsData(R.drawable.allo_no_circle, "Allo", "blue", false))

        adapter = ProductsAdapter().apply {

            setOnClickListener(this@ChooseProductActivity)

            setItem(productList)

        }

        binding.rvProducts.adapter = adapter

        val list = ArrayList<TotalPriceData>()
        list.add(TotalPriceData("Total Saldo", "Rp10.999.0"))
        list.add(TotalPriceData("Limit Tersedia", "Rp0"))
        list.add(TotalPriceData("Value 3", "Rp1.000.0"))

        val data = MFSModal(
            R.drawable.popcorn_unselected,
            "Dummy Title",
            "Pay now, pay later and get instant cash this is sample of two line",
            "Overdue - Payment due date: 3 June 2023",
            list
        )

        binding.ccvMFS.setData(data)

    }

    override fun onClick(view: View?) {

        when (view?.id) {

            R.id.clRootView -> {

                val position = view.tag as Int

                when (productList[position].gradColor) {
                    "blue" -> binding.clBanner.setBackgroundResource(R.drawable.banner_gradient_blue)
                    "orange" -> binding.clBanner.setBackgroundResource(R.drawable.banner_gradient_orange)
                    "yellow" -> binding.clBanner.setBackgroundResource(R.drawable.banner_gradient_yellow)
                }

                adapter?.getItem()?.get(position)?.isSelected = true
                adapter?.getItem()?.get(selectedPosition)?.isSelected = false

                adapter?.notifyItemChanged(position)
                adapter?.notifyItemChanged(selectedPosition)

                selectedPosition = position

            }

        }

    }

}