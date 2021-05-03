
package com.example.appacademia

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.widget.Toast

class DBHelper(context: Context) : SQLiteOpenHelper(context, BD_NOME, null, BD_VERSAO) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(QUERY_USUARIO)
        db?.execSQL(QUERY_ATIVIDADES)
        db?.execSQL(QUERY_US_AT)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(QUERY_DELETE_USUARIOS)
        db?.execSQL(QUERY_DELETE_ATIVIDADES)
        db?.execSQL(QUERY_DELETE_US_AT)
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
                    c.getString(c.getColumnIndex(COLUNA_TELEFONE)),
                    c.getInt(c.getColumnIndex(COLUNA_IDADE))
            )
            usuario.codigo = c.getString(c.getColumnIndex(COLUNA_COD_US)).toLong()
        }

        return usuario

    }

    fun readUsuario() : Cursor
    {
        val db:SQLiteDatabase = this.readableDatabase
        return db.rawQuery("SELECT * FROM $TABELA_USUARIOS", arrayOf())
    }

    fun readProfessores() : Cursor
    {
        val db:SQLiteDatabase = this.readableDatabase
        return db.rawQuery("SELECT * FROM $TABELA_USUARIOS WHERE $COLUNA_CATEGORIA = 'Professor'", arrayOf())
    }

    fun readAlunosProfessor(codProfessor: String) : Cursor {
        val db:SQLiteDatabase = this.readableDatabase
        return db.rawQuery("SELECT * FROM $TABELA_USUARIOS WHERE $COLUNA_COD_PROF = ?", arrayOf(codProfessor))
    }

    fun readAlunos() : Cursor
    {
        val db:SQLiteDatabase = this.readableDatabase
        return db.rawQuery("SELECT * FROM $TABELA_USUARIOS WHERE $COLUNA_CATEGORIA = 'Aluno'", arrayOf())
    }

    fun adicionarUsuario(usuario: Usuarios, context: Context) : Long{
        var rowID: Long = -1
        try {
            val checkUsuario = recuperarUsuario(usuario.usuario)
            Log.i("USUARIO", checkUsuario.toString())
            checkUsuario?.let {
                if (checkUsuario.usuario == usuario.usuario) {
                    Toast.makeText(context, "Usuário ja cadastrado", Toast.LENGTH_SHORT).show()
                    return -1
                }
            }
            val db:SQLiteDatabase = this.writableDatabase
            val values = ContentValues()
            values.put(COLUNA_USUARIO, usuario.usuario)
            values.put(COLUNA_SENHA, usuario.senha?.let { MD5(it) })
            values.put(COLUNA_IDADE, usuario.idade)
            values.put(COLUNA_CATEGORIA, usuario.categoria)
            values.put(COLUNA_NOME_USER, usuario.nome)
            values.put(COLUNA_TELEFONE, usuario.telefone)
            rowID = db.insert(TABELA_USUARIOS, null, values)
            db.close()
            Toast.makeText(context, "Usuário cadastrado com sucesso", Toast.LENGTH_SHORT).show()
        } catch (ex: Exception) {
            Toast.makeText(context, ex.toString(), Toast.LENGTH_SHORT).show()
        }

        return rowID

    }

    fun adicionarProfessorAluno(codAluno: String, codProfessor: String, context: Context): Int {
        try {
            val db: SQLiteDatabase = this.writableDatabase
            val values = ContentValues()
            values.put(COLUNA_COD_PROF, codProfessor)
            return db.update(TABELA_USUARIOS, values, "$COLUNA_COD_US = ?", arrayOf(codAluno))
        } catch (ex: Exception) {
            Toast.makeText(context, ex.toString(), Toast.LENGTH_LONG).show()
        }
        return 0
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

    fun adicionarAtividade(nome: String, descricao: String, codProfessor: String, context: Context){
        val db : SQLiteDatabase = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COLUNA_NOME, nome)
        contentValues.put(COLUNA_DESCRICAO, descricao)
        contentValues.put(COLUNA_COD_PROF, codProfessor)
        val inserted = db.insert(TABELA_ATIVIDADES, null, contentValues)
        db.close()
        if (inserted != (-1).toLong()) {
            Toast.makeText(context, "Atividade cadastrado com sucesso", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Falha ao cadastrar atividade", Toast.LENGTH_SHORT).show()
        }
    }

    fun adicionarAtividadeAluno(repeticoes: Int, dias: String, codAtividade: Int, codAluno: Long, context: Context) : Long{
        val db : SQLiteDatabase = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COLUNA_REPETICOES, repeticoes)
        contentValues.put(COLUNA_DIAS, dias)
        contentValues.put(COLUNA_COD_US, codAluno)
        contentValues.put(COLUNA_COD_ATIV, codAtividade)
        val inserted = db.insert(TABELA_US_AT, null, contentValues)
        db.close()
        return inserted
    }

    fun readAtividadesAlunos(idAluno: String) : Cursor
    {
        val db:SQLiteDatabase = this.readableDatabase
        return db.rawQuery("SELECT atividades.cod_atividade, atividades.nome, atividades.descricao, usuarios_atividades.repeticoes, usuarios_atividades.dias " +
                "FROM atividades " +
                "INNER JOIN usuarios_atividades " +
                "ON usuarios_atividades.cod_atividade = atividades.cod_atividade " +
                "WHERE usuarios_atividades.cod_usuario = ?", arrayOf(idAluno))
    }

    fun readAtividade(idProfessor: String) : Cursor
    {
        val db:SQLiteDatabase = this.readableDatabase
        return db.rawQuery("SELECT * FROM $TABELA_ATIVIDADES WHERE cod_professor = ?", arrayOf(idProfessor))
    }

    fun readAtividades() : Cursor
    {
        val db:SQLiteDatabase = this.readableDatabase
        return db.rawQuery("SELECT * FROM $TABELA_ATIVIDADES", arrayOf())
    }

    companion object {
        private const val BD_VERSAO = 9
        private const val BD_NOME = "AppAcademia.db"
        private const val TABELA_USUARIOS = "usuarios"
        private const val COLUNA_COD_US = "cod_usuario"
        private const val COLUNA_USUARIO = "usuario"
        private const val COLUNA_SENHA = "senha"
        private const val COLUNA_CATEGORIA = "categoria"
        private const val COLUNA_NOME_USER = "nome"
        private const val COLUNA_TELEFONE = "telefone"
        private const val COLUNA_IDADE = "idade"

        private const val TABELA_ATIVIDADES = "atividades"
        private const val COLUNA_COD_ATIV = "cod_atividade"
        private const val COLUNA_COD_PROF = "cod_professor"
        private const val COLUNA_NOME = "nome"
        private const val COLUNA_DESCRICAO = "descricao"

        private const val TABELA_US_AT = "usuarios_atividades"
        private const val COLUNA_COD_US_AT = "cod_us_at"
        private const val COLUNA_REPETICOES = "repeticoes"
        private const val COLUNA_DIAS = "dias"

        private const val QUERY_USUARIO = "CREATE TABLE IF NOT EXISTS $TABELA_USUARIOS (" +
                "$COLUNA_COD_US INTEGER PRIMARY KEY, " +
                "$COLUNA_USUARIO TEXT, " +
                "$COLUNA_SENHA TEXT, " +
                "$COLUNA_NOME_USER TEXT," +
                "$COLUNA_TELEFONE TEXT," +
                "$COLUNA_IDADE INTEGER," +
                "$COLUNA_CATEGORIA TEXT," +
                "$COLUNA_COD_PROF INTEGER," +
                "FOREIGN KEY ($COLUNA_COD_PROF) REFERENCES $TABELA_USUARIOS ($COLUNA_COD_US) ON UPDATE CASCADE ON DELETE CASCADE);"

        private const val QUERY_ATIVIDADES = "CREATE TABLE IF NOT EXISTS $TABELA_ATIVIDADES (" +
                "$COLUNA_COD_ATIV INTEGER PRIMARY KEY, " +
                "$COLUNA_NOME TEXT, " +
                "$COLUNA_DESCRICAO TEXT," +
                "$COLUNA_COD_PROF INTEGER NOT NULL," +
                "FOREIGN KEY ($COLUNA_COD_PROF) REFERENCES $TABELA_USUARIOS ($COLUNA_COD_US) ON UPDATE CASCADE ON DELETE CASCADE);"

        private const val QUERY_US_AT = "CREATE TABLE IF NOT EXISTS $TABELA_US_AT (" +
                "$COLUNA_COD_US_AT INTEGER PRIMARY KEY, " +
                "$COLUNA_REPETICOES INTEGER, " +
                "$COLUNA_DIAS TEXT, " +
                "$COLUNA_COD_US INTEGER NOT NULL, " +
                "$COLUNA_COD_ATIV INTEGER NOT NULL, " +
                "FOREIGN KEY ($COLUNA_COD_US) REFERENCES $TABELA_USUARIOS ($COLUNA_COD_US) ON UPDATE CASCADE ON DELETE CASCADE," +
                "FOREIGN KEY ($COLUNA_COD_ATIV) REFERENCES $TABELA_ATIVIDADES ($COLUNA_COD_ATIV) ON UPDATE CASCADE ON DELETE CASCADE);"

        private const val QUERY_DELETE_USUARIOS = "DROP TABLE IF EXISTS $TABELA_USUARIOS"
        private const val QUERY_DELETE_ATIVIDADES = "DROP TABLE IF EXISTS $TABELA_ATIVIDADES"
        private const val QUERY_DELETE_US_AT = "DROP TABLE IF EXISTS $TABELA_US_AT"
    }
}