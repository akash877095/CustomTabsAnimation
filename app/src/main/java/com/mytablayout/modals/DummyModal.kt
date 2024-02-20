package com.mytablayout.modals

data class MFSModal(
    val image: Int,
    val title: String,
    val description: String,
    val pay_due_text: String,
    var list: ArrayList<TotalPriceData>
)

data class TotalPriceData(
    val title: String,
    val value: String
)