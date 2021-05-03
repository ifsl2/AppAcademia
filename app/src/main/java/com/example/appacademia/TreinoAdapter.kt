package com.example.appacademia

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appacademia.databinding.LinhaBinding
import com.example.appacademia.databinding.LinhaTreinoBinding

class TreinoAdapter(private val treinos: ArrayList<Treino>,
                    private val inflater: LayoutInflater
) : RecyclerView.Adapter<TreinoViewHolder>() {
    override fun getItemCount(): Int = treinos.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TreinoViewHolder {
        val binding = LinhaTreinoBinding.inflate(inflater, parent, false)
        return TreinoViewHolder(binding, parent.context as Activity)
    }

    override fun onBindViewHolder(holder: TreinoViewHolder, position: Int) {
        holder.bindTo(treinos[position])
    }

}
