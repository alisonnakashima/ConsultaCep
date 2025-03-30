package br.edu.utfpr.consultacep

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform