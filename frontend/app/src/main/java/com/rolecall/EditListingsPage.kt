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
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rolecall.ui.theme.BlueBox
import com.rolecall.ui.theme.RedStroke
import com.rolecall.ui.theme.RoleCallTheme
import com.rolecall.ui.theme.WhiteText
import androidx.compose.runtime.remember

class EditListingsPage : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val listing = intent.getSerializableExtra("listing") as Listing
        setContent {
            RoleCallTheme {
                RenderEditListingsPage(listing)
            }
        }
    }
}

@Composable
fun RenderEditListingsPage(listing: Listing){
    val mutableListing = remember { mutableStateOf(listing)}
    val isCampaign = listing.isCampaign;
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
                    RenderRoleLimits(mutableListing)
                } else{
                    RenderRoleSelect(mutableListing)
                }
            }
        }
    }
}
@Composable
fun RenderRoleLimits(listing : MutableState<Listing>){
    val roles = listOf("Tank","DPS","Face","Healer","Support")
    Box(Modifier.fillMaxWidth().padding(24.dp)){
        Column(verticalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()){
            Text("Role Limits", color = WhiteText, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(24.dp), modifier = Modifier.fillMaxWidth()) {
                for (role in roles.take(3)) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text=role,color= WhiteText)
                        RenderRoleSelector()
                    }
                }
            }
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(24.dp, Alignment.CenterHorizontally), modifier = Modifier.fillMaxWidth()) {
                for (role in roles.drop(3)) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text=role,color= WhiteText)
                        RenderRoleSelector()
                    }
                }
            }
            Divider(color = Color.Gray, thickness = 1.dp)
        }
    }
}
@Composable
fun RenderRoleSelect(listing : MutableState<Listing>){
    val roles = listOf("Tank","DPS","Face","Healer","Support")
    Box(Modifier.fillMaxWidth().padding(24.dp)){
        Column(verticalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()){
            Text("Role Limits", color = WhiteText, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(16.dp), modifier = Modifier.fillMaxWidth()) {
                for (role in roles ){
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text=role,color= WhiteText)
                        RadioButton( selected = listing.value.getRoleValue(role) == 1, onClick = {
                            println(listing.value.getRoleValue(role))
                            listing.value.setRoleValue(role, if (listing.value.getRoleValue(role) == 1) 0 else 1)
                        })
                    }
                }
            }
            Divider(color = Color.Gray, thickness = 1.dp)
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
