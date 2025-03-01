package com.example.todotask

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.todotask.viewmodel.CheckListViewModel
import com.example.todotask.uiscreen.CheckListScreen
import junit.framework.TestCase.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CheckListInstrumentedTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun test_addNewChecklist_updatesListCount() {
        val viewModel = CheckListViewModel()

        composeTestRule.setContent {
            CheckListScreen(viewModel = viewModel)
        }

        // Hent antall lister før handling
        val initialCount = viewModel.checkLists.value.size

        // Klikk på FAB-knappen for å legge til en ny liste
        composeTestRule.onNodeWithContentDescription("Legg til ny sjekkliste").performClick()

        // Velg ikon (simulerer klikk på første ikon)
        composeTestRule.onNodeWithText("Handlekurv").performClick()

        // Angi navn på sjekkliste
        composeTestRule.onNodeWithText("Gi navn til sjekkliste").performTextInput("Testliste")
        composeTestRule.onNodeWithText("OK").performClick()

        // Sjekk at antall lister har økt med 1
        composeTestRule.waitForIdle()
        assertEquals(initialCount + 1, viewModel.checkLists.value.size)
    }

    @Test
    fun test_deleteChecklist_updatesListCount() {
        val viewModel = CheckListViewModel()
        composeTestRule.setContent {
            CheckListScreen(viewModel = viewModel)
        }

        // Hent antall lister før handling
        val initialCount = viewModel.checkLists.value.size

        // Klikk på slett-knappen til første sjekkliste
        composeTestRule.onAllNodesWithContentDescription("Slett liste")[0].performClick()

        // Bekreft sletting
        composeTestRule.onNodeWithText("Slett").performClick()

        // Sjekk at antall lister er redusert med 1
        composeTestRule.waitForIdle()
        assert(viewModel.checkLists.value.size == initialCount - 1)
    }

    @Test
    fun test_deleteChecklist_updatesCompletedTaskCount() {
        val viewModel = CheckListViewModel()
        composeTestRule.setContent {
            CheckListScreen(viewModel = viewModel)
        }

        // Hent antall fullførte oppgaver før sletting
        val initialCompletedTasks = viewModel.checkLists.value.sumOf { it.items.count { item -> item.checked } }

        // Klikk på slett-knappen til første sjekkliste
        composeTestRule.onAllNodesWithContentDescription("Slett liste")[0].performClick()

        // Bekreft sletting
        composeTestRule.onNodeWithText("Slett").performClick()

        // Sjekk at antall fullførte oppgaver er redusert
        composeTestRule.waitForIdle()
        val newCompletedTasks = viewModel.checkLists.value.sumOf { it.items.count { item -> item.checked } }
        assert(newCompletedTasks < initialCompletedTasks)
    }

    @Test
    fun test_toggleTask_updatesCompletedTaskCount() {
        val viewModel = CheckListViewModel()
        composeTestRule.setContent {
            CheckListScreen(viewModel = viewModel)
        }

        // Først ekspander listen slik at oppgavelisten (og checkboxene) blir synlige.
        composeTestRule.onNodeWithContentDescription("Expand/Collapse").performClick()

        // Hent antall fullførte oppgaver før handling
        val initialCompletedTasks = viewModel.checkLists.value.sumOf { it.items.count { item -> item.checked } }

        // Klikk på første oppgave for å endre status (når oppgavelisten er synlig)
        composeTestRule.onAllNodesWithTag("TaskToggle")[0].performClick()

        // Sjekk at antall fullførte oppgaver har endret seg
        composeTestRule.waitForIdle()
        val newCompletedTasks = viewModel.checkLists.value.sumOf { it.items.count { item -> item.checked } }
        assert(newCompletedTasks != initialCompletedTasks)
    }
}
