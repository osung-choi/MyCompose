package com.osung.basicstatecodelab

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun WaterCounter(modifier: Modifier = Modifier) {
    Column(modifier.padding(16.dp)) {
        // rememberSaveable : Configuration Changed 에서도 remember의 상태 유지
        var count by rememberSaveable { mutableStateOf(0) }

        if (count > 0) {
            Text(text = "You've had $count glasses.")
        }
        Button(
            modifier = Modifier.padding(top = 8.dp),
            onClick = { count++  },
            enabled = count < 10
        ) {
            Text(text = "Add One")
        }
    }
}

//@Composable
//fun WaterCounter(modifier: Modifier = Modifier) {
//    Column(modifier.padding(16.dp)) {
//        var count by remember { mutableStateOf(0) }
//
//        if (count > 0) {
//            // Clear water count 버튼을 누르면 리컴포지션이 발생하면서 Text와 WellnessTaskItem 코드가 수행되지 않고 종료된다.
//            // showTask by remember 코드가 호출되지 않았으므로 삭제된다. 따라서 Add One을 다시 클릭하면 다시 생성된다.
//            var showTask by remember { mutableStateOf(true) }
//
//            if (showTask) {
//                WellnessTaskItem(
//                    taskName = "Have you taken your 15 minute walk today?",
//                    onClose = { showTask = false }
//                )
//            }
//            Text(text = "You've had $count glasses.")
//        }
//        Row(modifier = Modifier.padding(top = 8.dp)) {
//            Button(
//                onClick = { count++ },
//                enabled = count < 10
//            ) {
//                Text(text = "Add One")
//            }
//
//            Button(
//                modifier = Modifier.padding(start = 8.dp),
//                onClick = { count = 0 }
//            ) {
//                Text(text = "Clear water count")
//            }
//        }
//    }
//}

@Composable
@Preview(showBackground = true)
fun WaterCounterPreview() {
    WaterCounter()
}