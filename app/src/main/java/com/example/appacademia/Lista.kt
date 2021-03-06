package com.example.appacademia

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.get
import com.example.appacademia.databinding.ActivityListaBinding
import com.google.firebase.auth.FirebaseAuth

class Lista : AppCompatActivity() {

    internal lateinit var db:DBHelper
    lateinit var result : TextView
    private var listNotes = ArrayList<String>()
    private lateinit var binding: ActivityListaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListaBinding.inflate(layoutInflater)
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
        val data = db.readUsuario()
        val stringBuffer = StringBuffer()

        if(data != null && data.count >0){
            while(data.moveToNext()){
                stringBuffer.append("Nome: ${data.getString(3)}\n")
                stringBuffer.append("Telefone: ${data.getString(4)}\n")
                stringBuffer.append("E-mail: ${data.getString(1)}\n\n")
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