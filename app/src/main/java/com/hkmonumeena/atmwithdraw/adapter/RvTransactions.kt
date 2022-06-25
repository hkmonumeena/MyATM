package com.hkmonumeena.atmwithdraw.adapter

import com.hkmonumeena.atmwithdraw.R
import com.hkmonumeena.atmwithdraw.dataclass.Transactions
import com.hkmonumeena.atmwithdraw.helper.Craft.textView
import com.hkmonumeena.atmwithdraw.helper.GenericAdapter
import com.hkmonumeena.atmwithdraw.helper.ViewHolder

class RvTransactions(items: ArrayList<Transactions>) : GenericAdapter<Transactions>(items) {
    override fun configure(item: Transactions, holder: ViewHolder, position: Int) {
        with(holder.itemView){
            textView(R.id.textViewTotalAmount).text = item.totalAmount.toString()
            textView(R.id.textViewHundredRupeeCount).text = item.rupee100.toString()
            textView(R.id.textViewTwoHundredRupeeCount).text = item.rupee200.toString()
            textView(R.id.textViewFiveHundredRupeeCount).text = item.rupee500.toString()
            textView(R.id.textViewTwoThousandRupeeCount).text = item.rupee2000.toString()
        }
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.rv_transactions
    }
}