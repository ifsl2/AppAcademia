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

        binding.rbAluno.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked) {
                binding.btCadastrar.text = resources.getString(R.string.Proximo)
            } else {
                binding.btCadastrar.text = resources.getString(R.string.Cadastrar)
            }
        }

        binding.btCadastrar.setOnClickListener {
            var email = binding.editEmail.text.toString()
            var senha = binding.editSenha.text.toString()
            var nome = binding.editNome.text.toString()
            var idade = binding.editIdade.text.toString()
            var telefone = binding.editTelefone.text.toString()
            var professor = binding.rbProfessor.isChecked
            var aluno = binding.rbAluno.isChecked
            var msgErro = binding.mensagemErro
            if(email.isEmpty() || senha.isEmpty() || nome.isEmpty() || telefone.isEmpty() || idade.isEmpty() || (!professor && !aluno)){
                msgErro.text = "Preencha todos os campos!"
            } else {
                CadastrarUsuario()
            }
        }
    }

    private fun proximoCadastro(codAluno: Long){

        var email = binding.editEmail.text.toString()
        var senha = binding.editSenha.text.toString()
        var nome = binding.editNome.text.toString()
        var idade = binding.editIdade.text.toString()
        var telefone = binding.editTelefone.text.toString()
        var rbGroup = binding.rbGroup
        val radioButton: RadioButton = findViewById(rbGroup.checkedRadioButtonId)

        val intent = Intent(this, FormCadastro2::class.java)
        intent.putExtra("VIEWHOLDER_ACAO", 100)
        intent.putExtra("COD_USUARIO", codAluno)
        startActivity(intent)
        finish()
    }

    private fun CadastrarUsuario(){

        var email = binding.editEmail.text.toString()
        var senha = binding.editSenha.text.toString()
        var nome = binding.editNome.text.toString()
        var idade = binding.editIdade.text.toString().toInt()
        var telefone = binding.editTelefone.text.toString()
        var rbGroup = binding.rbGroup
        var msgErro = binding.mensagemErro

        val radioButton: RadioButton = findViewById(rbGroup.checkedRadioButtonId)

        val usuario = Usuarios(binding.editEmail.text.toString(), binding.editSenha.text.toString(), radioButton.text.toString(), nome, telefone, idade)

        var rowID = db.adicionarUsuario(usuario, this)

        if (rowID == (-1).toLong()) { return }

        if (radioButton.text.toString() == "Professor") {
            RedirectListaProfessor(rowID)
        } else {
            proximoCadastro(rowID)
            //redirectLista(rowID)
        }
    }

    private fun toobar(){
        var toolbar = binding.toolbarCadastro
        toolbar.setBackgroundColor(getColor(R.color.white))
        toolbar.setNavigationIcon(getDrawable(R.drawable.ic_halteres))
    }

    private fun redirectLista(codAluno: Long){
        val intent = Intent(this, MenuAluno::class.java)
        intent.putExtra("CODIGO_ALUNO", codAluno)
        startActivity(intent)
        finish()
    }

    private fun RedirectListaProfessor(codProfessor: Long){
        val intent = Intent(this, MenuProfessor::class.java)
        intent.putExtra("CODIGO_PROFESSOR", codProfessor)
        startActivity(intent)
        finish()
    }
}