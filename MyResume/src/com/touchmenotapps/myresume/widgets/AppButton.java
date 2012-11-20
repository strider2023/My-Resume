package com.touchmenotapps.myresume.widgets;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

import com.touchmenotapps.myresume.variables.AppGlobalVariables;

public class AppButton extends Button {

	public AppButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		setTypeface(Typeface.createFromAsset(context.getAssets(),
				AppGlobalVariables.FONT_NAME));
		setHorizontalFadingEdgeEnabled(false);
	}

	public AppButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		setTypeface(Typeface.createFromAsset(context.getAssets(),
				AppGlobalVariables.FONT_NAME));
		setHorizontalFadingEdgeEnabled(false);
	}

	public AppButton(Context context) {
		super(context);
		setTypeface(Typeface.createFromAsset(context.getAssets(),
				AppGlobalVariables.FONT_NAME));
		setHorizontalFadingEdgeEnabled(false);
	}
}
