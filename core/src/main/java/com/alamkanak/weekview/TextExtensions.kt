package com.alamkanak.weekview

import android.graphics.Paint
import android.graphics.Typeface
import android.os.Build
import android.text.*
import android.text.style.StyleSpan
import android.text.style.TypefaceSpan
import com.alamkanak.weekview.base.TextProcessors

internal val CharSequence.processed: CharSequence
    get() = TextProcessors.process(this)


// Converts text to static layout
internal fun CharSequence.toTextLayout(
    textPaint: TextPaint,
    width: Int,
    alignment: Layout.Alignment = Layout.Alignment.ALIGN_NORMAL,
    spacingMultiplier: Float = 1f,
    spacingExtra: Float = 0f,
    includePad: Boolean = false
) = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

    StaticLayout.Builder
        .obtain(this, 0, length, textPaint, width)
        .setAlignment(alignment)
        .setLineSpacing(spacingExtra, spacingMultiplier)
        .setIncludePad(includePad)
        .build()

} else {
    @Suppress("DEPRECATION")
    StaticLayout(this, textPaint, width, alignment, spacingMultiplier, spacingExtra, includePad)
}


internal fun getTestText(): SpannableStringBuilder {

    // Prepare the name of the event.
    val bob = SpannableStringBuilder()
    var len = 0
        bob.append("Test Event")
        bob.setSpan(StyleSpan(Typeface.BOLD), 0, bob.length, 0)
        len = bob.length

    bob.append("\n")
    bob.append("\nEngineer test iddddd")

    // Event Engineer id
    bob.append(
        """
            Engineer Id : test id 555555555555555555555555555
            """.trimIndent()
    )

    return bob
}

internal val StaticLayout.maxLineLength: Float
    get() = (0 until lineCount).map { getLineWidth(it) }.maxOrNull() ?: 0f

internal fun SpannableStringBuilder.build(): SpannableString = SpannableString.valueOf(this)

internal fun CharSequence.semibold() = SpannableString(this).apply {
    setSpan(TypefaceSpan("sans-serif-medium"), 0, length, 0)
}

internal fun ViewState.getTextPaint(event: ResolvedWeekViewEntity): TextPaint {
    val textPaint = TextPaint(if (event.isAllDay) allDayEventTextPaint else eventTextPaint)
    textPaint.textAlign = if (isLtr) Paint.Align.LEFT else Paint.Align.RIGHT

    val textColor = event.style.textColor
    if (textColor != null) {
        textPaint.color = textColor
    }
    return textPaint
}
