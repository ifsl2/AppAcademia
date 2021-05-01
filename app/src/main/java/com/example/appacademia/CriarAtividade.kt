package com.example.appacademia

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RadioButton
import com.example.appacademia.databinding.ActivityCriarAtividadeBinding

class CriarAtividade : AppCompatActivity() {
    internal lateinit var db:DBHelper
    val shPrefClass: SharedPreferencesClass = SharedPreferencesClass()
    private lateinit var  binding: ActivityCriarAtividadeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCriarAtividadeBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
            startActivity(intent)
            finish()
        }
    }

    private fun CadastrarAtividade(){

        var nome = binding.editNome.text.toString()
        var descricao = binding.editDescricao.text.toString()
        var msgErro = binding.mensagemErro

        db.adicionarAtividade(nome,descricao, this)

    }
}