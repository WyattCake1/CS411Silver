package com.rolecall

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rolecall.ui.theme.BlueBox
import com.rolecall.ui.theme.RedStroke
import com.rolecall.ui.theme.RoleCallTheme
import com.rolecall.ui.theme.WhiteText
import okio.IOException


class EditListingsPage : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val listing = intent.getSerializableExtra("listing") as Listing
        val listingData = listing.toList();
        setContent {
            RoleCallTheme {
                RenderEditListingsPage(listingData)
            }
        }
    }
}

@Composable
fun RenderEditListingsPage(listing: List<Any>){
    val mutableListing = remember { mutableStateOf(listing)}

    val isCampaign = mutableListing.value[0] as Boolean
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
            Column(verticalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()){
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
                    RenderEditHeader()
                }
                //Role Limits
                if(isCampaign){
                    RenderRoleLimits(mutableListing)
                } else{
                    RenderRoleSelect(mutableListing)
                }
                //Dropdowns
                RenderDropdownPickers(mutableListing)
                RenderTimePickers(mutableListing)
                RenderSaveListing(mutableListing)
            }
        }
    }
}
@Composable
fun RenderRoleLimits(listing : MutableState<List<Any>>){
    val roles = listOf("Tank","DPS","Face","Healer","Support")
    val rolesLimit = listing.value[7] as Map<String,Int>
    Box(
        Modifier
            .fillMaxWidth()
            .padding(24.dp)){
        Column(verticalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()){
            Text("Role Limits", color = WhiteText, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(24.dp), modifier = Modifier.fillMaxWidth()) {
                for (role in roles.take(3)) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text=role,color= WhiteText)
                        RenderRoleSelector(role, rolesLimit[role] ?: 0, {newValue -> setRoleValue(listing,role,newValue)})
                    }
                }
            }
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(24.dp, Alignment.CenterHorizontally), modifier = Modifier.fillMaxWidth()) {
                for (role in roles.drop(3)) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text=role,color= WhiteText)
                        RenderRoleSelector(role, rolesLimit[role] ?: 0, {newValue -> setRoleValue(listing,role,newValue)})
                    }
                }
            }
            Divider(color = Color.Gray, thickness = 1.dp)
        }
    }
}
@Composable
fun RenderRoleSelect(listing : MutableState<List<Any>>){
    println(listing)
    val roles = listOf("Tank","DPS","Face","Healer","Support")
    val rolesLimit = listing.value[7] as Map<String,Int>
    Box(
        Modifier
            .fillMaxWidth()
            .padding(24.dp)){
        Column(verticalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()){
            Text("Role Selection", color = WhiteText, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(16.dp), modifier = Modifier.fillMaxWidth()) {
                for (role in roles ){
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text=role,color= WhiteText)
                        RadioButton(
                            selected = getRoleValue(listing.value[7] as Map<String,Int>,role) == 1,
                            onClick = {
                                val newValue = if (getRoleValue(listing.value[7] as Map<String,Int>,role) == 1) 0 else 1
                                setRoleValue(listing, role, newValue)
                        })
                    }
                }
            }
            Divider(color = Color.Gray, thickness = 1.dp)
        }
    }
}
@Composable
fun RenderRoleSelector(role: String, value: Int, onValueChange: (Int) -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.padding(horizontal = 8.dp)
    ) {
        IconButton(
            onClick = { if (value > 0) onValueChange(value - 1) },
            modifier = Modifier.size(25.dp)
        ) {
            Icon(
                imageVector = Icons.Default.KeyboardArrowDown,
                contentDescription = "Decrease Role",
                tint = Color.White
            )
        }
        Text(
            text = "$value",
            color = WhiteText,
            fontSize = 16.sp
        )
        IconButton(
            onClick = { onValueChange(value + 1) },
            modifier = Modifier.size(25.dp)
        ) {
            Icon(
                imageVector = Icons.Default.KeyboardArrowUp,
                contentDescription = "Increase Role",
                tint = Color.White
            )
        }
    }
}

@Composable
fun RenderEditHeader(){
    Text(
        text = "Edit Listing",
        fontSize =  48.sp,
        color = WhiteText,
        modifier = Modifier.padding(16.dp)
    )
}

