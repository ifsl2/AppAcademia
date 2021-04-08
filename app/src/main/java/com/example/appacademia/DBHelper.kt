package com.example.appacademia

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.DatabaseErrorHandler
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class DBHelper(context: Context) : SQLiteOpenHelper(context, BD_NOME, null, BD_VERSAO) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    fun recuperarUsuario(usuario: String) : Usuarios? {
        val query = "SELECT * FROM $TABELA_USUARIOS WHERE $COLUNA_USUARIO = ?"
        val db:SQLiteDatabase = this.readableDatabase

        val args = arrayOf(usuario)

        val c:Cursor = db.rawQuery(query, args)

        var usuario:Usuarios? = null

        if (c.count > 0){
            c.moveToFirst()

            usuario = Usuarios(
                    c.getString(c.getColumnIndex(COLUNA_USUARIO)),
                    c.getString(c.getColumnIndex(COLUNA_SENHA)),
                    c.getString(c.getColumnIndex(COLUNA_CATEGORIA))
            )
        }

        return usuario

    }

    fun adicionarUsuario(usuario: Usuarios, context: Context, msgErro:TextView){
        try {
            val checkUsuario = recuperarUsuario(usuario.usuario)
            Log.i("USUARIO", checkUsuario.toString())
            checkUsuario?.let {
                msgErro.text = checkUsuario.toString()
                if (checkUsuario.usuario == usuario.usuario) {
                    Toast.makeText(context, "Usuário ja cadastrado", Toast.LENGTH_SHORT).show()
                    return
                }
            }
            val db:SQLiteDatabase = this.writableDatabase
            val values = ContentValues()
            values.put(COLUNA_USUARIO, usuario.usuario)
            values.put(COLUNA_SENHA, usuario.senha?.let { MD5(it) })
            values.put(COLUNA_CATEGORIA, usuario.categoria)
            db.insert(TABELA_USUARIOS, null, values)
            db.close()
            Toast.makeText(context, "Usuário cadastrado com sucesso", Toast.LENGTH_SHORT).show()
        } catch (ex: Exception) {
            Toast.makeText(context, ex.toString(), Toast.LENGTH_SHORT).show()
        }

    }

    fun atualizarUsuario(usuario: Usuarios): Int{
        val db:SQLiteDatabase = this.writableDatabase
        val values = ContentValues()
        values.put(COLUNA_USUARIO, usuario.usuario)
        usuario.senha?.let {
            values.put(COLUNA_SENHA, MD5(usuario.senha!!))
        }
        values.put(COLUNA_CATEGORIA, usuario.categoria)
        return db.update(TABELA_USUARIOS, values, "$COLUNA_USUARIO=?", arrayOf(usuario.codigo.toString()))
    }

    companion object {
        private const val BD_VERSAO = 2
        private const val BD_NOME = "AppAcademia.db"
        private const val TABELA_USUARIOS = "usuarios"
        private const val COLUNA_USUARIO = "usuario"
        private const val COLUNA_SENHA = "senha"
        private const val COLUNA_CATEGORIA = "categoria"
        private const val SQL_CREATE_ENTRIES = "CREATE TABLE IF NOT EXISTS $TABELA_USUARIOS ($COLUNA_USUARIO TEXT PRIMARY KEY, $COLUNA_SENHA TEXT, $COLUNA_CATEGORIA TEXT);"

        private const val SQL_DELETE_ENTRIES = "DROP TABLE $TABELA_USUARIOS"
    }
}