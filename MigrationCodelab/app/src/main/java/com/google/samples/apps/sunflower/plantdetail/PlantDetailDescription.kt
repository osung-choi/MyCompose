/*
 * Copyright 2020 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.samples.apps.sunflower.plantdetail

import android.content.res.Configuration
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.widget.TextView
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat
import com.google.android.material.composethemeadapter.MdcTheme
import com.google.samples.apps.sunflower.R
import com.google.samples.apps.sunflower.data.Plant
import com.google.samples.apps.sunflower.viewmodels.PlantDetailViewModel

@Composable
fun PlantDetailDescription(plantDetailViewModel: PlantDetailViewModel) {
    val plant by plantDetailViewModel.plant.observeAsState() // LiveData 에서 값 읽어올 때 사용

    plant?.let { PlantDetailContent(plant = it) }

}

@Composable
fun PlantDetailContent(plant: Plant) {
    Surface {
        Column(Modifier.padding(dimensionResource(id = R.dimen.margin_normal))) {
            PlantName(name = plant.name)
            PlantWatering(wateringInterval = plant.wateringInterval)
            PlantDescription(description = plant.description)
        }
    }
}

@Composable
private fun PlantDescription(description: String) {
    val htmlDescription: Spanned = remember(description) {
        HtmlCompat.fromHtml(description, HtmlCompat.FROM_HTML_MODE_COMPACT)
    }

    // AndroidView : 기존의 안드로이드 View를 사용할 수 있다.
    // Compose에서 지원하지 않는 기능(Spanned 등)에 대해 기존의 View를 활용하여 구현할 수 있다.
    // htmlDescription 이 변경되면 AndroidView의 update 콜백이 재구성 된다.
    AndroidView(
        factory = { context ->
            TextView(context).apply {
                movementMethod = LinkMovementMethod.getInstance()
            }
        },
        update = {
            it.text = htmlDescription
        }
    )
}

@Composable
private fun PlantName(name: String) {
    Text(
        text = name,
        style = MaterialTheme.typography.h5,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = dimensionResource(id = R.dimen.margin_small))
            .wrapContentWidth(Alignment.CenterHorizontally)
    )
}

@Composable
private fun PlantWatering(wateringInterval: Int) {
    Column(Modifier.fillMaxWidth()) {
        // Modifier 재사용
        val centerWithPaddingModifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = dimensionResource(id = R.dimen.margin_small))
            .wrapContentWidth(Alignment.CenterHorizontally)

        val normalPadding = dimensionResource(id = R.dimen.margin_normal)

        Text(
            text = stringResource(id = R.string.watering_needs_prefix),
            color = MaterialTheme.colors.primaryVariant,
            fontWeight = FontWeight.Bold,
            modifier = centerWithPaddingModifier.padding(top = normalPadding)
        )

        // 지역별 숫자에 따라 문자열 표현 차이 (ex: 미국의 경우 1개는 day, 복수개는 days)
        val wateringIntervalText = LocalContext.current.resources.getQuantityString(
            R.plurals.watering_needs_suffix, wateringInterval, wateringInterval
        )

        Text(
            text = wateringIntervalText,
            modifier = centerWithPaddingModifier.padding(bottom = normalPadding)
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PlantDetailContentPreview() {
    val plant = Plant("id", "Apple", "description", 3, 30, "")
    MdcTheme {
        PlantDetailContent(plant = plant)
    }
}

@Preview
@Composable
private fun PlantNamePreview() {
    MdcTheme {
        PlantName(name = "Apple")
    }
}

@Preview
@Composable
private fun PlantWateringPreview() {
    MdcTheme {
        PlantWatering(wateringInterval = 7)
    }
}

@Preview
@Composable
private fun PlantDescriptionPreview() {
    MdcTheme {
        PlantDescription(description = "HTML<br><br>description")
    }
}