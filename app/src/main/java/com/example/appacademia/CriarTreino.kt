package com.example.appacademia

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CompoundButton
import android.widget.Toast
import com.example.appacademia.databinding.ActivityCriarAtividadeBinding
import com.example.appacademia.databinding.ActivityCriarTreinoBinding

class CriarTreino : AppCompatActivity() {
    private lateinit var  binding: ActivityCriarTreinoBinding
    private var codProfessor: Long = 0
    private var diasTreino: ArrayList<String> = ArrayList()
    private var codAtividade: Int = 0
    private var tipo: String? = null
    private var acao: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCriarTreinoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (intent.extras != null)
        {
            tipo = intent.extras!!.getString("TIPO_USUARIO")
            codProfessor = intent.extras!!.getLong("COD_USUARIO")
            codAtividade = intent.extras!!.getInt("COD_ATIVIDADE")
            acao = intent.extras!!.getInt("VIEWHOLDER_ACAO")
        }

        binding.diaSegunda.setOnCheckedChangeListener { it, isChecked ->
            adicionarDias(it, isChecked)
        }
        binding.diaTerca.setOnCheckedChangeListener { it, isChecked ->
            adicionarDias(it, isChecked)
        }
        binding.diaQuarta.setOnCheckedChangeListener { it, isChecked ->
            adicionarDias(it, isChecked)
        }
        binding.diaQuinta.setOnCheckedChangeListener { it, isChecked ->
            adicionarDias(it, isChecked)
        }
        binding.diaSexta.setOnCheckedChangeListener { it, isChecked ->
            adicionarDias(it, isChecked)
        }
        binding.diaSabado.setOnCheckedChangeListener { it, isChecked ->
            adicionarDias(it, isChecked)
        }
        binding.diaDomingo.setOnCheckedChangeListener { it, isChecked ->
            adicionarDias(it, isChecked)
        }

        binding.proximo.setOnClickListener {
            var repeticoes = binding.repeticoes.text.toString()
            if (repeticoes == "" || (!binding.diaSegunda.isChecked &&
                        !binding.diaTerca.isChecked && !binding.diaQuarta.isChecked &&
                        !binding.diaQuinta.isChecked && !binding.diaSexta.isChecked &&
                        !binding.diaSabado.isChecked && !binding.diaDomingo.isChecked)) {
                Toast.makeText(this, "Preencha todos os campos e seleciona pelo menos um dia", Toast.LENGTH_LONG).show()
            } else {
                val intent = Intent(this, ListaAlunos::class.java)
                intent.putExtra("COD_USUARIO", codProfessor)
                intent.putExtra("COD_ATIVIDADE", codAtividade)
                intent.putExtra("TIPO_USUARIO", "PROFESSOR")
                intent.putExtra("VIEWHOLDER_ACAO", 101)
                intent.putExtra("REPETICOES", repeticoes.toInt())
                intent.putExtra("DIAS", diasTreino)
                startActivity(intent)
                finish()
            }
        }

    }

    fun adicionarDias(checkBox: CompoundButton, isChecked: Boolean) {
        if (isChecked) {
            diasTreino.add(checkBox.text.toString())
        } else {
            diasTreino.remove(checkBox.text.toString())
        }
    }
}