@Composable
fun RenderDropdownPickers(listing : MutableState<List<Any>>) {
    val maxDistances = listOf("5","10","25","50","100")
    val locations = listOf("In-person", "Online")
    val difficulties = listOf("First Game", "Casual", "Intermediate", "Advanced")
    val days = listOf("Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday")

    Column(verticalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
        //RenderDropdownPicker(listing,maxDistances,)
        RenderDropdownPicker(listing,locations,2,"Location")
        RenderDropdownPicker(listing,difficulties,6, "Difficulty")
        Divider(color = Color.Gray, thickness = 1.dp)
        RenderDropdownPicker(listing,days,3, "Day")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RenderDropdownPicker(listing : MutableState<List<Any>>, attributeChoices: List<String>, index: Int, label :String){
    println(listing.value[index])
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf(listing.value[index].toString())}
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(horizontal = 8.dp))
        {
            Text(label, color = WhiteText,fontWeight = FontWeight.Bold)
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = it },
            ) {
                Text(
                    text = selectedText.toString(),
                    modifier = Modifier.menuAnchor().clickable { expanded = true }
                        .padding(horizontal = 16.dp, vertical = 10.dp),
                    color = WhiteText
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    attributeChoices.forEach { selectedValue ->
                        DropdownMenuItem(
                            text = { Text(selectedValue) },
                            onClick = {
                                selectedText = selectedValue
                                expanded = false
                                listing.value = listing.value.toMutableList().apply{
                                    this[index] = selectedValue
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun RenderSaveListing(listing: MutableState<List<Any>>){
    val context = LocalContext.current
    val id = listing.value[8].toString()
    Button(onClick = {
        saveListing(listing)
        val intent = Intent(context, UserListingsPage::class.java).apply{
            putExtra("userId", id)
        }
        context.startActivity(intent)
    },  modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)){
        Text(text = "Save Updated Listing")
    }
}

fun saveListing(updatedListing : MutableState<List<Any>>){
    val listing = Listing();
    listing.campaign = updatedListing.value[0].toString() == "true"
    listing.gameName = updatedListing.value[1].toString()
    listing.environment = updatedListing.value[2].toString()
    listing.day = updatedListing.value[3].toString()
    listing.startTime = updatedListing.value[4].toString()
    listing.endTime = updatedListing.value[5].toString()
    listing.difficulty = updatedListing.value[6].toString()
    val input = updatedListing.value[7].toString()
    val map = HashMap<String, Int>()
    val entries = input.removeSurrounding("{", "}").split(", ")
    for (entry in entries) {
        val (key, value) = entry.split("=")
        map[key] = value.toInt()
    }
    listing.role = map
    listing.userProfileId = updatedListing.value[8].toString()
    val flaskClient = FlaskClient();
    flaskClient.updateListing(listing, object : ResponseCallback {
        override fun onSuccess(response: String) {
            println("Successfully Updated Listing: $response")
        }

        override fun onError(e: IOException) {
            println("Failed to Update Listing: ${e.message}")
        }
    })
}

@Composable
fun RenderTimePickers(listing : MutableState<List<Any>>) {
    var initalStartTime by remember { mutableStateOf(listing.value[4].toString())}
    var initalEndTime by remember { mutableStateOf(listing.value[5].toString())}

    fun updateTime(time: String, timeType: String) {
        if (timeType == "Start") {
            listing.value = listing.value.toMutableList().apply { this[4] = time }
        } else {
            listing.value = listing.value.toMutableList().apply { this[5] = time }
        }
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        contentAlignment = Alignment.Center
    ) {
        Column() {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
            {
                Text("Start Time", color = WhiteText, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.padding(8.dp))
                BasicTextField(
                    value = initalStartTime,
                    onValueChange = { newTime ->
                        initalStartTime = newTime;
                        updateTime(newTime, "Start")
                    },
                    textStyle = TextStyle(
                        color = WhiteText
                    ),
                    singleLine = true
                )
            }
            Spacer(modifier = Modifier.padding(16.dp)) // Add some space between fields
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
            {
                Text("End Time", color = WhiteText, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.padding(12.dp))
                BasicTextField(
                    value = initalEndTime,
                    onValueChange = { newTime ->
                        initalEndTime = newTime;
                        updateTime(newTime, "End")
                    },
                    textStyle = TextStyle(
                        color = WhiteText
                    ),
                    singleLine = true
                )
            }
        }
    }
}



fun getRoleValue(roles: Map<String,Int>, key: String): Int {
    return roles[key] as Int
}

fun setRoleValue(listing: MutableState<List<Any>>, key: String, value: Int) {
    val updatedRoles = (listing.value[7] as Map<String, Int>).toMutableMap()
    updatedRoles[key] = value
    val updatedListing = listing.value.toMutableList()
    updatedListing[7] = updatedRoles
    listing.value = updatedListing
}
