package com.mytablayout.utils;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mytablayout.R;

import java.util.ArrayList;
import java.util.List;

public class HorizontalTabBar extends FrameLayout {
    private Context context;
    private List<String> contentList = new ArrayList<>();
    LinearLayout linearLayout;
    private OnSlideTabClickListener onSlideTabClickListener;
    int selectedIndex;
    LinearLayout highLighterView;
    int parentHeight = 65;
    int childHeight = 65;
    LinearLayout textLayout;
    int width = 384;

    public HorizontalTabBar(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    public HorizontalTabBar(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public void populateSliderData(List<String> sliderData, int selectedIndex,
                                   OnSlideTabClickListener onSlideTabClickListener) {
        this.onSlideTabClickListener = onSlideTabClickListener;
        removeAllViews();
        contentList.clear();
        contentList = sliderData;
        this.selectedIndex = selectedIndex;
        init();
        setSelectedIndex(selectedIndex);
    }

    private void init() {
        linearLayout = new LinearLayout(context);
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, parentHeight);
        linearLayout.setLayoutParams(layoutParams);

        if (!contentList.isEmpty()) {
            width = getScreenWidth(context) / contentList.size();
        }

        addView(linearLayout);

        buildBackgroundView(width);
        buildHighlighterView(width);

        buildTextContent(width);
    }

    private void buildBackgroundView(int width) {

        for (int i = 0; i < contentList.size(); i++) {
            int tempindex = i;
            View view = new View(context);
            LinearLayout.LayoutParams viewParams = new LinearLayout.LayoutParams(width, childHeight);
            view.setLayoutParams(viewParams);
            view.setBackgroundColor(Color.TRANSPARENT);
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (selectedIndex != tempindex) {
                        setSelectedIndex(tempindex);
                        onSlideTabClickListener.onSlideTabClicked(tempindex);
                    }
                    selectedIndex = tempindex;
                }
            });
            linearLayout.addView(view);
        }
    }

    private void buildHighlighterView(int width) {

        highLighterView = new LinearLayout(context);
        highLighterView.setGravity(Gravity.CENTER_VERTICAL);
        highLighterView.setOrientation(LinearLayout.VERTICAL);
        LayoutParams viewParams = new LayoutParams(width, parentHeight);
        highLighterView.setLayoutParams(viewParams);

        View view = new View(context);
        LinearLayout.LayoutParams innerViewParams = new LinearLayout.LayoutParams(width, childHeight);
        view.setBackgroundResource(R.drawable.white_round_back);
        view.setLayoutParams(innerViewParams);
        highLighterView.addView(view);

        addView(highLighterView);

    }

    private void buildTextContent(int width) {
        textLayout = new LinearLayout(context);
        textLayout.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 50));

        for (String text : contentList) {
            TextView textView = new TextView(context);
            textView.setText(text);
            textView.setTextSize(15);
            textView.setTextColor(Color.parseColor("#000000"));

            LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
            textView.setGravity(Gravity.CENTER);
            textView.setSingleLine(true);
            textParams.setMargins(15, 10, 15, 0);
            textView.setLayoutParams(textParams);

            textLayout.addView(textView);
        }

        addView(textLayout);
    }

    public interface OnSlideTabClickListener {
        void onSlideTabClicked(int index);
    }

    public void setSelectedIndex(int index) {
        selectedIndex = index;
        if (textLayout == null) {
            return;
        }
        int distanceX = width * index;

        TextView selectedTextView = (TextView) textLayout.getChildAt(index);
        if (selectedTextView != null) {
            int highlighterWidth = selectedTextView.getWidth() - 2 * selectedTextView.getPaddingStart(); // Consider padding
            int highlighterX = selectedTextView.getLeft() + selectedTextView.getPaddingStart(); // X position of the selected text view
            LayoutParams highlighterLayoutParams = (LayoutParams) highLighterView.getLayoutParams();
            highlighterLayoutParams.width = highlighterWidth;
            highLighterView.setLayoutParams(highlighterLayoutParams);

            // Animate the highlighter view's translation and X position
            ObjectAnimator animator = ObjectAnimator.ofFloat(highLighterView, "translationX", distanceX - highlighterX);
            animator.setInterpolator(new AccelerateDecelerateInterpolator());
            animator.setDuration(400);
            animator.start();
        }

        for (int i = 0; i < textLayout.getChildCount(); i++) {
            TextView textView = (TextView) textLayout.getChildAt(i);
            textView.setTextColor(Color.parseColor("#000000"));
        }

    }

    public static int getScreenWidth(Context context) {
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }
}
