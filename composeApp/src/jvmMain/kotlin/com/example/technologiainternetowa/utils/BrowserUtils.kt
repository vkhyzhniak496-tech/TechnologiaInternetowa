package com.example.technologiainternetowa.utils


import java.awt.Desktop
import java.net.URI

actual fun openBrowser(url: String) {
    if (Desktop.isDesktopSupported()) {
        Desktop.getDesktop().browse(URI(url))
    }
}