package com.alamkanak.weekview.sample.models;

import android.text.TextPaint;
import android.text.style.MetricAffectingSpan;

import androidx.annotation.NonNull;

public class CenterVerticalSpan extends MetricAffectingSpan {

    @Override
    public void updateMeasureState(@NonNull TextPaint textPaint) {
        textPaint.baselineShift += getBaselineShift(textPaint);
    }

    @Override
    public void updateDrawState(TextPaint textPaint) {
        textPaint.baselineShift += getBaselineShift(textPaint);
    }

    private int getBaselineShift(TextPaint tp) {
        float total = tp.ascent() + tp.descent();
        return (int) (total / 2f);
    }
}