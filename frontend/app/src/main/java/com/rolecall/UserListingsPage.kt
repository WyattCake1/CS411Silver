package com.rolecall

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.rolecall.ui.theme.RoleCallTheme
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

class UserListingsPage : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RoleCallTheme {
                RenderUserListingsPage()
            }
        }
    }
}
@Composable
fun RenderUserListingsPage() {
    val userListings = remember { mutableStateListOf<Listing>() }
    LaunchedEffect(Unit) {
        if(userListings.isEmpty()){
            getListings(userListings)
        }
    }
    Render(userListings)
}
@Composable
fun Render(userListings: MutableList<Listing>) {
    Scaffold (
        bottomBar = {
            RenderCreateListings()
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            RenderHeader()
            RenderListings(userListings)
        }
    }
}
suspend fun getListings(userListings: MutableList<Listing>) {
    val flaskClient = FlaskClient()
    withContext(Dispatchers.IO){
        flaskClient.requestUserListings(object : ResponseCallback {
            override fun onSuccess(response: String) {
                val listings = response.drop(1).dropLast(3).split("},")
                for (listing in listings) {
                    val newListing = Listing(listing)
                    userListings.add(newListing)
                }
            }

            override fun onError(e: IOException?) {
                System.err.println(e?.message)
            }
        })
    }
}

@Composable
fun RenderListings(userListings: List<Listing>){
    if (userListings.isEmpty()){
        //clean up this
        Text("You have no listings, get started matching with button below")
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            userListings.forEach{
                ListingItem(it)
            }
        }
    }

}

@Composable
fun ListingItem(listing: Listing){
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(125.dp)
        .padding(4.dp)
        .clip(RoundedCornerShape(16.dp))
        .background(Color(0xFF004AAD))){
        Box(modifier = Modifier.padding(8.dp)){
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ){
                Row(verticalAlignment = Alignment.CenterVertically){
                    Text(
                        text = listing.name,
                        fontSize = 28.sp,
                    )
                    IconButton(onClick = {
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
                    Button(modifier = Modifier.weight(1f),onClick = { }) {
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
        fontSize =  48.sp
    )
}

@Composable
fun RenderCreateListings(){
    val context = LocalContext.current
    Button(onClick = {
        val intent = Intent(context, CreateListing::class.java)
        context.startActivity(intent)
    },  modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)){
        Text(text = "Create New Listing")
    }
}