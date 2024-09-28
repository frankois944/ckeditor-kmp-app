package fr.francoisdabonot.ckeditorapp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform