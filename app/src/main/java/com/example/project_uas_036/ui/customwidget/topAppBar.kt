package com.example.project_uas_036.ui.customwidget

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.offset
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoustumeTopAppBar(
    title: String,
    canNavigateBack: Boolean,
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    navigateUp: () -> Unit = {},
    onRefresh: () -> Unit = {},
) {
    // Define the custom text style
    val customTextStyle = TextStyle(
        fontFamily = FontFamily.SansSerif, // You can replace this with a custom font family
        fontWeight = FontWeight.Bold, // Set the font weight
        fontSize = 23.sp, // Set the font size
        color = Color.Black // Set the font color
    )
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = title,
                style = customTextStyle
            )
        },
        actions = {
            Icon(imageVector = Icons.Default.Refresh, contentDescription = "", modifier = modifier.clickable {
                onRefresh()
            })
        },
        modifier = modifier,
        scrollBehavior = scrollBehavior,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                }
            }
        }
    )

}
