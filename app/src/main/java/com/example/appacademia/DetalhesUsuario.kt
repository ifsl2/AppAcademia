package com.example.appacademia

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.appacademia.databinding.ActivityCriarAtividadeBinding
import com.example.appacademia.databinding.ActivityDetalhesUsuarioBinding

class DetalhesUsuario : AppCompatActivity() {

    private lateinit var  binding: ActivityDetalhesUsuarioBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetalhesUsuarioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState?.isEmpty != true) {
            binding.nomeAluno.text = savedInstanceState?.get("NOME_ALUNO").toString()
            binding.idadeAluno.text = savedInstanceState?.get("IDADE_ALUNO").toString()
            binding.telefoneAluno.text = savedInstanceState?.get("TELEFONE_ALUNO").toString()
        } else {
            Toast.makeText(this, "Nao foi possivel recuperar o usuario!", Toast.LENGTH_LONG).show()
            irMenuProfessor()
        }


        binding.voltar.setOnClickListener {
            irMenuProfessor()
        }

        binding.addAtividade.setOnClickListener {
            val intent = Intent(this, ListaAtividade::class.java)
            intent.putExtra("TIPO", 1)
            startActivity(intent)
            finish()
        }
    }

    fun irMenuProfessor() {
        val intent = Intent(this, MenuProfessor::class.java)
        startActivity(intent)
        finish()
    }
}