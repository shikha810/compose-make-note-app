package com.example.notebook

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.notebook.model.Note
import com.example.notebook.screen.NoteScreen
import com.example.notebook.screen.NoteViewModel
import com.example.notebook.ui.theme.NoteBookTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NoteBookTheme {
                Surface(
                    color = Color.White
                ) {
                    val noteViewModel: NoteViewModel by viewModels()
                    NoteApplicationStart(noteViewModel)
                }
            }

        }
    }
}

@ExperimentalComposeUiApi
@Composable
fun NoteApp(noteViewModel: NoteViewModel) {
    val noteList: List<Note> = noteViewModel.noteList.collectAsState().value
    NoteScreen(
        noteViewModel,
        notes = noteList,
        onAddNotes = {
            noteViewModel.addNote(it)
        },
        onRemoveNote = {
            noteViewModel.removeNote(it)
        })
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun NoteApplicationStart(noteViewModel: NoteViewModel) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "notes_list") {
        composable("notes_list") {
            NoteApp(noteViewModel)

        }
        composable("notes_Details") {

        }

    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Preview(showBackground = true)
@Composable
fun NoteAppPreview() {
    NoteBookTheme {
        NoteApp(viewModel())
    }
}