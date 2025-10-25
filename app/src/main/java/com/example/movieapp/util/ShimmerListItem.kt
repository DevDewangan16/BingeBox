package com.example.movieapp.util

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun shimmerBrush(): Brush {
    val colors = listOf(
        Color(0xFF2F2F2F),
        Color(0xFF3F3F3F),
        Color(0xFF2F2F2F),
    )

    val transition = rememberInfiniteTransition(label = "shimmer")
    val translateAnim = transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "shimmer"
    )

    return Brush.linearGradient(
        colors = colors,
        start = Offset(translateAnim.value, translateAnim.value),
        end = Offset(translateAnim.value + 300f, translateAnim.value + 300f)
    )
}

@Composable
fun HomeShimmerEffect(modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxSize()) {
        // Featured shimmer
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(500.dp)
                .background(shimmerBrush())
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Category title shimmer
        Box(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .width(150.dp)
                .height(24.dp)
                .background(shimmerBrush(), RoundedCornerShape(4.dp))
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Movie cards shimmer
        Row(
            modifier = Modifier.padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            repeat(3) {
                Box(
                    modifier = Modifier
                        .width(140.dp)
                        .height(240.dp)
                        .background(shimmerBrush(), RoundedCornerShape(8.dp))
                )
            }
        }
    }
}

@Composable
fun DetailsShimmerEffect(modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxSize()) {
        // Hero image shimmer
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
                .background(shimmerBrush())
        )

        Column(modifier = Modifier.padding(16.dp)) {
            // Title shimmer
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .height(32.dp)
                    .background(shimmerBrush(), RoundedCornerShape(4.dp))
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Meta info shimmer
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Box(
                    modifier = Modifier
                        .width(60.dp)
                        .height(20.dp)
                        .background(shimmerBrush(), RoundedCornerShape(4.dp))
                )
                Box(
                    modifier = Modifier
                        .width(80.dp)
                        .height(20.dp)
                        .background(shimmerBrush(), RoundedCornerShape(4.dp))
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Genres shimmer
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                repeat(3) {
                    Box(
                        modifier = Modifier
                            .width(70.dp)
                            .height(28.dp)
                            .background(shimmerBrush(), RoundedCornerShape(16.dp))
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Overview title shimmer
            Box(
                modifier = Modifier
                    .width(100.dp)
                    .height(20.dp)
                    .background(shimmerBrush(), RoundedCornerShape(4.dp))
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Overview text shimmer
            repeat(5) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(16.dp)
                        .background(shimmerBrush(), RoundedCornerShape(4.dp))
                )
                Spacer(modifier = Modifier.height(6.dp))
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Button shimmer
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .background(shimmerBrush(), RoundedCornerShape(8.dp))
            )
        }
    }
}