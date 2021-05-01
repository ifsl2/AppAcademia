package com.example.appacademia

import android.content.Intent
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appacademia.databinding.ActivityFormCadastro2Binding
import com.example.appacademia.databinding.ActivityFormCadastroBinding
import java.lang.Exception

class FormCadastro2 : AppCompatActivity() {
    private lateinit var  binding: ActivityFormCadastro2Binding
    private var codAluno: Long = 0
    internal lateinit var db:DBHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormCadastro2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        if (intent.extras != null)
        {
            codAluno = intent.extras!!.getLong("COD_ALUNO")
        }

        db = DBHelper(this)

        var profs = retornarProfessores()

        var recyclerViewProfs = binding.recViewProfs

        binding.finalizar.setOnClickListener {
            val intent = Intent(this, MenuAluno::class.java)
            intent.putExtra("CODIGO_ALUNO", codAluno)
            startActivity(intent)
            finish()
        }

        recyclerViewProfs.apply {
            layoutManager = LinearLayoutManager(this@FormCadastro2)
            addItemDecoration(DividerItemDecoration(this@FormCadastro2, DividerItemDecoration.VERTICAL))
            adapter = AlunosAdapter(profs, layoutInflater)
        }
    }

    private fun retornarProfessores() : ArrayList<Alunos>{
        val professores: ArrayList<Alunos> = ArrayList()
        try {
            val data: Cursor = db.readProfessores()

            if (data != null && data.count > 0) {
                if (data.moveToFirst()) {
                    do {
                        val obj = Alunos(data.getInt(0), data.getString(1), data.getInt(5), data.getString(4))
                        professores.add(obj)
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

        return professores
    }
}