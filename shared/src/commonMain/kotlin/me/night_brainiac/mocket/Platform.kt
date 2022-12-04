package me.night_brainiac.mocket

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
