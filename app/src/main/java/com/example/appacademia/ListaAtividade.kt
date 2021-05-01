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
import androidx.recyclerview.widget.RecyclerView
import com.example.appacademia.databinding.ActivityListaAtividadeBinding
import com.google.firebase.auth.FirebaseAuth
import java.lang.Exception
import kotlin.properties.Delegates

class ListaAtividade : AppCompatActivity() {
    internal lateinit var db:DBHelper
    private lateinit var binding: ActivityListaAtividadeBinding
    private var codProfessor: Long = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListaAtividadeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = DBHelper(this)

        if (intent.extras != null)
        {
            codProfessor = intent.extras!!.getLong("COD_PROFESSOR")
        }

        var atividades = readDataFunction()

        binding.voltar.setOnClickListener {
            val intent = Intent(this, MenuProfessor::class.java)
            intent.putExtra("CODIGO_PROFESSOR", codProfessor)
            startActivity(intent)
            finish()
        }

        var recyclerViewAtividades = binding.recViewAtividades

        recyclerViewAtividades.apply {
            layoutManager = LinearLayoutManager(this@ListaAtividade)
            addItemDecoration(DividerItemDecoration(this@ListaAtividade, DividerItemDecoration.VERTICAL))
            adapter = AtividadesAdapter(atividades, layoutInflater)
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

    private fun readDataFunction() : ArrayList<Atividades>{
        val atividades: ArrayList<Atividades> = ArrayList()
        try {
            val data: Cursor = db.readAtividade(codProfessor.toString())

            if (data != null && data.count > 0) {
                if (data.moveToFirst()) {
                    do {
                        val atv = Atividades(data.getString(0).toInt(), data.getString(1), data.getString(2))
                        atividades.add(atv)
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

        return atividades
    }



    private fun RedirectLogin(){
        val intent = Intent(this, FormLogin::class.java)
        startActivity(intent)
        finish()
    }
}