package com.example.appacademia

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import com.example.appacademia.databinding.ActivityMenuAlunoBinding
import com.google.firebase.auth.FirebaseAuth

class MenuAluno : AppCompatActivity() {
    private lateinit var binding: ActivityMenuAlunoBinding
    private var codAluno: Long = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuAlunoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (intent.extras != null)
        {
            codAluno = intent.extras!!.getLong("COD_ALUNO")
        }

        var btn = binding.listaUser
        btn.setOnClickListener {
            RedirectListUser()
        }

    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflate = menuInflater
        inflate.inflate(R.menu.menu_principal, menu)

        return true
    }

    private fun RedirectListUser(){
        val intent = Intent(this, ListaAlunos::class.java)
        intent.putExtra("TIPO_USUARIO", "ALUNO")
        intent.putExtra("COD_USUARIO", codAluno)
        startActivity(intent)
        finish()
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
}


