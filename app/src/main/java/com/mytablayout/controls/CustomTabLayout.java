package com.mytablayout.controls;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.mytablayout.R;

public class CustomTabLayout extends TabLayout {

    private int lastSelectedPosition = 0;
    private int defaultWidth = -1;
    private boolean isAnimating = false;

    public CustomTabLayout(Context context) {
        super(context);
    }

    public CustomTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void enableScrollableTabs() {
        setTabMode(TabLayout.MODE_SCROLLABLE);
        setTabGravity(TabLayout.GRAVITY_FILL);
    }

    @Override
    public void setupWithViewPager(@Nullable ViewPager viewPager) {
        super.setupWithViewPager(viewPager);

        // Loop through all tabs to set the custom view and handle animation
        for (int i = 0; i < getTabCount(); i++) {
            Tab tab = getTabAt(i);
            if (tab != null) {
                tab.setCustomView(R.layout.tab_layout_item);

                View customView = tab.getCustomView();
                if (customView != null) {
                    ImageView tabIcon = customView.findViewById(R.id.tabIcon);
                    TextView tabText = customView.findViewById(R.id.tabText);

                    LinearLayout.LayoutParams layoutParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);

                    if (i == 0) {
                        // Select the first tab by default
                        tabIcon.setImageResource(R.drawable.game_selected);
                        tabText.setTextColor(ContextCompat.getColor(getContext(), R.color.tab_selected_text_color));
                        customView.setBackgroundResource(R.drawable.selected_tab_back);
                        layoutParam.setMargins(0, 0, 0, 0);
                        customView.setLayoutParams(layoutParam);
                    } else {
                        tabIcon.setImageResource(R.drawable.game_unselected);
                        tabText.setTextColor(ContextCompat.getColor(getContext(), R.color.tab_unselected_text_color));
                        tabText.setVisibility(View.GONE);
                        // layoutParam.setMargins(-60, 0, -60, 0);
                        customView.setLayoutParams(layoutParam);
                    }
                }
            }
        }

        // Set a listener to handle tab selection and unselection animation
        addOnTabSelectedListener(new OnTabSelectedListener() {
            @Override
            public void onTabSelected(@NonNull Tab tab) {
                animateTabSelection(tab, true);
            }

            @Override
            public void onTabUnselected(@NonNull Tab tab) {
                if (!isAnimating) {
                    Log.e("onTabUnselected", "onTabUnselected");
                    animateTabSelection(tab, false);
                }
            }

            @Override
            public void onTabReselected(@NonNull Tab tab) {
            }

        });
    }

    private void animateTabSelection(Tab tab, boolean selected) {
        View customView = tab.getCustomView();

        if (customView != null) {
            ImageView tabIcon = customView.findViewById(R.id.tabIcon);
            TextView tabText = customView.findViewById(R.id.tabText);

            int iconResId = selected ? R.drawable.game_selected : R.drawable.game_unselected;
            int textColor = selected ? R.color.tab_selected_text_color : R.color.tab_unselected_text_color;
            int tabBackground = selected ? R.drawable.selected_tab_back : 0;

            LinearLayout.LayoutParams layoutParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);

            if (!selected) {
                // Hide the icon for unselected tabs
                tabIcon.setVisibility(View.VISIBLE);
                if (tabText.getMeasuredWidth() > 0) {
                    collapseTextView(customView, tabText, tabIcon, tabBackground, textColor, iconResId);
                }
            } else {
                //layoutParam.setMargins(0, 0, 0, 0);
                // customView.setLayoutParams(layoutParam);
                tabText.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
                tabIcon.setVisibility(View.VISIBLE);
                tabText.setVisibility(View.VISIBLE);
                expandTextView(customView, tabText, tabIcon, tabBackground, textColor, iconResId);
            }

            lastSelectedPosition = tab.getPosition();
        }
    }

    private void collapseTextView(View customView, TextView tabText, ImageView tabIcon, int tabBackground, int textColor, int iconResId) {
        if (defaultWidth == -1) {
            defaultWidth = tabText.getMeasuredWidth();
        }

        int finalWidth = 130;

        isAnimating = true;
        ValueAnimator animator = ValueAnimator.ofInt(defaultWidth, 0);
        animator.setDuration(300);
        animator.addUpdateListener(animation -> {
            int value = (int) animation.getAnimatedValue();
            ViewGroup.LayoutParams layoutParams = tabText.getLayoutParams();
            layoutParams.width = value;
            tabText.setLayoutParams(layoutParams);
        });

        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                isAnimating = false;
                tabIcon.setImageResource(iconResId);
                tabText.setTextColor(ContextCompat.getColor(getContext(), textColor));
                customView.setBackgroundResource(tabBackground);
                tabText.setVisibility(View.GONE);
                new Handler().postDelayed(() -> {
                    ViewGroup.LayoutParams layoutParams = tabText.getLayoutParams();
                    layoutParams.width = defaultWidth;
                    tabText.setLayoutParams(layoutParams);
                }, 100);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                isAnimating = false;
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }

        });

        animator.start();
    }

    private void expandTextView(View customView, TextView tabText, ImageView tabIcon, int tabBackground, int textColor, int iconResId) {
        tabText.measure(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        int targetWidth = tabText.getMeasuredWidth();

        isAnimating = true;
        ValueAnimator animator = ValueAnimator.ofInt(0, targetWidth);
        animator.setDuration(500);

        animator.addUpdateListener(animation -> {
            int value = (int) animation.getAnimatedValue();
            ViewGroup.LayoutParams layoutParams = tabText.getLayoutParams();
            layoutParams.width = value;
            tabText.setLayoutParams(layoutParams);
            tabText.requestLayout();
        });

        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                tabIcon.setImageResource(iconResId);
                tabText.setTextColor(ContextCompat.getColor(getContext(), textColor));
                customView.setBackgroundResource(tabBackground);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                isAnimating = false;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                isAnimating = false;
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });

        animator.start();
    }
}
