package com.azhar.pahlawannasional.main

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.azhar.pahlawannasional.R
import com.azhar.pahlawannasional.detail.DetailActivity
import com.azhar.pahlawannasional.main.MainAdapter.MainViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import kotlinx.android.synthetic.main.list_nama_pahlawan.view.*

/**
 * Created by Azhar Rivaldi on 02-02-2022
 * Youtube Channel : https://bit.ly/2PJMowZ
 * Github : https://github.com/AzharRivaldi
 * Twitter : https://twitter.com/azharrvldi_
 * Instagram : https://www.instagram.com/azhardvls_
 * Linkedin : https://www.linkedin.com/in/azhar-rivaldi
 */

class MainAdapter(
    var context: Context,
    var modelMainList: MutableList<ModelMain>) : RecyclerView.Adapter<MainViewHolder>(), Filterable {

    var modelMainFilterList: List<ModelMain> = ArrayList(modelMainList)

    override fun getFilter(): Filter {
        return modelFilter
    }

    private val modelFilter: Filter = object : Filter() {
        override fun performFiltering(constraint: CharSequence): FilterResults {
            val filteredList: MutableList<ModelMain> = ArrayList()
            if (constraint == null || constraint.length == 0) {
                filteredList.addAll(modelMainFilterList)
            } else {
                val filterPattern = constraint.toString().lowercase()
                for (modelMainFilter in modelMainFilterList) {
                    if (modelMainFilter.nama.lowercase().contains(filterPattern) ||
                        modelMainFilter.namaLengkap.lowercase().contains(filterPattern)
                    ) {
                        filteredList.add(modelMainFilter)
                    }
                }
            }
            val results = FilterResults()
            results.values = filteredList
            return results
        }

        override fun publishResults(constraint: CharSequence, results: FilterResults) {
            modelMainList.clear()
            modelMainList.addAll(results.values as List<ModelMain>)
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_nama_pahlawan, parent, false)
        return MainViewHolder(view)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val data = modelMainList[position]

        holder.tvNamaPahlawan.text = data.nama
        holder.tvNamaLengkap.text = data.namaLengkap
        holder.tvKategori.text = data.kategori

        Glide.with(context)
            .load(data.image)
            .transform(CenterCrop(), RoundedCorners(25))
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(holder.imagePahlawan)

        //send data to detail activity
        holder.cvListMain.setOnClickListener { view: View? ->
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(DetailActivity.DETAIL_PAHLAWAN, modelMainList[position])
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return modelMainList.size
    }

    class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var cvListMain: CardView
        var tvNamaPahlawan: TextView
        var tvNamaLengkap: TextView
        var tvKategori: TextView
        var imagePahlawan: ImageView

        init {
            cvListMain = itemView.cvListMain
            tvNamaPahlawan = itemView.tvNamaPahlawan
            tvNamaLengkap = itemView.tvNamaLengkap
            tvKategori = itemView.tvKategori
            imagePahlawan = itemView.imagePahlawan
        }
    }

}