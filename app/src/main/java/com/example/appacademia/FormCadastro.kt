package com.example.appacademia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.appacademia.databinding.ActivityFormCadastroBinding

class FormCadastro : AppCompatActivity() {

     private lateinit var  binding: ActivityFormCadastroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormCadastroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar!!.hide()


        var toolbar = binding.toolbarCadastro
        toolbar.setBackgroundColor(getColor(R.color.white))
        toolbar.setNavigationIcon(getDrawable(R.drawable.ic_halteres))

    }
}