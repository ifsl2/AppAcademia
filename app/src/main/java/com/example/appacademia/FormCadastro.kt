package com.example.appacademia

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.appacademia.databinding.ActivityFormCadastroBinding
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException

class FormCadastro : AppCompatActivity() {

     private lateinit var  binding: ActivityFormCadastroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormCadastroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar!!.hide()
        toobar()

        binding.btCadastrar.setOnClickListener {
            var email = binding.editEmail.text.toString()
            var senha = binding.editSenha.text.toString()
            var msg_erro = binding.mensagemErro
            if(email.isEmpty() || senha.isEmpty()){
                msg_erro.setText("Preencha todos os campos!")
            }else{
                CadastrarUsuario()
            }
        }

    }

    private fun CadastrarUsuario(){

        var email = binding.editEmail.text.toString()
        var senha = binding.editSenha.text.toString()
        var msg_erro = binding.mensagemErro

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, senha).addOnCompleteListener {
            if(it.isSuccessful){
                Toast.makeText(this, "Usuário cadastrado com sucesso", Toast.LENGTH_SHORT).show()
                binding.editEmail.setText("")
                binding.editSenha.setText("")
                msg_erro.setText("")
                RedirectLista()
            }
        }.addOnFailureListener {

            var erro = it

            when{
                erro is FirebaseAuthWeakPasswordException -> msg_erro.setText("Digite uma senha com seis ou mais caractéres")
                erro is FirebaseAuthUserCollisionException -> msg_erro.setText("E-mail Cadastrado")
                erro is FirebaseNetworkException -> msg_erro.setText("Sem conexão a internet")
                else -> msg_erro.setText("Erro ao Cadastrar!")
            }

        }
    }

    private fun toobar(){
        var toolbar = binding.toolbarCadastro
        toolbar.setBackgroundColor(getColor(R.color.white))
        toolbar.setNavigationIcon(getDrawable(R.drawable.ic_halteres))
    }

    private fun RedirectLista(){
        val intent = Intent(this, Lista::class.java)
        startActivity(intent)
        finish()
    }
}