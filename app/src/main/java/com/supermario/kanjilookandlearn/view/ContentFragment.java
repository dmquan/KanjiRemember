package com.supermario.kanjilookandlearn.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.supermario.kanjilookandlearn.R;
import com.supermario.kanjilookandlearn.activity.FlashCardActivity;
import com.supermario.kanjilookandlearn.activity.MainActivity;
import com.supermario.kanjilookandlearn.adapter.ReviewListAdapter;
import com.supermario.kanjilookandlearn.data.Kanji;
import com.supermario.kanjilookandlearn.database.KanjiProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 04-06-2015.
 */
public class ContentFragment extends Fragment {
    private View rootView;
    public RecyclerView mRecyclerView;
    public ArrayList<Kanji> list = new ArrayList<Kanji>();
    private ReviewListAdapter mAdapter;
    private FloatingActionButton floatButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.content_fragment,container,false);
        initView();
        return rootView;
    }

    private void initView() {
        initId();
        floatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FlashCardActivity.class);
                startActivity(intent);
            }
        });
        list = KanjiProvider.getAll(getActivity());

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new ReviewListAdapter(getActivity(), list, mRecyclerView);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initId() {
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview);
        floatButton = (FloatingActionButton) rootView.findViewById(R.id.floatButton);
    }

    public void updateKanjiList(ArrayList<Kanji> list){
        this.list = list;
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new ReviewListAdapter(getActivity(), list, mRecyclerView);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }
}
