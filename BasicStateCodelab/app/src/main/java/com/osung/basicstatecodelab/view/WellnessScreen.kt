package com.osung.basicstatecodelab

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.osung.basicstatecodelab.data.WellnessTask
import com.osung.basicstatecodelab.viewmodel.WellnessViewModel

@Composable
fun WellnessScreen(
    modifier: Modifier = Modifier,
    wellnessViewModel: WellnessViewModel = viewModel()
) {
    Column(modifier = modifier) {
        StatefulCounter()

        // rememberSaveable 은 Bundle에 저장하기 때문에 데이터 객체의 경우 직렬화가 필요하다.
        // val list = remember { getWellnessTasks().toMutableStateList() }
        WellnessTasksList(
            list = wellnessViewModel.task,
            onCheckedTask = { task, checked ->
                wellnessViewModel.changeTaskChecked(task, checked)
            },
            onCloseTask = {
                wellnessViewModel.remove(it)
            }
        )
    }
}

@Composable
@Preview(showBackground = true)
fun WellnessScreenPreview() {
    WellnessScreen()
}

