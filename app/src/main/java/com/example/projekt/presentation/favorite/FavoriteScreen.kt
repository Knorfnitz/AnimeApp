package com.example.projekt.presentation.favorite

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade

@Composable
fun FavoriteScreen(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    AsyncImage(
        model = ImageRequest.Builder(context)
            .data("https://cdn.myanimelist.net/images/anime/1223/96541.jpg")
            .crossfade(true)
            .build(),
        contentDescription = "Test",
        modifier = Modifier
            .height(200.dp)
            .aspectRatio(2f / 3f),
        onSuccess = {
            Log.d("Coil3", "✅ Bild geladen")
        },
        onError = {
            Log.e("Coil3", "❌ Fehler beim Laden")
        }
    )


}


@Preview(showBackground = true)
@Composable
private fun FavoriteScreenPreview() {
    // Use Theme here
    FavoriteScreen()
}