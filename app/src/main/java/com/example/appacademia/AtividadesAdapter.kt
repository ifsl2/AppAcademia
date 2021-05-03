package com.example.appacademia

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appacademia.databinding.LinhaBinding

class AtividadesAdapter(private val atividades: ArrayList<Atividades>,
                        private val inflater: LayoutInflater
) : RecyclerView.Adapter<AtividadeViewHolder>() {
    override fun getItemCount(): Int = atividades.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AtividadeViewHolder {
        val binding = LinhaBinding.inflate(inflater, parent, false)
        return AtividadeViewHolder(binding, parent.context as Activity)
    }

    override fun onBindViewHolder(holder: AtividadeViewHolder, position: Int) {
        holder.bindTo(atividades[position])
    }

}
