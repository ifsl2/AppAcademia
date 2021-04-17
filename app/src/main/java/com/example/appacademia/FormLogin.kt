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

    internal lateinit var db:DBHelper

    private lateinit var binding: ActivityFormLoginBinding

    val shPrefClass: SharedPreferencesClass = SharedPreferencesClass()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = DBHelper(this)

        supportActionBar!!.hide()
        VerificaUserLogado()

        binding.txtTelaCadastro.setOnClickListener {
            val intent = Intent(this, FormCadastro::class.java)
            startActivity(intent)
        }

        binding.btEntrar.setOnClickListener {
            val email = binding.editEmail.text.toString()
            val senha = binding.editSenha.text.toString()
            val msgErro = binding.mensagemErro
            if(email.isEmpty() || senha.isEmpty()){
                msgErro.text = "Preencha todos os Campos!"
            }else{
                AuntenticarUsuario(email, senha)
            }
        }
    }


    private fun AuntenticarUsuario(email:String, senha:String){
        val msgErro = binding.mensagemErro

        val usuario = db.recuperarUsuario(email)
        usuario.let {
            msgErro.text = usuario.toString()
            if (usuario != null) {
                if (usuario.usuario == email && usuario.senha == MD5(senha)) {
                    shPrefClass.adicionarUsuario(this, usuario)
                    RedirectLista()
                } else {
                    Toast.makeText(this, "Usuario ou senha não coincidem!", Toast.LENGTH_SHORT).show()
                }

            } else {
                Toast.makeText(this, "Usuário não existe!", Toast.LENGTH_SHORT).show()
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