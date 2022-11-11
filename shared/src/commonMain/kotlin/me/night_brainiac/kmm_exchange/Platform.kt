package me.night_brainiac.kmm_exchange

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform