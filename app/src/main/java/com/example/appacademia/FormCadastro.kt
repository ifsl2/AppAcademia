package com.example.appacademia

import android.content.Intent
import android.os.Bundle
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import com.example.appacademia.databinding.ActivityFormCadastroBinding

class FormCadastro : AppCompatActivity() {

    internal lateinit var db:DBHelper

    private lateinit var  binding: ActivityFormCadastroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormCadastroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = DBHelper(this)

        supportActionBar!!.hide()
        toobar()

        binding.btCadastrar.setOnClickListener {
            var email = binding.editEmail.text.toString()
            var senha = binding.editSenha.text.toString()
            var professor = binding.rbProfessor.isChecked
            var aluno = binding.rbAluno.isChecked
            var msgErro = binding.mensagemErro
            if(email.isEmpty() || senha.isEmpty() || (!professor && !aluno)){
                msgErro.text = "Preencha todos os campos!"
            }else{
                CadastrarUsuario()
            }
        }
    }

    private fun CadastrarUsuario(){

        var email = binding.editEmail.text.toString()
        var senha = binding.editSenha.text.toString()
        var rbGroup = binding.rbGroup
        var msgErro = binding.mensagemErro

        val radioButton: RadioButton = findViewById(rbGroup.checkedRadioButtonId)

        val usuario = Usuarios(binding.editEmail.text.toString(), binding.editSenha.text.toString(), radioButton.text.toString())

        db.adicionarUsuario(usuario, this, msgErro)

        //redirectLista()
    }

    private fun toobar(){
        var toolbar = binding.toolbarCadastro
        toolbar.setBackgroundColor(getColor(R.color.white))
        toolbar.setNavigationIcon(getDrawable(R.drawable.ic_halteres))
    }

    private fun redirectLista(){
        val intent = Intent(this, Lista::class.java)
        startActivity(intent)
        finish()
    }
}