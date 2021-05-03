package com.example.appacademia

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.appacademia.databinding.LinhaBinding

class AtividadeViewHolder(private val binding: LinhaBinding, private val context: Activity) : RecyclerView.ViewHolder(binding.root) {
    var id : Int = 0
    var nome : String = "NOME"
    var descricao : String = "DESCRICAO"
    var repeticoes: Int = 0
    var diasSemana: String = ""


    init {
        var tipoAcao = 0
        var codProfessor: Long = 0
        if (context.intent.extras != null) {
            tipoAcao = context.intent.extras!!.getInt("VIEWHOLDER_ACAO")
            codProfessor = context.intent.extras!!.getLong("COD_USUARIO")
        }
        binding.root.setOnClickListener {
            if (tipoAcao == 101) {
                val selecionarAluno = Intent(context, CriarTreino::class.java)
                selecionarAluno.putExtra("TIPO_USUARIO", "PROFESSOR")
                selecionarAluno.putExtra("COD_USUARIO", codProfessor)
                selecionarAluno.putExtra("COD_ATIVIDADE", id)
                selecionarAluno.putExtra("VIEWHOLDER_ACAO", 101)

                context.startActivity(selecionarAluno)
            }
        }

    }
    fun bindTo(ativ : Atividades) {
        id = ativ.id
        nome = ativ.nome
        descricao = ativ.descricao

        binding.nome.text = "Nome: ${ativ.nome}"
        binding.descricao.text = "Descrição: ${ativ.descricao}"
    }
}
