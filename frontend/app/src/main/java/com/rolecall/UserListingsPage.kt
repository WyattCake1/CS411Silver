package com.rolecall

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rolecall.ui.theme.BlueBox
import com.rolecall.ui.theme.RedStroke
import com.rolecall.ui.theme.RoleCallTheme
import com.rolecall.ui.theme.WhiteText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.simple.JSONArray
import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser
import java.io.IOException

class UserListingsPage : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val intent = intent
        val id = intent.extras?.getString("userId") as String
        setContent {
            RoleCallTheme {
                RenderUserListingsPage(id)
            }
        }
    }
}
@Composable
fun RenderUserListingsPage(id : String) {
    val userListings = remember { mutableStateListOf<Listing>() }
    LaunchedEffect(Unit) {
        if(userListings.isEmpty()){
            getListings(id,userListings)
        }
    }
    Render(userListings,id)
}
@Composable
fun Render(userListings: MutableList<Listing>,id : String) {
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
        Scaffold(
            bottomBar = {
                RenderCreateListings(id)
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(gradientBrush)
                    .padding(innerPadding),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                RenderHeader()
                RenderListings(userListings, Integer.parseInt(id))
            }
        }
    }
}
suspend fun getListings(id : String, userListings: MutableList<Listing>) {
    val flaskClient = FlaskClient()
    withContext(Dispatchers.IO){
        //using default user id 1 for time being
        flaskClient.requestUserListings(id,object : ResponseCallback {
            override fun onSuccess(response: String) {
                if(response.trim() != "[]"){
                    val parser = JSONParser()
                    val jsonArray = parser.parse(response.trimIndent()) as JSONArray
                    for(i in 0 until jsonArray.size){
                        val jsonObject = jsonArray[i] as JSONObject
                        val newListing = Listing(jsonObject)
                        userListings.add(newListing)
                    }
                }
            }
            override fun onError(e: IOException?) {
                System.err.println(e?.message)
            }
        })
    }
}

@Composable
fun RenderListings(userListings: List<Listing>, id: Int){
    if (userListings.isEmpty()){
        //clean up this
        Text("You have no listings, get started matching with button below", color = WhiteText)
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            userListings.forEach{
                ListingItem(it, id)
            }
        }
    }

}

@Composable
fun ListingItem(listing: Listing, id: Int){
    val context = LocalContext.current
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(125.dp)
        .padding(4.dp)
        .clip(RoundedCornerShape(16.dp))
        .background(BlueBox)
        .border(4.dp, RedStroke, RoundedCornerShape(16.dp))
    ){
        Box(modifier = Modifier.padding(8.dp)){
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ){
                Row(verticalAlignment = Alignment.CenterVertically){
                    Text(
                        text = listing.gameName,
                        fontSize = 28.sp,
                        color = WhiteText
                    )
                    IconButton(onClick = {
                        val intent = Intent(context, ViewListing::class.java)
                        intent.putExtra("Listing",listing)
                        intent.putExtra("userId", listing.userProfileId)
                        context.startActivity(intent)
                    }){
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = "Listing Details",
                            tint = Color.White
                        )
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    Button(modifier = Modifier.weight(1f),onClick = { }) {
                        Text(
                            text = "Matches",
                            fontSize = 16.sp
                        )
                    }
                    Button(modifier = Modifier.weight(1f),onClick = {

                        // Launch the Chatroom Activity
                        val intent = Intent(context, Chatroom::class.java)
                        // replace the 1 in the next line with the userId
                        intent.putExtra("userId", id)
                        intent.putExtra("campaignId", Integer.parseInt(listing.getListingId()))
                        context.startActivity(intent)

                    }) {
                        Text(
                            text = "Chat",
                            fontSize = 16.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun RenderHeader(){
    Text(
        text = "My Listings",
        fontSize =  48.sp,
        color = WhiteText
    )
}

@Composable
fun RenderCreateListings(id : String){
    val context = LocalContext.current
    Button(onClick = {
        val intent = Intent(context, CreateListing::class.java)
        intent.putExtra("userId", id)
        context.startActivity(intent)
    },  modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)){
        Text(text = "Create New Listing")
    }
}