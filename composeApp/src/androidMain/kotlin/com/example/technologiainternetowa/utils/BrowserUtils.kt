package com.example.technologiainternetowa.utils

import android.content.Intent
import android.net.Uri
import androidx.compose.ui.platform.LocalContext

// Na Androidzie potrzebujemy tzw. Contextu, aby wywołać przeglądarkę
actual fun openBrowser(url: String) {
    // UWAGA: Standardowe 'actual fun' nie widzi automatycznie Contextu Compose.
    // Jeśli Twoja funkcja 'actual' jest wywoływana z UI,
    // najbezpieczniej jest zaimplementować to w App.kt lub użyć statycznego dostępu.
}