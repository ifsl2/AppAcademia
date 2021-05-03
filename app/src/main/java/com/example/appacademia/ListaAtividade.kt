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
    private var codUsuario: Long = 0
    private var tipo: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListaAtividadeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = DBHelper(this)

        if (intent.extras != null)
        {
            codUsuario = intent.extras!!.getLong("COD_USUARIO")
            tipo = intent.extras!!.getString("TIPO_USUARIO")
        }

        var atividades = readDataFunction()

        binding.voltar.setOnClickListener {
            val intent = Intent(this, MenuProfessor::class.java)
            intent.putExtra("COD_USUARIO", codUsuario)
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
            var data: Cursor = db.readAtividade(codUsuario.toString())

            if (data != null && data.count > 0) {
                if (data.moveToFirst()) {
                    do {
                        var atv = Atividades(data.getString(0).toInt(), data.getString(1), data.getString(2))
                        atividades.add(atv)
                    } while (data.moveToNext())
                }
                Toast.makeText(applicationContext, "Data carregada!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(applicationContext, "No Data", Toast.LENGTH_SHORT).show()
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