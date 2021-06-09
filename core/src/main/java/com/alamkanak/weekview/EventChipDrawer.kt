package com.alamkanak.weekview

import android.graphics.*
import android.text.Layout
import android.text.SpannableStringBuilder
import android.text.StaticLayout
import android.text.TextUtils
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan

internal class EventChipDrawer(
    private val viewState: ViewState
) {

    private val dragShadow: Int by lazy {
        Color.parseColor("#757575")
    }

    private val backgroundPaint = Paint()
    private val borderPaint = Paint()

    private val patternPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    internal fun draw(
        eventChip: EventChip,
        canvas: Canvas,
        textLayout: StaticLayout?
    ) = with(canvas) {
        val entity = eventChip.event
        val bounds = eventChip.bounds
        val cornerRadius = (entity.style.cornerRadius ?: viewState.eventCornerRadius).toFloat()

        val isBeingDragged = entity.id == viewState.dragState?.eventId
        updateBackgroundPaint(entity, isBeingDragged, backgroundPaint)
        drawRoundRect(bounds, cornerRadius, cornerRadius, backgroundPaint)

        val pattern = entity.style.pattern
        if (pattern != null) {
            drawPattern(
                pattern = pattern,
                bounds = eventChip.bounds,
                isLtr = viewState.isLtr,
                paint = patternPaint
            )
        }

        val borderWidth = entity.style.borderWidth
        if (borderWidth != null && borderWidth > 0) {
            updateBorderPaint(entity, borderPaint)
            val borderBounds = bounds.insetBy(borderWidth / 2f)
            drawRoundRect(borderBounds, cornerRadius, cornerRadius, borderPaint)
        }

        if (entity.isMultiDay && entity.isNotAllDay) {
            drawCornersForMultiDayEvents(eventChip, cornerRadius)
        }

        if (textLayout != null) {
            drawEventTitle(eventChip, textLayout)
        }

    }

/*    private fun drawEventTitle(
        event: WeekViewEntity.Event,
        rect: RectF,
        canvas: Canvas,
        originalTop: Float,
        originalLeft: Float
    ) {

        // Prepare the name of the event.
        val bob = SpannableStringBuilder()
        var len = 0
        if (event.getName() != null) {
            bob.append(event.getEventName())
            bob.setSpan(StyleSpan(Typeface.BOLD), 0, bob.length, 0)
            len = bob.length
        }

        // Event Engineer id
        bob.append(
            """


            Engineer Id : ${event.getEngineerName()}
            """.trimIndent()
        )
        bob.setSpan(RelativeSizeSpan(1f), len, bob.length, 0)
        len = bob.length


        // Event Address
        if (event.getLocation() != null) {
            len = bob.length
            bob.append("\n\n").append(event.getLocation())
        }
        val fcs_subtitle = ForegroundColorSpan(Color.rgb(151, 151, 151))
        bob.setSpan(fcs_subtitle, len, bob.length, 0)
        bob.append("\n\n")
        //bob.append("Estimate   ");
        len = bob.length
        when (event.getJobEventType()) {
            1 -> {

                // NORMAL_EVENT
                setEventsIconsBasedOnType(
                    bob, R.drawable.ic_job_grey, R.drawable.ic_status_red,
                    "Job", "Status"
                )
            }
            2 -> {

                // ESTIMATE_JOB_EVENT Event
                setEventsIconsBasedOnType(
                    bob, R.drawable.ic_estimate_grey_e, R.drawable.ic_status_blue,
                    "Estimate", "Status"
                )
            }
            3 -> {

                // NORMAL_JOB_EVENT
                setEventsIconsBasedOnType(
                    bob, R.drawable.ic_estimate_grey_e, R.drawable.ic_status_orange,
                    "Estimate", "Status"
                )
            }
        }
        val availableHeight = (rect.bottom - originalTop - mEventPadding * 2) as Int
        val availableWidth = (rect.right - originalLeft - mEventPadding * 2) as Int
        mEventTextPaint.setColor(Color.BLACK)
        // Get text dimensions.
        var textLayout = StaticLayout(
            bob,
            mEventTextPaint,
            availableWidth,
            Layout.Alignment.ALIGN_NORMAL,
            1.0f,
            0.0f,
            false
        )
        val lineHeight = textLayout.height / textLayout.lineCount
        if (availableHeight >= lineHeight) {
            // Calculate available number of line counts.
            var availableLineCount = availableHeight / lineHeight
            do {
                // Ellipsize text to fit into event rect.
                textLayout = StaticLayout(
                    TextUtils.ellipsize(
                        bob,
                        mEventTextPaint,
                        (availableLineCount * availableWidth).toFloat(),
                        TextUtils.TruncateAt.END
                    ),
                    mEventTextPaint,
                    (rect.right - originalLeft - mEventPadding * 2) as Int,
                    Layout.Alignment.ALIGN_NORMAL,
                    1.0f,
                    0.0f,
                    false
                )

                // Reduce line count.
                availableLineCount--

                // Repeat until text is short enough.
            } while (textLayout.height > availableHeight)

            // Draw text.
            canvas.save()
            canvas.translate(originalLeft + mEventPadding, originalTop + mEventPadding)
            textLayout.draw(canvas)
            canvas.restore()
        }
    }*/


    private fun Canvas.drawCornersForMultiDayEvents(
        eventChip: EventChip,
        cornerRadius: Float
    ) {
        val event = eventChip.event
        val bounds = eventChip.bounds

        val isBeingDragged = event.id == viewState.dragState?.eventId
        updateBackgroundPaint(event, isBeingDragged, backgroundPaint)

        if (eventChip.startsOnEarlierDay) {
            val topRect = RectF(bounds)
            topRect.bottom = topRect.top + cornerRadius
            drawRect(topRect, backgroundPaint)
        }

        if (eventChip.endsOnLaterDay) {
            val bottomRect = RectF(bounds)
            bottomRect.top = bottomRect.bottom - cornerRadius
            drawRect(bottomRect, backgroundPaint)
        }

        if (event.style.borderWidth != null) {
            drawMultiDayBorderStroke(eventChip, cornerRadius)
        }
    }

    private fun Canvas.drawMultiDayBorderStroke(
        eventChip: EventChip,
        cornerRadius: Float
    ) {
        val event = eventChip.event
        val bounds = eventChip.bounds

        val borderWidth = event.style.borderWidth ?: 0
        val borderStart = bounds.left + borderWidth / 2
        val borderEnd = bounds.right - borderWidth / 2

        updateBorderPaint(event, backgroundPaint)

        if (eventChip.startsOnEarlierDay) {
            drawVerticalLine(
                horizontalOffset = borderStart,
                startY = bounds.top,
                endY = bounds.top + cornerRadius,
                paint = backgroundPaint
            )

            drawVerticalLine(
                horizontalOffset = borderEnd,
                startY = bounds.top,
                endY = bounds.top + cornerRadius,
                paint = backgroundPaint
            )
        }

        if (eventChip.endsOnLaterDay) {
            drawVerticalLine(
                horizontalOffset = borderStart,
                startY = bounds.bottom - cornerRadius,
                endY = bounds.bottom,
                paint = backgroundPaint
            )

            drawVerticalLine(
                horizontalOffset = borderEnd,
                startY = bounds.bottom - cornerRadius,
                endY = bounds.bottom,
                paint = backgroundPaint
            )
        }
    }

    private fun Canvas.drawEventTitle(
        eventChip: EventChip,
        textLayout: StaticLayout
    ) {
        val bounds = eventChip.bounds

        val horizontalOffset = if (viewState.isLtr) {
            bounds.left + viewState.eventPaddingHorizontal
        } else {
            bounds.right - viewState.eventPaddingHorizontal
        }

        val verticalOffset = if (eventChip.event.isAllDay) {
            (bounds.height() - textLayout.height) / 2f
        } else {
            viewState.eventPaddingVertical.toFloat()
        }

        withTranslation(x = horizontalOffset, y = bounds.top + verticalOffset) {
            draw(textLayout)
        }
    }

    private fun updateBackgroundPaint(
        entity: ResolvedWeekViewEntity,
        isBeingDragged: Boolean,
        paint: Paint
    ) = with(paint) {
        color = entity.style.backgroundColor ?: viewState.defaultEventColor
        isAntiAlias = true
        strokeWidth = 0f
        style = Paint.Style.FILL

        if (isBeingDragged) {
            setShadowLayer(12f, 0f, 0f, dragShadow)
        } else {
            clearShadowLayer()
        }
    }

    private fun updateBorderPaint(
        entity: ResolvedWeekViewEntity,
        paint: Paint
    ) = with(paint) {
        color = entity.style.borderColor ?: viewState.defaultEventColor
        isAntiAlias = true
        strokeWidth = entity.style.borderWidth?.toFloat() ?: 0f
        style = Paint.Style.STROKE
    }
}
