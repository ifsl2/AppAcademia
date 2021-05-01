package com.example.appacademia

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appacademia.databinding.LinhaAlunoBinding
import com.example.appacademia.databinding.LinhaBinding

class AlunosAdapter(private val alunos: ArrayList<Alunos>,
                    private val inflater: LayoutInflater
) : RecyclerView.Adapter<AlunoViewHolder>() {
    override fun getItemCount(): Int = alunos.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlunoViewHolder {
        val binding = LinhaAlunoBinding.inflate(inflater, parent, false)
        return AlunoViewHolder(binding, parent.context as Activity)
    }

    override fun onBindViewHolder(holder: AlunoViewHolder, position: Int) {
        holder.bindTo(alunos[position])
    }

}
