package com.osung.compose

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.osung.compose.data.SampleData
import com.osung.compose.ui.theme.MyComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyComposeTheme { // 컴포저블에 Theme를 래핑하여 앱 전반에 일관성을 보장한다. (머티리얼 디자인 원칙 지원)
                Surface {
                    Conversation(message = SampleData.conversationSample)
                }
            }
        }
    }
}

data class Message(val author: String, val body: String)

@Composable
fun Conversation(message: List<Message>) {
    LazyColumn {
        items(message) { message ->
            MessageCard(msg = message)
        }
    }
}

@Composable
fun MessageCard(msg: Message) {
    Row(modifier = Modifier.padding(all = 8.dp)) { // 내부 요소들을 수평으로 정렬
        Image(
            painter = painterResource(id = R.drawable.dog),
            contentDescription = "cute dog",
            modifier = Modifier //수정자 : 컴포저블의 크기, 레이아웃, 모양을 변경하거나 요소를 클릭 가능하게 만드는 등의 상위 수준 상호작용을 추가할 수 있다.
                .size(40.dp)
                .clip(shape = CircleShape)
                .border(1.5.dp, MaterialTheme.colors.secondary, CircleShape)
        )
        
        Spacer(modifier = Modifier.width(8.dp)) // 요소 사이의 간격 (margin 역할)

        var isExpanded by remember { mutableStateOf(false) }
        val surfaceColor by animateColorAsState( //컬러 변경 애니메이션
            if (isExpanded) MaterialTheme.colors.primary else MaterialTheme.colors.surface,
        )

        Column(modifier = Modifier.clickable { isExpanded = !isExpanded }) { // 내부 요소들을 수직으로 정렬
            Text(
                text = msg.author,
                color = MaterialTheme.colors.secondaryVariant,
                style = MaterialTheme.typography.subtitle2
            )
            Spacer(modifier = Modifier.height(4.dp))

            Surface(
                shape = MaterialTheme.shapes.medium,
                elevation = 1.dp,
                color = surfaceColor,
                modifier = Modifier.animateContentSize().padding(1.dp) //크기 변경 애니메이션
            ) {
                Text(
                    text = msg.body,
                    modifier = Modifier.padding(4.dp),
                    style = MaterialTheme.typography.body2,
                    maxLines = if (isExpanded) Int.MAX_VALUE else 1
                )
            }
        }
    }
}

@Preview(name = "LightMode", showBackground = true) // 핫 리로드를 보여주기 위한 Annotation, 파라미터가 없는 메소드에만 붙일 수 있다.
@Preview( // 머티리얼 디자인 지원으로 다크모드를 쉽게 대응할 수 있다.
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "DarkMode"
)
@Composable
fun PreviewMessageCard() {
    MyComposeTheme {
        Surface {
            MessageCard(
                msg = Message("Colleague", "Hey, take a look at Jetpack Compose, it's great!")
            )
        }
    }
}

@Preview
@Composable
fun PreviewConversation() {
    MyComposeTheme {
        Surface {
            Conversation(message = SampleData.conversationSample)
        }
    }
}