package com.example.appacademia

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import com.example.appacademia.databinding.ActivityListaAlunosBinding
import com.google.firebase.auth.FirebaseAuth

class ListaAtividade : AppCompatActivity() {
    internal lateinit var db:DBHelper
    lateinit var result : TextView
    private lateinit var binding: ActivityListaAlunosBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListaAlunosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = DBHelper(this)
        result = binding.resultText


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

    private fun readDataFunction(){
        val data = db.readAtividade()
        val stringBuffer = StringBuffer()

        if(data != null && data.count >0){
            while(data.moveToNext()){
                stringBuffer.append("Nome: ${data.getString(0)}\n")
                stringBuffer.append("Descrição: ${data.getString(1)}\n\n")
            }
            result.text = stringBuffer.toString()
            Toast.makeText(applicationContext,"Data carregada!", Toast.LENGTH_LONG).show()
        }else{
            Toast.makeText(applicationContext,"No Data", Toast.LENGTH_LONG).show()
        }

    }



    private fun RedirectLogin(){
        val intent = Intent(this, FormLogin::class.java)
        startActivity(intent)
        finish()
    }
}