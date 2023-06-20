package com.example.wordswar.uidata

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.wordswar.R

@Composable
fun GameScreen(gameViewModel: GameViewModel = viewModel()
) {

    //Get the uiState from the gameViewModel
    val gameUIState by gameViewModel.uiState.collectAsState()

    val  mediumPadding = dimensionResource(R.dimen.medium_padding)

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(mediumPadding),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(stringResource(R.string.game_title),
            style = MaterialTheme.typography.titleLarge)

        GameLayout(
            scrambledWord = gameUIState.currentScrambledWord,
            wordCount = gameUIState.wordCount,
            userGuessWord = gameViewModel.userGuessedWord,
            onValueChange = {gameViewModel.userGuessInput(it)},
            isErrorState = gameUIState.isGuessedWrongWord,
            onKeyboardDone = {gameViewModel.checkUserInput()}

        )

        Column(
            modifier = Modifier.padding(mediumPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(mediumPadding)
        ) {
            Button(onClick = { gameViewModel.checkUserInput() },
                modifier = Modifier.fillMaxWidth()) {
                Text(stringResource(R.string.submit_button),
                    fontSize = 16.sp)
            }
            OutlinedButton(onClick = { /*TODO*/ },
                modifier = Modifier.fillMaxWidth()) {
                Text(stringResource(R.string.skip_button),
                    fontSize = 16.sp)
            }

        }

        GameScore()
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameLayout(
    scrambledWord: String,
    wordCount: Int,
    userGuessWord: String,
    onValueChange: (String) -> Unit,
    isErrorState: Boolean,
    onKeyboardDone: () -> Unit
) {

    val  mediumPadding = dimensionResource(R.dimen.medium_padding)
    Card() {
        Column(modifier = Modifier
            .padding(mediumPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(mediumPadding)
        ) {
            Text(
                modifier = Modifier
                    .clip(shapes.medium)
                    .background(colorScheme.surfaceTint)
                    .padding(horizontal = 10.dp, vertical = 4.dp)
                    .align(alignment = Alignment.End),
                text =  stringResource(R.string.word_count, wordCount),
                color = colorScheme.onPrimary,
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = scrambledWord,
                style = MaterialTheme.typography.titleLarge)
                Text(stringResource(R.string.game_instructions),
                style = MaterialTheme.typography.titleSmall)

                OutlinedTextField(
                value = userGuessWord,
                onValueChange = onValueChange,
                label = {if (isErrorState) Text(stringResource(R.string.wrong_guess)) else
                        Text(stringResource(R.string.word_textfield))},
                singleLine = true,
                colors = TextFieldDefaults.textFieldColors(containerColor = colorScheme.surface),
                shape = shapes.medium,
                isError = isErrorState,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ), keyboardActions = KeyboardActions(
                    onDone = { onKeyboardDone() }
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
