package com.example.wordswar.uidata

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.wordswar.R

@Composable
fun GameScreen() {
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(stringResource(R.string.game_title),
            style = MaterialTheme.typography.titleMedium)

        GameLayout()

        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = { /*TODO*/ },
                modifier = Modifier.fillMaxWidth()) {
                Text(stringResource(R.string.submit_button))
            }
            OutlinedButton(onClick = { /*TODO*/ },
                modifier = Modifier.fillMaxWidth()) {
                Text(stringResource(R.string.skip_button))
            }

        }

        GameScore()
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameLayout() {

    Card() {
        Column(modifier = Modifier
            .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier
                    .clip(shapes.medium)
                    .background(colorScheme.surfaceTint)
                    .padding(horizontal = 10.dp, vertical = 4.dp)
                    .align(alignment = Alignment.End),
                text =  stringResource(R.string.word_count),
                color = colorScheme.onPrimary,
                style = MaterialTheme.typography.titleMedium
            )

            Text(text = "nlmeo")
            Text(stringResource(R.string.game_instructions))
            OutlinedTextField(
                value = "",
                onValueChange = {},
                label = { Text(stringResource(R.string.word_textfield))},
                singleLine = true,
                colors = TextFieldDefaults.textFieldColors(containerColor = colorScheme.surface),
                shape = shapes.medium,
                isError = false,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ), keyboardActions = KeyboardActions(
                    onDone = {}
                )
            )

        }
    }

}


@Composable
fun GameScore() {
    Card() {
        Text(stringResource(R.string.game_score),
        style = MaterialTheme.typography.headlineMedium,
        modifier = Modifier.padding(8.dp))
    }
}

@Composable
fun FinalScoreDialog(
    score: Int,
    onPlayAgain: () -> Unit
    ) {
    val activity = (LocalContext.current as Activity)

    AlertDialog(
        onDismissRequest = { /*TODO*/ },
        title = { Text(stringResource(R.string.congratulations))},
        text = { Text(stringResource(R.string.dialog_score, score))},
        modifier = Modifier,
        dismissButton = {
            TextButton(
                onClick = {
                    activity.finish()
                }
            ){
                Text(stringResource(R.string.dialog_exit))
            }
        },
        confirmButton = {
            TextButton(onClick = onPlayAgain){
                Text(stringResource(R.string.dialog_play_again))
            }
        }


        )
}

@Preview(showBackground = true)
@Composable
fun GameScreenPrev() {
    GameScreen()
}
