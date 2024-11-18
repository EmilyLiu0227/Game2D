package com.example.myapplication

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContentTransitionScope.SlideDirection.Companion.Start
import androidx.compose.foundation.Image
import androidx.compose.foundation.interaction.DragInteraction
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapplication.ui.theme.MyApplicationTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.Button
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.compose.runtime.collectAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.offset

import kotlin.math.abs
class Background(val screenW:Int) {
    var x1 = 0 //背景圖1_x軸
    var x2 = screenW //背景圖2_x軸
    fun Roll(){
        x1 --
        if (abs(x1) > screenW){ //已經移動整張
            x1 = 0
        }
        x2 = x1 + screenW
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)
                    val screenW = resources.displayMetrics.widthPixels
                    val screenH = resources.displayMetrics.heightPixels
                    val game = Game(GlobalScope,screenW, screenH)
                    Start(m = Modifier.padding(innerPadding), game)

                }
            }
        }
    }
}

@Composable
fun Start(m: Modifier, game:Game){
    var msg by remember { mutableStateOf("遊戲開始") }
    val counter by game.state.collectAsState()
    var counter2 by remember { mutableStateOf(0) }

    Image(
        painter = painterResource(id = R.drawable.forest),
        contentDescription = "背景圖",
        contentScale = ContentScale.FillBounds, //縮放符合螢幕寬度
        modifier = Modifier
            .offset { IntOffset(game.background.x1, 0) }
    )
    Image(
        painter = painterResource(id = R.drawable.forest),
        contentDescription = "背景圖2",
        contentScale = ContentScale.FillBounds, //縮放符合螢幕寬度
        modifier = Modifier
            .offset { IntOffset(game.background.x2, 0) }
    )


    Row {
        Button(
            onClick = {
                if (msg=="遊戲開始"){
                    msg = "遊戲暫停"
                    game.Play()
                }
                else{
                    msg = "遊戲開始"
                    game.isPlaying = false
                }


            },
            modifier = m
        ){
            Text(text = msg)
        }
        Text(text = "%.2f 秒".format(counter*.04), modifier = m)


        Button(
            onClick = {
                counter2++

            },
            modifier = m
        ){
            Text(text = "加一")
        }
        Text(text = counter2.toString(), modifier = m)
    }
}