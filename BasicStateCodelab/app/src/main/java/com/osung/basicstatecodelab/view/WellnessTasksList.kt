package com.osung.basicstatecodelab

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.osung.basicstatecodelab.data.WellnessTask

@Composable
fun WellnessTasksList(
    modifier: Modifier = Modifier,
    list: List<WellnessTask>,
    onCheckedTask: (WellnessTask, Boolean) -> Unit,
    onCloseTask: (WellnessTask) -> Unit = {}
) {
    LazyColumn(modifier = modifier) {
        items(
            items = list,
            key = { task -> task.id } // 각 항목의 상태는 목록에 있는 항목의 위치를 기준으로 키가 지정된다.
        ) { task ->
            WellnessTaskItem(
                taskName = task.label,
                checked = task.checked,
                onCheckedChange = { onCheckedTask(task, it) },
                onClose = { onCloseTask(task) })
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WellnessTaskListPreview() {
    // WellnessTasksList(list = listOf())
}