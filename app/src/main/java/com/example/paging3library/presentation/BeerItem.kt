package com.example.paging3library.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.paging3library.domain.Beer
import com.example.paging3library.ui.theme.Paging3LibraryTheme

@Composable
fun BeerItem(
    beer: Beer,
    modifier: Modifier = Modifier
) {

}
@Preview
@Composable
fun BeerItemPreview() {
    Paging3LibraryTheme {
        BeerItem(
            beer = Beer(
                id = 1,
                name = "Beer",
                tagline = "cool beer",
                firstBrewed = "08/23",
                description = "this is description",
                imageUrl = ""
            ),
            modifier = Modifier.fillMaxSize()
        )
    }
}