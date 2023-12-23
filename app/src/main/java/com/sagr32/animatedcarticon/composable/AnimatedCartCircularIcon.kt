package com.sagr32.animatedcarticon.composable

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.sagr32.animatedcarticon.R
import com.sagr32.animatedcarticon.ui.theme.PrimaryColor
import kotlinx.coroutines.launch


@Composable
fun AnimatedCartCircularIcon(
    modifier: Modifier = Modifier,
    onTap: () -> Unit
) {
    var flag by remember { mutableStateOf(true) }

    val context = LocalContext.current
    val isChecked = remember {
        mutableStateOf(false)
    }
    val resourceId =
        remember(flag) { if (flag) R.drawable.shopping_cart else R.drawable.check }
    val scope = rememberCoroutineScope()
    val rotation = remember { Animatable(0f) }
    Box(
        modifier = Modifier
            .padding(8.dp)
            .size(32.dp)
            .clip(CircleShape)
            .background(color = PrimaryColor)
            .clickable {
                if (!isChecked.value) {
                    scope.launch {
                        rotation.animateTo(
                            targetValue = -45f,
                            animationSpec = tween(500, easing = LinearEasing)
                        )
                        flag = !flag
                        rotation.animateTo(
                            targetValue = 45f,
                            animationSpec = tween(500, easing = LinearEasing)
                        )
                        rotation.animateTo(
                            targetValue = 0f,
                            animationSpec = tween(500, easing = LinearEasing)
                        )
                        rotation.snapTo(0f)
                        isChecked.value = !isChecked.value
                        onTap()
                    }
                }
            },
        content = {
            Image(
                modifier = Modifier
                    .padding(
                        if (!flag)
                            4.dp else 8.dp
                    )
                    .size(32.dp)
                    .rotate(rotation.value),
                painter = painterResource(id = resourceId),
                colorFilter = ColorFilter.tint(
                    color = Color.White
                ),
                contentDescription = "avatar",
                contentScale = ContentScale.Crop,
            )
        })
}