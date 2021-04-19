
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
                    c.getString(c.getColumnIndex(COLUNA_CATEGORIA)),
                    c.getString(c.getColumnIndex(COLUNA_NOME_USER)),
                    c.getString(c.getColumnIndex(COLUNA_TELEFONE))
            )
        }

        return usuario

    }

    fun readUsuario() : Cursor
    {
        val db:SQLiteDatabase = this.writableDatabase
        val read : Cursor = db.rawQuery("SELECT * FROM $TABELA_USUARIOS WHERE $COLUNA_CATEGORIA = 'Professor'",null)
        return read
    }

    fun readAlunos() : Cursor
    {
        val db:SQLiteDatabase = this.writableDatabase
        val read : Cursor = db.rawQuery("SELECT * FROM $TABELA_USUARIOS WHERE $COLUNA_CATEGORIA = 'Aluno'",null)
        return read
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
            values.put(COLUNA_NOME_USER, usuario.nome)
            values.put(COLUNA_TELEFONE, usuario.telefone)
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

    fun adicionarAtividade(nome: String, descricao: String, context: Context){
        val db : SQLiteDatabase = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COLUNA_NOME, nome)
        contentValues.put(COLUNA_DESCRICAO, descricao)
        val insert_data = db.insert(TABELA_ATIVIDADES, null, contentValues)
        db.close()
        Toast.makeText(context, "Atividade cadastrado com sucesso", Toast.LENGTH_SHORT).show()
    }

    fun readAtividade() : Cursor
    {
        val db:SQLiteDatabase = this.writableDatabase
        val read : Cursor = db.rawQuery("SELECT * FROM $TABELA_ATIVIDADES",null)
        return read
    }

    companion object {
        private const val BD_VERSAO = 5
        private const val BD_NOME = "AppAcademia.db"
        private const val TABELA_USUARIOS = "usuarios"
        private const val COLUNA_COD_US = "cod_usuario"
        private const val COLUNA_USUARIO = "usuario"
        private const val COLUNA_SENHA = "senha"
        private const val COLUNA_CATEGORIA = "categoria"
        private const val COLUNA_NOME_USER = "nome"
        private const val COLUNA_TELEFONE = "telefone"

        private const val TABELA_ATIVIDADES = "atividades"
        private const val COLUNA_COD_ATIV = "cod_atividade"
        private const val COLUNA_NOME = "nome"
        private const val COLUNA_DESCRICAO = "descricao"

        private const val TABELA_US_AT = "usuarios_atividades"
        private const val COLUNA_COD_US_AT = "cod_us_at"
        private const val COLUNA_REPETICOES = "repeticoes"
        private const val COLUNA_DIAS = "dias"

        private const val SQL_CREATE_ENTRIES =
                "CREATE TABLE IF NOT EXISTS $TABELA_USUARIOS (" +
                        "$COLUNA_COD_US INTEGER PRIMARY KEY, " +
                        "$COLUNA_USUARIO TEXT, " +
                        "$COLUNA_SENHA TEXT, " +
                        "$COLUNA_NOME_USER TEXT," +
                        "$COLUNA_TELEFONE TEXT," +
                        "$COLUNA_CATEGORIA TEXT);" +
                "CREATE TABLE IF NOT EXISTS $TABELA_ATIVIDADES (" +
                        "$COLUNA_COD_ATIV INTEGER PRIMARY KEY, " +
                        "$COLUNA_NOME TEXT, " +
                        "$COLUNA_DESCRICAO TEXT);" +
                "CREATE TABLE IF NOT EXISTS $TABELA_US_AT (" +
                        "$COLUNA_COD_US_AT INTEGER PRIMARY KEY, " +
                        "$COLUNA_REPETICOES INTEGER, " +
                        "$COLUNA_DIAS TEXT, " +
                        "$COLUNA_COD_US INTEGER NOT NULL, " +
                        "$COLUNA_COD_ATIV INTEGER NOT NULL, " +
                        "FOREIGN KEY ($COLUNA_COD_US) REFERENCES $TABELA_USUARIOS ($COLUNA_COD_US) ON UPDATE CASCADE ON DELETE CASCADE," +
                        "FOREIGN KEY ($COLUNA_COD_ATIV) REFERENCES $TABELA_ATIVIDADES ($COLUNA_COD_ATIV) ON UPDATE CASCADE ON DELETE CASCADE);"


        private const val SQL_DELETE_ENTRIES = "DROP TABLE $TABELA_USUARIOS"
    }
}