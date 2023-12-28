package com.example.composestudy

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composestudy.ui.theme.ComposeStudyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeStudyTheme {
                //데이터 상태 간직을 위해
                val clickCount : MutableState<Int> = remember{
                    mutableStateOf(0)
                }
                var messageList : SnapshotStateList<Message> = remember { mutableStateListOf<Message>() }
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {


                    Column {
                        Greeting("Android",clickCount.value, onClicked={
                            Log.d("TAG", "onCreate:클릭됨")
                            clickCount.value +=1
                            val newMsg = Message(clickCount.value, "메세지 입니다 ${clickCount.value}")
                            messageList.add(newMsg)
                        })
                        MessageList(messageList , onClicked = {
                            //Log.d("TAG", "클릭됨: ${it.id}")
                            //삭제기능 구현
                            messageList.remove(it)
                        })
                    }

                }
            }
        }
    }
}

@Composable
fun MessageList(messages: List<Message>, onClicked: (Message) -> Unit) {
    Column {
        messages.forEach { message ->
            MessageRow(message, onClicked)
        }
    }
}

@Composable
fun MessageRow(message: Message, onClicked: (Message) -> Unit) {
    Surface(
        modifier = Modifier.padding(16.dp).fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, Color.LightGray),
        shadowElevation = 10.dp
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text("id: ${message.id} / content: ${message.content}")
            Button(onClick = {onClicked(message)}) {
                Text("삭제")
            }
        }
    }

}

@Composable
fun Greeting(name: String,clickCount:Int, modifier: Modifier = Modifier, onClicked:() -> Unit) {
    Column {
        Text(
            "Hello $name!",
            modifier = modifier
        )
        Text("클릭된 카운트: $clickCount")
        Button(onClicked) {
            Text(text="클릭해주세요")
        }
    }
}


//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    ComposeStudyTheme {
//        Greeting("Android",10, onClicked={
//            Log.d("TAG", "onCreate:클릭됨")
//        })
//    }
//}