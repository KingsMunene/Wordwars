package com.example.wordswar.uidata

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.wordswar.R

@Composable
fun GameScreen() {
    Column(modifier = Modifier) {
        Text(stringResource(R.string.game_title),
            style = MaterialTheme.typography.titleMedium)

        GameLayout()

    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameLayout() {
    Column(modifier = Modifier) {
        Text(stringResource(R.string.word_count))
        Text(text = "nlmeo")
        Text(stringResource(R.string.game_instructions))
        OutlinedTextField(value = "", onValueChange = {},
            label = { Text(stringResource(R.string.word_textfield))})

    }
}


@Preview(showBackground = true)
@Composable
fun GameScreenPrev() {
    GameScreen()
}