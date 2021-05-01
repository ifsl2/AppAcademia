package com.example.appacademia

data class Usuarios(val usuario: String, val senha: String, val categoria: String, val nome: String, val telefone: String) {
    var codigo: Long = 0
}