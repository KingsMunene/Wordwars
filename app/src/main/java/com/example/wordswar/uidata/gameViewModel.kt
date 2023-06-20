package com.example.wordswar.uidata

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.wordswar.data.MAX_WORDS
import com.example.wordswar.data.WORD_SCORE
import com.example.wordswar.data.words
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class GameViewModel : ViewModel() {

    data class GameUIState(
        val currentScrambledWord: String = "",
        val isGuessedWrongWord: Boolean = false,
        val wordCount: Int = 0,
        val score: Int = 0,
        val isGameOver: Boolean = false
        )

    //Game UI state
    private val _uiState = MutableStateFlow(GameUIState())

    //Backing property to avoid state updates from other classes
    val uiState = _uiState.asStateFlow()

    //user guessed word
    var userGuessedWord by mutableStateOf("")

    // Reflect the user input in the text field

    fun userGuessInput(word: String){
        userGuessedWord = word
    }

    // currentWord
    private lateinit var currentWord: String

    // Mutable set to store already selected words
    private var selectedWords: MutableSet<String> = mutableSetOf()

    // function to get a random word from the set of words and shuffle to scramble it
    private fun pickRandomWordAndShuffle(): String {
        currentWord = words.random()

        // check if the word is already selected
        if (selectedWords.contains(currentWord)){
            return pickRandomWordAndShuffle()
        } else {
            selectedWords.add(currentWord)
            return shuffleCurrentWord(currentWord)
        }

    }

    // Shuffle method converts the word into an array of characters then shuffles the characters
    // It returns the shuffled characters as a String
    private fun shuffleCurrentWord(currentWord: String): String {
        val tempWord = currentWord.toCharArray()

        tempWord.shuffle()

        while (String(tempWord) == currentWord){
            tempWord.shuffle()
        }
        return String(tempWord)
    }

    // Reset function
     fun resetGame() {
        selectedWords.clear()
        _uiState.value = GameUIState(currentScrambledWord = pickRandomWordAndShuffle(),
        wordCount = 1)
    }

    // Function to check the user's input vs the current word
    fun checkUserInput() {
        if (userGuessedWord.equals(currentWord, ignoreCase = true)){

            val currentScore = _uiState.value.score.plus(WORD_SCORE)
            updateGameState(currentScore)
        }else {
            _uiState.update{ currentState ->
                currentState.copy(isGuessedWrongWord = true)
            }
        }
        userGuessInput("")
    }

    // We call the updateGameState method passing the current score
    fun onSkipClicked(){
        updateGameState(_uiState.value.score)
    }

    // Update gameState function makes sure that words don't pass max of 10
    // It then updates the values of the game state accordingly
    private fun updateGameState(currentScore: Int) {
       if (_uiState.value.wordCount == MAX_WORDS) {
           _uiState.update{currentState ->
               currentState.copy(score = currentScore,
               isGuessedWrongWord = false,
               isGameOver = true
               )
           }

       }else {

           _uiState.update{ currentState ->
               currentState.copy(
                   score = currentScore,
                   currentScrambledWord = pickRandomWordAndShuffle(),
                   wordCount =  _uiState.value.wordCount.inc(),
                   isGuessedWrongWord = false
               )
           }
       }
    }

    init {
        resetGame()
    }
}
