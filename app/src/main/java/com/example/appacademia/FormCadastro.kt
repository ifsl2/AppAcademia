package com.example.appacademia

import android.content.Intent
import android.os.Bundle
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import com.example.appacademia.databinding.ActivityFormCadastroBinding

class FormCadastro : AppCompatActivity() {

    internal lateinit var db:DBHelper

    val shPrefClass: SharedPreferencesClass = SharedPreferencesClass()

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
            var nome = binding.editNome.text.toString()
            var telefone = binding.editTelefone.text.toString()
            var professor = binding.rbProfessor.isChecked
            var aluno = binding.rbAluno.isChecked
            var msgErro = binding.mensagemErro
            if(email.isEmpty() || senha.isEmpty() || nome.isEmpty() || telefone.isEmpty() ||(!professor && !aluno)){
                msgErro.text = "Preencha todos os campos!"
            }else{
                CadastrarUsuario()
            }
        }
    }

    private fun CadastrarUsuario(){

        var email = binding.editEmail.text.toString()
        var senha = binding.editSenha.text.toString()
        var nome = binding.editNome.text.toString()
        var telefone = binding.editTelefone.text.toString()
        var rbGroup = binding.rbGroup
        var msgErro = binding.mensagemErro

        val radioButton: RadioButton = findViewById(rbGroup.checkedRadioButtonId)

        val usuario = Usuarios(binding.editEmail.text.toString(), binding.editSenha.text.toString(), radioButton.text.toString(), nome, telefone)

        db.adicionarUsuario(usuario, this, msgErro)

        shPrefClass.adicionarUsuario(this, usuario)
        redirectLista()
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