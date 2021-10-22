package com.example.dogbox.ui

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import coil.decode.DataSource
import coil.decode.GifDecoder
import coil.transform.CircleCropTransformation
import com.example.dogbox.repository.DogsRepositoryImpl
import com.example.dogbox.ui.ui.theme.DogBoxTheme
import com.example.dogbox.R


@Composable
fun DogBoxApp(content: @Composable () -> Unit) {
    DogBoxTheme {
        Surface(color = MaterialTheme.colors.background) {
            content()
        }
    }
}

@ExperimentalCoilApi
@Composable
fun ImageGallery() {
    /* TODO: Make this performant*/
    LazyColumn {
        item {
            StaggeredVerticalGrid(Modifier.padding(8.dp)) {
                for(uri in DogsRepositoryImpl().getDogUrls()) {
                    val painter = rememberImagePainter(
                            data = uri,
                            builder = {
                                crossfade(true)
                            })

                    var modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                        .clickable(enabled = true, onClick = {
                            Log.i("CLICKED", uri.toString())
                        })
                    val state = painter.state
                    if (state is ImagePainter.State.Success && state.result.metadata.dataSource != DataSource.MEMORY_CACHE ) {
                        val height = state.result.drawable.intrinsicHeight
                        val width = state.result.drawable.intrinsicWidth
                        modifier.aspectRatio(width.toFloat()/height.toFloat())
                    } else {
                        modifier = modifier.height(150.dp)
                    }

                    Card(
                        backgroundColor = Color.Yellow,
                        modifier = modifier,
                    ) {
                        Image(
                            painter = painter,
                            contentDescription = "Image of a doggo",
                            contentScale = ContentScale.FillWidth
                        )
                    }
                }
            }
        }
    }
    Image(
        rememberImagePainter(
            data = "",
            builder = {
                decoder(GifDecoder())
                placeholder(R.drawable.placeholder)
                error(R.drawable.placeholder)
            }
        ),
        contentDescription = "Flying dog",
        modifier = Modifier
            .clip(CircleShape)

    )
}


@Preview(showBackground = true)
@Composable
fun DogBoxAppPreview() {
    DogBoxApp { ImageGallery() }
}