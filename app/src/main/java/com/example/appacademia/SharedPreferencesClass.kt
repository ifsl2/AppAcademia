package com.example.appacademia

import android.app.Activity
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import java.util.*

class SharedPreferencesClass {

    fun adicionarUsuario(activity: Activity, usuario: Usuarios) {
        val sharedPref = activity.getSharedPreferences("usuario_logado", MODE_PRIVATE)

        val editor: Editor = sharedPref.edit()

        editor.putString("usuario.email", usuario.usuario)
        editor.putString("usuario.tipo", usuario.categoria)
        editor.commit()

    }

    fun recuperarUsuario(activity: Activity): Map<String, String> {
        val sharedPref = activity.getSharedPreferences("usuario_logado", MODE_PRIVATE)

        val ret: Map<String, String> = mapOf(
            "email" to sharedPref.getString("usuario.email", "").toString(),
            "tipo" to sharedPref.getString("usuario.tipo", "").toString()
        )

        return ret
    }

}