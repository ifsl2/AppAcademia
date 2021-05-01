package com.example.appacademia

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.appacademia.databinding.LinhaAlunoBinding
import com.example.appacademia.databinding.LinhaBinding

class AlunoViewHolder(private val binding: LinhaAlunoBinding, private val context: Activity) : RecyclerView.ViewHolder(binding.root) {
    var id : Int = 0
    var nome : String = "NOME"
    var idade : Int = 0
    var telefone : String = "TELEFONE"

    init {
        var db = DBHelper(context)
        var tipoAcao = 0
        var codAluno: Long = 0
        if (context.intent.extras != null) {
            tipoAcao = context.intent.extras!!.getInt("VIEWHOLDER_ACAO")
            codAluno = context.intent.extras!!.getLong("COD_ALUNO")
        }
        binding.root.setOnClickListener {
            val c = binding.id.context

            if (tipoAcao == 100) {
                var result = db.adicionarProfessorAluno(codAluno.toString(), id.toString(), context)
                if (result > 0) {
                    Toast.makeText(context, "Voce escolheu o Professor $nome", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(context, "Erro ao escolher o professor", Toast.LENGTH_LONG).show()
                }
            }

            /*
            //Intent Explicito
            val intentExplicito = Intent(c, ProfessorActivity::class.java)
            intentExplicito.putExtra("nome", nome)
            intentExplicito.putExtra("login", login)
            //c.startActivity(intentExplicito)

            val intentImplicito = Intent()
            intentImplicito.action = Intent.ACTION_VIEW
            intentImplicito.data = Uri.parse(site)//https://
            */

            //intentImplicito.data = Uri.parse("geo:0,0?q=Marco Zero")

            //c.startActivity(intentImplicito)
        }
    }
    fun bindTo(aluno : Alunos) {
        id = aluno.id
        nome = aluno.nome
        idade = aluno.idade
        telefone = aluno.telefone

        binding.id.text = aluno.id.toString()
        binding.nome.text = aluno.nome
        binding.idade.text = aluno.idade.toString()
        binding.telefone.text = aluno.telefone.toString()
    }
}
