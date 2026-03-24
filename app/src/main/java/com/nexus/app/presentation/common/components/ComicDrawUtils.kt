package com.nexus.app.presentation.common.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nexus.app.presentation.theme.*
import kotlin.math.*

// ═══════════════════════════════════════════════════════════════
// Halftone Dot Overlay — aged newsprint texture
// ═══════════════════════════════════════════════════════════════

@Composable
fun HalftoneOverlay(
    modifier: Modifier = Modifier,
    dotColor: Color = HalftoneBase,
    dotRadius: Float = 2.5f,
    spacing: Float = 14f
) {
    Canvas(modifier = modifier.fillMaxSize()) {
        val cols = (size.width / spacing).toInt() + 1
        val rows = (size.height / spacing).toInt() + 1
        for (row in 0..rows) {
            val offsetX = if (row % 2 == 0) 0f else spacing / 2f
            for (col in 0..cols) {
                drawCircle(
                    color = dotColor,
                    radius = dotRadius,
                    center = Offset(col * spacing + offsetX, row * spacing)
                )
            }
        }
    }
}

// ═══════════════════════════════════════════════════════════════
// Comic Panel — a bordered, slightly rotated container
// ═══════════════════════════════════════════════════════════════

@Composable
fun ComicBookPanel(
    modifier: Modifier = Modifier,
    rotationDeg: Float = 0f,
    borderWidth: Dp = 3.dp,
    borderColor: Color = PanelBorder,
    backgroundColor: Color = ComicAgedPaper,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier
            .rotate(rotationDeg)
            .clip(RoundedCornerShape(4.dp))
            .background(backgroundColor)
            .border(borderWidth, borderColor, RoundedCornerShape(4.dp))
            .padding(12.dp),
        content = content
    )
}

// ═══════════════════════════════════════════════════════════════
// Speed Lines — radiating action lines from center
// ═══════════════════════════════════════════════════════════════

@Composable
fun SpeedLines(
    modifier: Modifier = Modifier,
    lineColor: Color = SpeedLineColor,
    lineCount: Int = 28,
    animated: Boolean = true
) {
    val infiniteTransition = rememberInfiniteTransition(label = "speed")
    val rotation by if (animated) {
        infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 360f,
            animationSpec = infiniteRepeatable(
                animation = tween(60000, easing = LinearEasing),
                repeatMode = RepeatMode.Restart
            ),
            label = "speedRotation"
        )
    } else {
        remember { mutableFloatStateOf(0f) }
    }

    Canvas(modifier = modifier.fillMaxSize()) {
        val cx = size.width / 2f
        val cy = size.height / 2f
        val maxRadius = maxOf(size.width, size.height)
        val angleStep = 360f / lineCount

        rotate(rotation, pivot = Offset(cx, cy)) {
            for (i in 0 until lineCount) {
                val angle = Math.toRadians((i * angleStep).toDouble())
                val innerR = maxRadius * 0.22f
                val outerR = maxRadius * 0.9f
                val alpha = if (i % 3 == 0) 0.12f else 0.06f
                val strokeW = if (i % 2 == 0) 1.8f else 1.0f
                drawLine(
                    color = lineColor.copy(alpha = alpha),
                    start = Offset(
                        cx + innerR * cos(angle).toFloat(),
                        cy + innerR * sin(angle).toFloat()
                    ),
                    end = Offset(
                        cx + outerR * cos(angle).toFloat(),
                        cy + outerR * sin(angle).toFloat()
                    ),
                    strokeWidth = strokeW
                )
            }
        }
    }
}

// ═══════════════════════════════════════════════════════════════
// Starburst / Explosion Shape — POW! style background
// ═══════════════════════════════════════════════════════════════

@Composable
fun StarburstBackground(
    modifier: Modifier = Modifier,
    fillColor: Color = ComicYellow,
    outlineColor: Color = ComicBlack,
    points: Int = 14,
    innerRadiusRatio: Float = 0.55f
) {
    Canvas(modifier = modifier) {
        val cx = size.width / 2f
        val cy = size.height / 2f
        val outerR = minOf(cx, cy)
        val innerR = outerR * innerRadiusRatio
        val angleStep = PI / points

        val path = Path()
        for (i in 0 until points * 2) {
            val r = if (i % 2 == 0) outerR else innerR
            val angle = i * angleStep - PI / 2
            val x = cx + r * cos(angle).toFloat()
            val y = cy + r * sin(angle).toFloat()
            if (i == 0) path.moveTo(x, y) else path.lineTo(x, y)
        }
        path.close()

        drawPath(path, color = fillColor)
        drawPath(path, color = outlineColor, style = Stroke(width = 3f))
    }
}

