package com.example.appacademia

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.appacademia.databinding.ActivityMenuProfessorBinding
import com.google.firebase.auth.FirebaseAuth

class MenuProfessor : AppCompatActivity() {
    private lateinit var binding: ActivityMenuProfessorBinding
    private var codProfessor: Long = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuProfessorBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (intent.extras != null)
        {
            codProfessor = intent.extras!!.getLong("COD_USUARIO")
        }


        var btn = binding.listaUser
        var btnAtividade = binding.atividade
        var btnAtividades = binding.atividades
        var btnTreino = binding.treino

        btn.setOnClickListener {
            RedirectListUser()
        }
        btnAtividades.setOnClickListener {
            val intent = Intent(this, ListaAtividade::class.java)
            intent.putExtra("COD_USUARIO", codProfessor)
            startActivity(intent)
            finish()
        }
        btnAtividade.setOnClickListener {
            val intent = Intent(this, CriarAtividade::class.java)
            intent.putExtra("COD_USUARIO", codProfessor)
            startActivity(intent)
            finish()
        }
        btnTreino.setOnClickListener {
            val intent = Intent(this, ListaAtividade::class.java)
            intent.putExtra("COD_USUARIO", codProfessor)
            intent.putExtra("VIEWHOLDER_ACAO", 101)
            startActivity(intent)
            finish()
        }



    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflate = menuInflater
        inflate.inflate(R.menu.menu_principal, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.deslogar -> {
                FirebaseAuth.getInstance().signOut()
                RedirectLogin()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun RedirectLogin(){
        val intent = Intent(this, FormLogin::class.java)
        startActivity(intent)
        finish()
    }

    private fun RedirectListUser(){
        val intent = Intent(this, ListaAlunos::class.java)
        intent.putExtra("COD_USUARIO", codProfessor)
        intent.putExtra("TIPO_USUARIO", "PROFESSOR")
        startActivity(intent)
        finish()
    }
}


