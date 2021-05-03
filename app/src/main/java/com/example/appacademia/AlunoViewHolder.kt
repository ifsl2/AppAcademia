package com.example.appacademia

import android.app.Activity
import android.content.Intent
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.appacademia.databinding.LinhaAlunoBinding

class AlunoViewHolder(private val binding: LinhaAlunoBinding, private val context: Activity) : RecyclerView.ViewHolder(binding.root) {
    var id : Int = 0
    var nome : String = "NOME"
    var email : String = "EMAIL"
    var idade : Int = 0
    var telefone : String = "TELEFONE"

    init {
        var db = DBHelper(context)
        var tipoAcao = 0
        var tipo: String = ""
        var codUsuario: Long = 0
        var repeticoesExtra: Int = 0
        var diasSemanas: ArrayList<String> = ArrayList()
        var codAtividade: Int = 0
        if (context.intent.extras != null) {
            tipoAcao = context.intent.extras!!.getInt("VIEWHOLDER_ACAO")
            context.intent.extras!!.getString("TIPO_USUARIO").let {
                if (it != null) {
                    tipo = it
                }
            }
            codUsuario = context.intent.extras!!.getLong("COD_USUARIO")
            codAtividade = context.intent.extras!!.getInt("COD_ATIVIDADE")
            repeticoesExtra = context.intent.extras!!.getInt("REPETICOES")
            context.intent.extras!!.getStringArrayList("DIAS").let {
                if (it != null) {
                    diasSemanas = it
                }
            }
        }
        binding.root.setOnClickListener {

            if (tipoAcao == 100) {
                var result = db.adicionarProfessorAluno(codUsuario.toString(), id.toString(), context)
                if (result > 0) {
                    Toast.makeText(context, "Voce escolheu o Professor $nome", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(context, "Erro ao escolher o professor", Toast.LENGTH_LONG).show()
                }
            } else if (tipoAcao == 101) {
                var result = db.adicionarAtividadeAluno(repeticoesExtra, diasSemanas.toString(), codAtividade, id.toLong(), context)
                if (result != (-1).toLong()) {
                    Toast.makeText(context, "Atividade adicionada para o aluno com sucesso", Toast.LENGTH_SHORT).show()
                    val menuProf = Intent(context, MenuProfessor::class.java)
                    menuProf.putExtra("TIPO_USUARIO", "PROFESSOR")
                    menuProf.putExtra("COD_USUARIO", codUsuario)
                    context.startActivity(menuProf)
                } else {
                    Toast.makeText(context, "Falha ao adicionar atividade ao aluno", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    fun bindTo(aluno : Alunos) {
        id = aluno.id
        nome = aluno.nome
        email = aluno.email
        idade = aluno.idade
        telefone = aluno.telefone

        binding.nome.text = "Nome: ${aluno.nome}"
        binding.email.text = "Email: ${aluno.email}"
        binding.idade.text = "Idade: ${aluno.idade}"
        binding.telefone.text = "Telefone: ${aluno.telefone}"
    }
}
