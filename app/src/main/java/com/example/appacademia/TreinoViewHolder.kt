package com.example.appacademia

import android.app.Activity
import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import com.example.appacademia.databinding.LinhaTreinoBinding

class TreinoViewHolder(private val binding: LinhaTreinoBinding, private val context: Activity) : RecyclerView.ViewHolder(binding.root) {
    var id: Int = 0
    var nome: String = "NOME"
    var descricao: String = "DESCRICAO"
    var repeticoes: Int = 0
    var diasSemana: String = ""


    init {
        binding.root.setOnClickListener {
        }
    }

    fun bindTo(treino: Treino) {
        id = treino.id
        nome = treino.nome
        descricao = treino.descricao
        repeticoes = treino.repeticoes
        diasSemana = treino.diasSemanas

        binding.nome.text = "Nome: ${treino.nome}"
        binding.descricao.text = "Descrição: ${treino.descricao}"
        binding.atvRepeticoes.text = "Repetições: ${treino.repeticoes}"
        binding.diasSemana.text = "Dias: ${treino.diasSemanas}"
    }
}
