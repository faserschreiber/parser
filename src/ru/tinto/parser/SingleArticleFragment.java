package ru.tinto.parser;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class SingleArticleFragment extends Fragment {

    public static SingleArticleFragment newInstance(String text){
        SingleArticleFragment fragment = new SingleArticleFragment();
        Bundle args = new Bundle();
        args.putString("text", text);
        fragment.setArguments(args);
        return fragment;
    }

    String getText(){
        return getArguments().getString("text");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.single_article, null);
        TextView tvText = (TextView) v.findViewById(R.id.tvText);
        tvText.setText(getText());

        return v;
    }

}
