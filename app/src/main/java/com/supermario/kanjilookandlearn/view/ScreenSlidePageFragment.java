/*
 * Copyright 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.supermario.kanjilookandlearn.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.supermario.kanjilookandlearn.R;
import com.supermario.kanjilookandlearn.common.Utils;
import com.supermario.kanjilookandlearn.data.Kanji;
import com.supermario.kanjilookandlearn.database.KanjiProvider;

import java.io.IOException;

/**
 * A fragment representing a single step in a wizard. The fragment shows a dummy title indicating
 * the page number, along with some dummy text.
 */
public class ScreenSlidePageFragment extends Fragment {


    private ViewGroup rootView;
    private ImageView imageView;
    private View rootLayout;
    private View cardFace;
    private View cardBack;
    private TextView kanjiFaceTextView;
    private TextView kunyomiTextView;
    private TextView onyomiTextView;
    private TextView rememberTextView;
    private ImageView favoriteImageView;
    private Kanji kanjiItem;
    private WebView strokeWebView;


    /**
     * Factory method for this fragment class. Constructs a new fragment for the given page number.
     */
    public static ScreenSlidePageFragment create(Kanji kanjiItem) {
        ScreenSlidePageFragment fragment = new ScreenSlidePageFragment();
        fragment.kanjiItem = kanjiItem;

        return fragment;
    }

    public ScreenSlidePageFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout containing a title and body text.
        rootView = (ViewGroup) inflater
                .inflate(R.layout.fragment_screen_slide_page, container, false);


        initView();


        return rootView;
    }

    private void initView() {
        initId();

        rootLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCardClick(v);
            }
        });
        imageView.setImageDrawable(Utils.getDrawableFromAssets(getActivity(), kanjiItem.image));
        kanjiFaceTextView.setText(kanjiItem.kanji);

        // a SpannableStringBuilder containing text to display
        SpannableStringBuilder sb = new SpannableStringBuilder(kanjiItem.remember);

        // create a bold StyleSpan to be used on the SpannableStringBuilder
        StyleSpan b = new StyleSpan(android.graphics.Typeface.BOLD); // Span to make text bold

        int index = kanjiItem.remember.indexOf(kanjiItem.meanVietnamese);
        if (index >= 0) {
            // set only the name part of the SpannableStringBuilder to be bold --> 16, 16 + name.length()
            sb.setSpan(b, index, index + kanjiItem.meanVietnamese.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        }

        kunyomiTextView.setText(kanjiItem.kunyomi);
        onyomiTextView.setText(kanjiItem.onyomi);


        rememberTextView.setText(sb);

//        try {
//            strokeWebView.loadDataWithBaseURL(null,Utils.getStringFromInputStream(Utils.getInputStreamFromAssets(getActivity(), "04e00")), "text/html", "UTF-8", null);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


        if (kanjiItem.favorite == 1) {
            favoriteImageView.setImageResource(R.drawable.icon_favorite_active);
        } else {
            favoriteImageView.setImageResource(R.drawable.icon_favorite_inactive);
        }

        favoriteImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (kanjiItem.favorite == 0) {
                    kanjiItem.favorite = 1;
                    favoriteImageView.setImageResource(R.drawable.icon_favorite_active);
                } else {
                    kanjiItem.favorite = 0;
                    favoriteImageView.setImageResource(R.drawable.icon_favorite_inactive);
                }
                KanjiProvider.updateFavorite(getActivity(), kanjiItem);
            }
        });
    }

    private void initId() {
        imageView = (ImageView) rootView.findViewById(R.id.pictureImageView);
        kanjiFaceTextView = (TextView) rootView.findViewById(R.id.kanjiFaceTextView);
        rootLayout = (View) rootView.findViewById(R.id.card_layout);
        cardFace = (View) rootView.findViewById(R.id.face_card_view);
        cardBack = (View) rootView.findViewById(R.id.back_card_view);
        kunyomiTextView = (TextView) rootView.findViewById(R.id.kunyomiTextView);
        onyomiTextView = (TextView) rootView.findViewById(R.id.onyomiTextView);
        rememberTextView = (TextView) rootView.findViewById(R.id.rememberTextView);
        favoriteImageView = (ImageView) rootView.findViewById(R.id.favoriteImageView);
        strokeWebView = (WebView) rootView.findViewById(R.id.strokeImageView);
    }

    public void onCardClick(View view) {
        flipCard();
    }

    private void flipCard() {


        FlipAnimation flipAnimation = new FlipAnimation(cardFace, cardBack);

        if (cardFace.getVisibility() == View.GONE) {
            flipAnimation.reverse();
        }
        rootLayout.startAnimation(flipAnimation);
    }


}
