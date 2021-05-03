package com.example.appacademia

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = DBHelper(this)

        supportActionBar!!.hide()

        binding.txtTelaCadastro.setOnClickListener {
            val intent = Intent(this, FormCadastro::class.java)
            startActivity(intent)
        }

        binding.btEntrar.setOnClickListener {
            try {
                val email = binding.editEmail.text.toString()
                val senha = binding.editSenha.text.toString()
                val msgErro = binding.mensagemErro
                if (email.isEmpty() || senha.isEmpty()) {
                    msgErro.text = "Preencha todos os Campos!"
                } else {
                    AuntenticarUsuario(email, senha)
                }
            } catch (ex: Exception) {
                Toast.makeText(applicationContext, ex.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun AuntenticarUsuario(email:String, senha:String){
        val usuario = db.recuperarUsuario(email)
        usuario.let {
            if (usuario != null) {
                if (usuario.usuario == email && usuario.senha == MD5(senha)) {
                    if(usuario.categoria == "Professor"){
                        RedirectListaProfessor(usuario.codigo)
                    }else{
                        RedirectLista(usuario.codigo)
                    }
                } else {
                    Toast.makeText(this, "Usuario ou senha não coincidem!", Toast.LENGTH_SHORT).show()
                }

            } else {
                Toast.makeText(this, "Usuário não existe!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun RedirectLista(codAluno: Long){
        val intent = Intent(this, MenuAluno::class.java)
        intent.putExtra("COD_USUARIO", codAluno)
        startActivity(intent)
        finish()
    }

    private fun RedirectListaProfessor(codProfessor: Long){
        val intent = Intent(this, MenuProfessor::class.java)
        intent.putExtra("COD_USUARIO", codProfessor)
        startActivity(intent)
        finish()
    }
}