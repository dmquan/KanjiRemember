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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.supermario.kanjilookandlearn.R;
import com.supermario.kanjilookandlearn.common.Utils;
import com.supermario.kanjilookandlearn.data.Kanji;

/**
 * A fragment representing a single step in a wizard. The fragment shows a dummy title indicating
 * the page number, along with some dummy text.
 */
public class ScreenSlidePageFragment extends Fragment {
    /**
     * The argument key for the page number this fragment represents.
     */
    public static final String IMAGE = "image";
    private static final String KANJI = "kanji";
    private static final String ONYOMI = "onyomi";
    private static final String KUNYOMI = "kunyomi";
    private static final String NAME = "name";
    private static final String REMEMBER = "remember";
    private static final String FAVORITE = "remember";


    private String image;
    private String word;
    private ImageView imageView;
    private String mean;
    private String kanji;

    /**
     * Factory method for this fragment class. Constructs a new fragment for the given page number.
     */
    public static ScreenSlidePageFragment create(Kanji kanjiItem) {
        ScreenSlidePageFragment fragment = new ScreenSlidePageFragment();
        Bundle args = new Bundle();
        args.putString(IMAGE, kanjiItem.image);
        args.putString(KANJI, kanjiItem.kanji);
        args.putString(ONYOMI, kanjiItem.onyomi);
        args.putString(KUNYOMI, kanjiItem.kunyomi);
        args.putString(NAME, kanjiItem.name);
        args.putString(REMEMBER, kanjiItem.remember);
        args.putInt(FAVORITE, kanjiItem.favorite);
        fragment.setArguments(args);
        return fragment;
    }

    public ScreenSlidePageFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        image = getArguments().getString(IMAGE);
        kanji = getArguments().getString(KANJI);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout containing a title and body text.
        ViewGroup rootView = (ViewGroup) inflater
                .inflate(R.layout.fragment_screen_slide_page, container, false);
        imageView = (ImageView) rootView.findViewById(R.id.image_view);


        imageView.setImageDrawable(Utils.getDrawableFromAssets(getActivity(), image));
        return rootView;
    }

}
