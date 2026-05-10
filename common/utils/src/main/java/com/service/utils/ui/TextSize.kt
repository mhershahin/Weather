package com.service.utils.ui

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

data class TextSize(
    val eight: TextUnit = 8.sp,
    val tenSp: TextUnit = 10.sp,
    val elevenSp: TextUnit = 11.sp,
    val twelveSp: TextUnit = 12.sp,
    val thirteenSp: TextUnit = 13.sp,
    val fourteenSp: TextUnit = 14.sp,
    val fifteenSp: TextUnit = 15.sp,
    val sixteenSp: TextUnit = 16.sp,
    val seventeenSp: TextUnit = 17.sp,
    val eighteenSp: TextUnit = 18.sp,
    val twentySp: TextUnit = 20.sp,
    val twentyFourSp: TextUnit = 24.sp,
    val twentyFiveSp: TextUnit = 25.sp,
    val fortySp: TextUnit = 40.sp
)

val LocalTextSize = compositionLocalOf { TextSize() }