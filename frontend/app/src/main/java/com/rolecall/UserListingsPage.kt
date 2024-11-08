package com.rolecall

import androidx.compose.foundation.background
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.dp


@Preview(showBackground = true)
@Composable
fun UserListingsPage() {
    val userListings = remember { mutableListOf<Listing>() }



    getListings(userListings)

    Scaffold{ innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            RenderHeader()
            RenderListings(userListings)
        }
    }
}
fun getListings(userListings: MutableList<Listing>) {
    userListings.add(Listing(1, "Listing 1"))
    userListings.add(Listing(2, "Listing 2"))
    userListings.add(Listing(3, "Listing 3"))
}

@Composable
fun RenderListings(userListings: List<Listing>){
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
                        fontSize = 32.sp,
                    )
                    IconButton(onClick = { }){
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = "Listing Details",
                            tint = Color.White
                        )
                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth().padding(start = 16.dp, end = 16.dp),
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
