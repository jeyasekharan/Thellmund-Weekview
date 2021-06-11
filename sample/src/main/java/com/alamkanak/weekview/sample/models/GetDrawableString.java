package com.alamkanak.weekview.sample.models;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;

import androidx.core.content.ContextCompat;

import static java.security.AccessController.getContext;

public class GetDrawableString {

    public static SpannableStringBuilder setEventsIconsBasedOnType(Context context, SpannableStringBuilder bob, int leftDrawable, int rightDrawable, String leftText, String rightText) {
        int len = bob.length();

        /* Drawable left*/
        Drawable d1 = ContextCompat.getDrawable(context, leftDrawable);
        d1.setBounds(50, 50, 50, 50);

        String newStr = d1.toString();
        //bob.append(newStr);
        bob.setSpan(
                new ImageSpan(d1),
                len-1,
                bob.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        len = bob.length();

        // Set left Text
        bob.append("  " + leftText + "       ");
        bob.setSpan(new CenterVerticalSpan(), len, bob.length(), 0);
        len = bob.length();


        // Drawable Right
        Drawable d2 = ContextCompat.getDrawable(context, rightDrawable);
        d2.setBounds(50, 50, 50, 50);

        String newStr2 = d2.toString();
        //bob.append(newStr2);
        bob.setSpan(
                new ImageSpan(d2),
                len-1,
                bob.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);


        //Set right text
        len = bob.length();
        bob.append("  " + rightText);

        //bob.setSpan(new AbsoluteSizeSpan(fontSize), 0, 3, 0);
        bob.setSpan(new CenterVerticalSpan(), len, bob.length(), 0);

        return bob;
    }

}
