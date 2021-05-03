package com.example.appacademia

import android.content.Intent
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appacademia.databinding.ActivityListaAlunosBinding
import com.example.appacademia.databinding.ActivityMenuAlunoBinding
import com.google.firebase.auth.FirebaseAuth
import java.lang.Exception

class ListaAlunos : AppCompatActivity() {
    internal lateinit var db:DBHelper
    private var tipo: String = ""
    private var codUsuario: Long = 0
    private lateinit var binding: ActivityListaAlunosBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListaAlunosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (intent.extras != null) {
            tipo = intent.extras!!.getString("TIPO_USUARIO").toString()
            codUsuario = intent.extras!!.getLong("COD_USUARIO")
        }

        db = DBHelper(this)

        binding.voltar.setOnClickListener {
            var intent: Intent? = null
            intent = if (tipo == "PROFESSOR") {
                Intent(this, MenuProfessor::class.java)
            } else {
                Intent(this, MenuAluno::class.java)
            }
            startActivity(intent)
            finish()
        }

        var result = readDataFunction()

        var recyclerViewLista = binding.recViewAlunos

        recyclerViewLista.apply {
            layoutManager = LinearLayoutManager(this@ListaAlunos)
            addItemDecoration(DividerItemDecoration(this@ListaAlunos, DividerItemDecoration.VERTICAL))
            adapter = AlunosAdapter(result, layoutInflater)
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

    private fun readDataFunction() : ArrayList<Alunos>{
        val alunos: ArrayList<Alunos> = ArrayList()
        try {
            var data: Cursor = db.readProfessores()
            if (tipo == "PROFESSOR") {
                data = db.readAlunosProfessor(codUsuario.toString())
            }


            if (data != null && data.count > 0) {
                if (data.moveToFirst()) {
                    do {
                        val al = Alunos(data.getInt(0), data.getString(1), data.getInt(5), data.getString(4))
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



