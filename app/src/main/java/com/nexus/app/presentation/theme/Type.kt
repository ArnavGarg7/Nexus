package com.nexus.app.presentation.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.nexus.app.R

// ── Comic Title Font (Bangers – bold, blocky action lettering) ──
val NexusDisplayFont = FontFamily(Font(R.font.bangers_regular, FontWeight.Bold))

// ── Body Font (Inter – clean modern body) ──
val NexusBodyFont = FontFamily(Font(R.font.inter_regular, FontWeight.Normal))

// ── Hand-Lettered Comic Font (Comic Neue – hand-written feel) ──
val ComicHandFont = FontFamily(Font(R.font.comic_neue_bold, FontWeight.Bold))

val NexusTypography = Typography(
    displayLarge   = TextStyle(fontFamily = NexusDisplayFont, fontWeight = FontWeight.Bold, fontSize = 48.sp, letterSpacing = 2.sp),
    displayMedium  = TextStyle(fontFamily = NexusDisplayFont, fontWeight = FontWeight.Bold, fontSize = 36.sp, letterSpacing = 1.5.sp),
    displaySmall   = TextStyle(fontFamily = NexusDisplayFont, fontWeight = FontWeight.Bold, fontSize = 28.sp, letterSpacing = 1.sp),
    headlineLarge  = TextStyle(fontFamily = NexusBodyFont, fontWeight = FontWeight.SemiBold, fontSize = 24.sp),
    headlineMedium = TextStyle(fontFamily = NexusBodyFont, fontWeight = FontWeight.SemiBold, fontSize = 20.sp),
    headlineSmall  = TextStyle(fontFamily = NexusBodyFont, fontWeight = FontWeight.SemiBold, fontSize = 18.sp),
    bodyLarge      = TextStyle(fontFamily = NexusBodyFont, fontWeight = FontWeight.Normal, fontSize = 16.sp, lineHeight = 24.sp),
    bodyMedium     = TextStyle(fontFamily = NexusBodyFont, fontWeight = FontWeight.Normal, fontSize = 14.sp, lineHeight = 20.sp),
    bodySmall      = TextStyle(fontFamily = NexusBodyFont, fontWeight = FontWeight.Normal, fontSize = 12.sp, lineHeight = 16.sp),
    labelLarge     = TextStyle(fontFamily = NexusBodyFont, fontWeight = FontWeight.Medium, fontSize = 14.sp),
    labelMedium    = TextStyle(fontFamily = NexusBodyFont, fontWeight = FontWeight.Medium, fontSize = 12.sp),
    labelSmall     = TextStyle(fontFamily = NexusBodyFont, fontWeight = FontWeight.Medium, fontSize = 10.sp),
)
