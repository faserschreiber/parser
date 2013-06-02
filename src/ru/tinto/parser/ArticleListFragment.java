package ru.tinto.parser;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.AdapterView;

public class ArticleListFragment extends ListFragment {

    public interface onArticleListItemClickListener {
        public void itemClick(int i);
    }

    onArticleListItemClickListener listener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            listener = (onArticleListItemClickListener) activity;
        } catch (ClassCastException e){
            throw new ClassCastException(activity.toString() + "must implement onArticleListItemClickListener");
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listener.itemClick(position);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
