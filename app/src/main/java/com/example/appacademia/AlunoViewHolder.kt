package com.example.appacademia

import androidx.recyclerview.widget.RecyclerView
import com.example.appacademia.databinding.LinhaBinding

class AlunoViewHolder(private val binding: LinhaBinding) : RecyclerView.ViewHolder(binding.root) {
    var id : Int = 0
    var nome : String = "NOME"
    var idade : Int = 0

    init {
        binding.root.setOnClickListener {
            val c = binding.id.context

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

        binding.id.text = aluno.id.toString()
        binding.nome.text= aluno.nome
        binding.descricao.text= aluno.idade.toString()
    }
}
