package com.jatinvashisht.mergesortvisualizer.ui.home_screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner

@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel = hiltViewModel(),
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    currentOnStart: () -> Unit,
    currentOnStop: () -> Unit,
) {
    val columnLazyListState = rememberLazyListState()
    val currentPhaseList = remember{viewModel.currentPhasesList}
    LaunchedEffect(key1 = currentPhaseList.size){
        columnLazyListState.animateScrollToItem(viewModel.currentPhasesList.size-1)
    }
    DisposableEffect(lifecycleOwner){
        val observer = LifecycleEventObserver{_, event ->
            if(event == Lifecycle.Event.ON_START){
                currentOnStart()
            }else if(event == Lifecycle.Event.ON_STOP){
                currentOnStop()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    val permanentIntList = remember{ mutableStateOf(viewModel.intListState.value)}
    LazyColumn(modifier = Modifier.fillMaxSize(), state = columnLazyListState) {
        item{
            PlayPauseButton(text = "play", modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp)) {
                viewModel.start()
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
        items(currentPhaseList){list->
            TextBoxes(intList = list, modifier = Modifier.fillMaxSize())
        }
    }
}

@Composable
fun TextBoxes(
    modifier: Modifier = Modifier,
    intList: List<Int>
) {
    LazyRow(modifier = modifier, contentPadding = PaddingValues(all = 4.dp)){
        items(intList){element->
            Box(
                modifier = Modifier
                    .size(75.dp)
                    .padding(4.dp)
                    .drawBehind { drawRect(Color(0, 200, 83, 255)) },
                contentAlignment = Alignment.Center
            ) {
                Text(text = element.toString())
            }
        }
    }
}

@Composable
fun PlayPauseButton(modifier: Modifier = Modifier, text: String,onPlayPauseClicked: ()->Unit) {
    Button(onClick = onPlayPauseClicked, modifier = modifier) {
        Text(text = text)
    }
}