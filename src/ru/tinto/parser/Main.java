package ru.tinto.parser;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import org.htmlcleaner.TagNode;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Main extends FragmentActivity implements ArticleListFragment.onArticleListItemClickListener {

    private ProgressDialog pd;
    private List<String> links;
    private ParseSingleArticle parseSingleArticle;

    private SingleArticleFragment singleArticleFragment;
    private ArticleListFragment articleListFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        pd = ProgressDialog.show(this, "Подождите...", "Строим список заголовков", true, false);
        new ParseSite().execute("http://www.lokomotiv.info/all/");
    }

    @Override
    public void itemClick(int i) {
//        Toast.makeText(this, links.get(i), Toast.LENGTH_SHORT).show();
        pd = ProgressDialog.show(this, "Подождите...", "Cкачиваем статью с сервера", true, false);
        parseSingleArticle = new ParseSingleArticle();
        parseSingleArticle.execute(links.get(i));
    }

    private class ParseSite extends AsyncTask<String, Void, List<String>> {

        @Override
        protected List<String> doInBackground(String... arg) {
            List<String> output = new ArrayList<String>();
            links = new ArrayList<String>();
            try {
                HtmlHelper hh = new HtmlHelper(new URL(arg[0]));
                List<TagNode> titles = hh.getLokoTitles();

                for (TagNode divElement : titles) {
                    output.add(divElement.getText().toString());
                    links.add(divElement.getAttributeByName("href"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return output;
        }

        protected void onPostExecute(List<String> output) {
            pd.dismiss();
            articleListFragment = new ArticleListFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragContainer, articleListFragment)
                    .commit();
            articleListFragment.setListAdapter(new ArrayAdapter<String>(Main.this, R.layout.item, output));
        }
    }

    private class ParseSingleArticle extends AsyncTask<String, String, String>{

        @Override
        protected String doInBackground(String... params) {
            String text = null;
            try {
                Log.d("myLogs", "Article URL: " + params[0]);
                HtmlHelper hh = new HtmlHelper(new URL(params[0]));
                text = hh.getSingleArticle();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return text;
        }

        @Override
        protected void onPostExecute(String s) {
            pd.dismiss();
            if (s!=null){
                singleArticleFragment = SingleArticleFragment.newInstance(s);
            }
            else{
                singleArticleFragment = SingleArticleFragment.newInstance("Return string is 'null'");
            }
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragContainer, singleArticleFragment)
                    .addToBackStack(null)
                    .commit();
//            Toast.makeText(Main.this, "done", Toast.LENGTH_SHORT).show();
        }
    }
}