// ═══════════════════════════════════════════════════════════════
// Action Cloud (POW / BANG bubble)
// ═══════════════════════════════════════════════════════════════

@Composable
fun ActionCloud(
    modifier: Modifier = Modifier,
    cloudColor: Color = Color.White,
    outlineColor: Color = ComicBlack,
    blobCount: Int = 10
) {
    Canvas(modifier = modifier) {
        val cx = size.width / 2f
        val cy = size.height / 2f
        val rx = size.width * 0.38f
        val ry = size.height * 0.35f

        val path = Path()
        for (i in 0 until blobCount) {
            val angle = (2 * PI * i / blobCount)
            val blobX = cx + rx * cos(angle).toFloat()
            val blobY = cy + ry * sin(angle).toFloat()
            val blobR = minOf(size.width, size.height) * 0.16f
            path.addOval(
                oval = androidx.compose.ui.geometry.Rect(
                    blobX - blobR, blobY - blobR,
                    blobX + blobR, blobY + blobR
                )
            )
        }
        // Center fill
        path.addOval(
            oval = androidx.compose.ui.geometry.Rect(
                cx - rx * 0.7f, cy - ry * 0.7f,
                cx + rx * 0.7f, cy + ry * 0.7f
            )
        )
        drawPath(path, color = cloudColor)
        drawPath(path, color = outlineColor, style = Stroke(width = 2.5f))
    }
}

// ═══════════════════════════════════════════════════════════════
// Comic Lightning Bolt Progress Bar
// ═══════════════════════════════════════════════════════════════

@Composable
fun ComicProgressBolt(
    progress: Float,
    modifier: Modifier = Modifier,
    trackColor: Color = ComicPaperDark,
    fillColor: Color = NexusRed
) {
    Canvas(modifier = modifier.fillMaxWidth().height(8.dp)) {
        val w = size.width
        val h = size.height
        // Track
        drawRoundRect(
            color = trackColor,
            cornerRadius = androidx.compose.ui.geometry.CornerRadius(h / 2, h / 2),
            size = Size(w, h)
        )
        // Filled portion with jagged edge
        val filledW = w * progress.coerceIn(0f, 1f)
        if (filledW > 0f) {
            val path = Path().apply {
                moveTo(0f, 0f)
                lineTo(filledW - 6f, 0f)
                lineTo(filledW, h / 2f)
                lineTo(filledW - 6f, h)
                lineTo(0f, h)
                close()
            }
            drawPath(path, color = fillColor)
        }
    }
}

// ═══════════════════════════════════════════════════════════════
// Jagged Panel Border Modifier (draw-behind)
// ═══════════════════════════════════════════════════════════════

fun Modifier.jaggedBorder(
    color: Color = PanelBorder,
    strokeWidth: Float = 4f,
    jagSize: Float = 5f
): Modifier = this.drawBehind {
    val w = size.width
    val h = size.height
    val path = Path().apply {
        moveTo(0f, 0f)
        // Top edge
        var x = 0f
        while (x < w) {
            lineTo(x + jagSize, -jagSize * 0.3f)
            lineTo(x + jagSize * 2, 0f)
            x += jagSize * 2
        }
        lineTo(w, 0f)
        // Right edge
        var y = 0f
        while (y < h) {
            lineTo(w + jagSize * 0.3f, y + jagSize)
            lineTo(w, y + jagSize * 2)
            y += jagSize * 2
        }
        lineTo(w, h)
        // Bottom edge
        x = w
        while (x > 0) {
            lineTo(x - jagSize, h + jagSize * 0.3f)
            lineTo(x - jagSize * 2, h)
            x -= jagSize * 2
        }
        lineTo(0f, h)
        // Left edge
        y = h
        while (y > 0) {
            lineTo(-jagSize * 0.3f, y - jagSize)
            lineTo(0f, y - jagSize * 2)
            y -= jagSize * 2
        }
        close()
    }
    drawPath(path, color = color, style = Stroke(width = strokeWidth))
}

// ═══════════════════════════════════════════════════════════════
// Animated Panel Pop-In
// ═══════════════════════════════════════════════════════════════

@Composable
fun animatedPanelScale(delayMs: Int = 0): State<Float> {
    val scale = remember { Animatable(0f) }
    LaunchedEffect(Unit) {
        kotlinx.coroutines.delay(delayMs.toLong())
        scale.animateTo(
            targetValue = 1f,
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessMedium
            )
        )
    }
    return scale.asState()
}
