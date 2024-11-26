package com.rolecall

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rolecall.ui.theme.BlueBox
import com.rolecall.ui.theme.RedStroke
import com.rolecall.ui.theme.RoleCallTheme
import com.rolecall.ui.theme.WhiteText


class EditListingsPage : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val listing = intent.getSerializableExtra("listing") as Listing
        setContent {
            RoleCallTheme {
                RenderEditListingsPage()
            }
        }
    }
}

@Preview
@Composable
fun RenderEditListingsPage(){
    val listing : Listing
    val isCampaign = true
    val gradientBrush = Brush.linearGradient(
        colors = listOf(
            Color(0xFF067E8E),
            Color(0xFF43EBC4),
            Color(0xFF46D9C2)
        ),
        start = Offset(0f,0f),
        end = Offset(0f,1000f)
    )
    Box(modifier = Modifier
        .fillMaxSize()
        .background(gradientBrush)) {
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(BlueBox)
            .border(4.dp, RedStroke))
        {
            Column {
                if(isCampaign){
                    RenderRoleLimits()
                } else{
                }
            }
        }
    }
}
@Composable
fun RenderRoleLimits(){
    val roles = listOf("Tank","DPS","Face","Healer","Support")
    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(16.dp), modifier = Modifier.fillMaxWidth()) {
        for (role in roles) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text=role,color= WhiteText)
                RenderRoleSelector()
            }
        }
    }
}

@Composable
fun RenderRoleSelector(){
    Row(verticalAlignment = Alignment.CenterVertically) {
        IconButton( onClick = {}, modifier = Modifier.size(25.dp)) {
            Icon(imageVector = Icons.Default.KeyboardArrowDown,
                contentDescription = "Increase Roles",
                tint = Color.White
            )
        }
        Text(text = "Num", color = WhiteText)
        IconButton( onClick = {}, modifier = Modifier.size(25.dp)) {
            Icon(imageVector = Icons.Default.KeyboardArrowUp,
                contentDescription = "Increase Roles",
                tint = Color.White
            )
        }
    }
}

@Composable
fun RenderEditHeader(){

}
