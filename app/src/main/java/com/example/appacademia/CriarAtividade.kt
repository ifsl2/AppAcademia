package com.example.appacademia

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.appacademia.databinding.ActivityCriarAtividadeBinding

class CriarAtividade : AppCompatActivity() {
    internal lateinit var db:DBHelper
    private lateinit var  binding: ActivityCriarAtividadeBinding
    private var codProfessor: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCriarAtividadeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (intent.extras != null)
        {
            codProfessor = intent.extras!!.getLong("COD_USUARIO")
        }

        db = DBHelper(this)

        supportActionBar!!.hide()
        binding.btCadastrar.setOnClickListener {
            var nome = binding.editNome.text.toString()
            var descricao = binding.editDescricao.text.toString()
            var msgErro = binding.mensagemErro
            if( nome.isEmpty() || descricao.isEmpty() ){
                msgErro.text = "Preencha todos os campos!"
            }else{
                CadastrarAtividade()
            }
        }

        binding.voltar.setOnClickListener{
            val intent = Intent(this, MenuProfessor::class.java)
            intent.putExtra("COD_USUARIO", codProfessor)
            startActivity(intent)
            finish()
        }
    }

    private fun CadastrarAtividade(){

        var nome = binding.editNome.text.toString()
        var descricao = binding.editDescricao.text.toString()
        var msgErro = binding.mensagemErro

        db.adicionarAtividade(nome, descricao, codProfessor.toString(), this)
        binding.editNome.setText("")
        binding.editDescricao.setText("")
    }
}