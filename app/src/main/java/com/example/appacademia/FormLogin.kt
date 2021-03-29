package com.example.appacademia

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import com.example.appacademia.databinding.ActivityFormLoginBinding
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException

class FormLogin : AppCompatActivity() {

    private lateinit var binding: ActivityFormLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar!!.hide()
        VerificaUserLogado()

        binding.txtTelaCadastro.setOnClickListener {
            val intent = Intent(this, FormCadastro::class.java)
            startActivity(intent)
        }

        binding.btEntrar.setOnClickListener {
            val email = binding.editEmail.text.toString()
            val senha = binding.editSenha.text.toString()
            val msg_erro = binding.mensagemErro
            if(email.isEmpty() || senha.isEmpty()){
                msg_erro.setText("Preencha todos os Campos!")
            }else{
                AuntenticarUser()
            }
        }
    }


    private fun AuntenticarUser(){
        val email = binding.editEmail.text.toString()
        val senha = binding.editSenha.text.toString()
        val msg_erro = binding.mensagemErro

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, senha).addOnCompleteListener {
            if(it.isSuccessful){
                Toast.makeText(this, "Login Efetuado com Sucesso", Toast.LENGTH_SHORT).show()
                RedirectLista()
            }
        }.addOnFailureListener {
            var erro = it

            when{
                erro is FirebaseAuthInvalidCredentialsException -> msg_erro.setText("E-mail ou Senha estão incorretos")
                erro is FirebaseNetworkException -> msg_erro.setText("Sem conexão a internet")
                else -> msg_erro.setText("Erro ao Cadastrar!")
            }
        }
    }

    private fun VerificaUserLogado(){
        val usuario = FirebaseAuth.getInstance().currentUser

        if(usuario != null){
            RedirectLista()
        }
    }

    private fun RedirectLista(){
        val intent = Intent(this, Lista::class.java)
        startActivity(intent)
        finish()
    }
}