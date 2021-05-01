package com.example.appacademia

import android.content.Intent
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import com.example.appacademia.databinding.ActivityListaAlunosBinding
import com.example.appacademia.databinding.ActivityMenuAlunoBinding
import com.google.firebase.auth.FirebaseAuth
import java.lang.Exception

class ListaAlunos : AppCompatActivity() {
    internal lateinit var db:DBHelper
    lateinit var result : TextView
    private lateinit var binding: ActivityListaAlunosBinding
    private var shPref: SharedPreferencesClass = SharedPreferencesClass()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListaAlunosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = DBHelper(this)
        result = binding.resultText

        binding.voltar.setOnClickListener {
            var intent: Intent? = null
            var tipo = shPref.recuperarUsuario(this)["tipo"]
            intent = if (tipo == "Professor") {
                Intent(this, MenuProfessor::class.java)
            } else {
                Intent(this, MenuAluno::class.java)
            }
            startActivity(intent)
            finish()
        }

        readDataFunction()

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

    private fun readDataFunction() : ArrayList<Alunos>{
        val alunos: ArrayList<Alunos> = ArrayList()
        try {
            val data: Cursor = db.readAlunos()

            if (data != null && data.count > 0) {
                if (data.moveToFirst()) {
                    do {
                        val al = Alunos(data.getString(0).toInt(), data.getString(1), 0)
                        alunos.add(al)
                    } while (data.moveToNext())
                }
                Toast.makeText(applicationContext, "Data carregada!", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(applicationContext, "No Data", Toast.LENGTH_LONG).show()
            }
        }
        catch (ex: Exception) {
            Toast.makeText(applicationContext, ex.toString(), Toast.LENGTH_LONG).show()
        }

        return alunos
    }



    private fun RedirectLogin(){
        val intent = Intent(this, FormLogin::class.java)
        startActivity(intent)
        finish()
    }
}


