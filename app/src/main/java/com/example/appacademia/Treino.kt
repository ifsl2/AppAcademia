package com.example.appacademia

data class Treino(val id: Int, val nome: String, val descricao: String, val repeticoes: Int, val diasSemanas: String) {
    companion object {
        var id = 0
        var nome = ""
        var descricao = ""
        var repeticoes = 0
        var diasSemanas = ""
    }
}