package com.gilorroristore.todoapp.addtasks.ui

import android.view.Gravity
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogWindowProvider
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import com.gilorroristore.todoapp.addtasks.ui.model.TaskModel

@Composable
fun TasksScreen(
    modifier: Modifier = Modifier,
    tasksViewModel: TasksViewModel = hiltViewModel()
) {
    val showDialog: Boolean by tasksViewModel.showDialog.observeAsState(false)
    val lifeCycle = LocalLifecycleOwner.current.lifecycle

    val uiState by produceState<TaskUiState>(
        initialValue = TaskUiState.Loading,
        key1 = lifeCycle,
        key2 = tasksViewModel
    ) {
        lifeCycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
            tasksViewModel.uiState.collect {
                value = it
            }
        }
    }

    when (uiState) {
        is TaskUiState.Error -> {

        }

        TaskUiState.Loading -> {
            CircularProgressIndicator()
        }

        is TaskUiState.Success -> {
            Box(modifier.fillMaxSize()) {

                AddTasksDialog(
                    showDialog,
                    onDismiss = { tasksViewModel.onDialogClose() },
                    onTaskAdded = { tasksViewModel.onTaskCreated(it) })
                TaskList((uiState as TaskUiState.Success).tasks, tasksViewModel)
                FabDialog(modifier.align(Alignment.BottomEnd), tasksViewModel)
            }
        }
    }
}

@Composable
fun TaskList(myTasks: List<TaskModel>, tasksViewModel: TasksViewModel) {

    if (myTasks.isEmpty()) {
        ShowEmptyFields()
    } else {
        LazyColumn() {
            items(myTasks, key = { it.id }) { task ->
                ItemTask(task, tasksViewModel)
            }
        }
    }
}

@Composable
fun ShowEmptyFields() {
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = 18.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        Text(
            textAlign = TextAlign.Center,
            text = "Presiona + para añadir una nueva nota",
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp
        )
    }
}

@Composable
fun ItemTask(taskModel: TaskModel, tasksViewModel: TasksViewModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .pointerInput(Unit) {
                detectTapGestures(onLongPress = {
                    tasksViewModel.onItemRemove(taskModel)
                })
            },
        //.border(border = BorderStroke(2.dp, Color.Gray), shape = RoundedCornerShape(16)),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 4.dp),
                text = taskModel.task
            )
            Checkbox(
                checked = taskModel.selected,
                onCheckedChange = {
                    tasksViewModel.onCheckBoxSelected(taskModel)
                }
            )
        }
    }
}

@Composable
fun FabDialog(modifier: Modifier = Modifier, tasksViewModel: TasksViewModel) {
    FloatingActionButton(
        onClick = {
            tasksViewModel.onShowSelected()
        },
        modifier.padding(16.dp)
    ) {
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = null
        )
    }
}


@Composable
fun AddTasksDialog(show: Boolean, onDismiss: () -> Unit, onTaskAdded: (String) -> Unit) {
    var myTask by remember { mutableStateOf("") }
    if (show) {
        Dialog(
            onDismissRequest = { onDismiss() }
        ) {
            (LocalView.current.parent as DialogWindowProvider).apply {
                window.setGravity(Gravity.BOTTOM)
                window.attributes = window.attributes.apply {
                    y = 0
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        MaterialTheme.colorScheme.onBackground,
                        RoundedCornerShape(
                            topStart = 12.dp,
                            topEnd = 12.dp,
                            bottomEnd = 0.dp,
                            bottomStart = 0.dp
                        )
                    )
                    .padding(16.dp),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Agrega tu tarea", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(16.dp))
                TextField(
                    value = myTask,
                    onValueChange = { myTask = it },
                    singleLine = true,
                    maxLines = 1
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(modifier = Modifier.fillMaxWidth(), onClick = {
                    if (myTask.isNotEmpty()) {
                        onTaskAdded(myTask)
                        myTask = ""
                    }
                }) {
                    Text(text = "Añadir tarea")
                }
            }
        }
    }
}