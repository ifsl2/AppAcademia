package com.example.appacademia

import android.app.Activity
import android.graphics.Color
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.appacademia.databinding.LinhaBinding

class AtividadeViewHolder(private val binding: LinhaBinding, private val context:Activity) : RecyclerView.ViewHolder(binding.root) {
    var id : Int = 0
    var nome : String = "NOME"
    var descricao : String = "DESCRICAO"


    init {
        var tipoAcao = 0
        var codProfessor: Long = 0
        if (context.intent.extras != null) {
            tipoAcao = context.intent.extras!!.getInt("VIEWHOLDER_ACAO")
            codProfessor = context.intent.extras!!.getLong("COD_PROFESSOR")
        }
        var ids: IntArray = intArrayOf()
        binding.root.setOnClickListener {
            val c = binding.id.context


            if (tipoAcao == 101) {
                //if(it.background)

                Toast.makeText(context, it.solidColor.toString(), Toast.LENGTH_LONG).show()
                it.setBackgroundColor(Color.RED)

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
    fun bindTo(ativ : Atividades) {
        id = ativ.id
        nome = ativ.nome
        descricao = ativ.descricao

        binding.id.text = ativ.id.toString()
        binding.nome.text= ativ.nome
        binding.descricao.text= ativ.descricao
    }
}
