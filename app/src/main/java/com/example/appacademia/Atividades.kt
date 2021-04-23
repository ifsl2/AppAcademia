package com.example.appacademia

data class Atividades(val id: Int, val nome: String, val descricao: String) {
    companion object {
        var id = 0
        var nome = ""
        var descricao = ""
    }
}