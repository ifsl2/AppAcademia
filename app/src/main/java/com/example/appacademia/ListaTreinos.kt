package com.example.appacademia

import android.content.Intent
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appacademia.databinding.ActivityCriarAtividadeBinding
import com.example.appacademia.databinding.ActivityListaAtividadeBinding
import com.example.appacademia.databinding.ActivityListaTreinosBinding
import java.lang.Exception

class ListaTreinos : AppCompatActivity() {
    internal lateinit var db:DBHelper
    private lateinit var binding: ActivityListaTreinosBinding
    private var codUsuario: Long = 0
    private var tipo: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListaTreinosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = DBHelper(this)

        if (intent.extras != null)
        {
            codUsuario = intent.extras!!.getLong("COD_USUARIO")
            tipo = intent.extras!!.getString("TIPO_USUARIO")
        }

        binding.voltar.setOnClickListener {
            val intent = Intent(this, MenuAluno::class.java)
            intent.putExtra("COD_USUARIO", codUsuario)
            startActivity(intent)
            finish()
        }

        var treinos = readDataFunction()

        var recyclerViewTreinos = binding.recViewTreinos

        recyclerViewTreinos.apply {
            layoutManager = LinearLayoutManager(this@ListaTreinos)
            addItemDecoration(DividerItemDecoration(this@ListaTreinos, DividerItemDecoration.VERTICAL))
            adapter = TreinoAdapter(treinos, layoutInflater)
        }
    }

    private fun readDataFunction() : ArrayList<Treino>{
        val treinos: ArrayList<Treino> = ArrayList()
        try {
            var data = db.readAtividadesAlunos(codUsuario.toString())

            if (data != null && data.count > 0) {
                if (data.moveToFirst()) {
                    do {
                        var treino = Treino(data.getString(0).toInt(), data.getString(1), data.getString(2), data.getString(3).toInt(), data.getString(4))
                        treinos.add(treino)
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

        return treinos
    }
}