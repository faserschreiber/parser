package ru.tinto.parser;

import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class HtmlHelper {

    TagNode rootNode;

    public HtmlHelper(URL htmlPage) throws IOException {
        HtmlCleaner cleaner = new HtmlCleaner();
        CleanerProperties prop = cleaner.getProperties();
        prop.setCharset("Windows-1251");
        rootNode = cleaner.clean(htmlPage);
    }

    List<TagNode> getLinksByClass(String CSSClassname)
    {
        List<TagNode> linkList = new ArrayList<TagNode>();

        TagNode linkElements[] = rootNode.getElementsByName("a", true);

        for (int i = 0; linkElements != null && i < linkElements.length; i++){
            String classType = linkElements[i].getAttributeByName("class");
            if (classType != null && classType.equals(CSSClassname)){
                linkList.add(linkElements[i]);
            }
        }
        return linkList;
    }

    List<TagNode> getLokoTitles()
    {
        List<TagNode> linkList = new ArrayList<TagNode>();

        Object[] divElements = new TagNode[0];
        try {
            divElements = rootNode
                    .evaluateXPath("//div/div[@class='title']/a");
        } catch (XPatherException e) {
            e.printStackTrace();
        }
        for (Object element: divElements){
            TagNode tag = (TagNode) element;
            linkList.add(tag);
        }

        return linkList;
    }

    String getSingleArticle() {
        Object[] divElements;
        String text = "";
        try {
            divElements = rootNode
                    .evaluateXPath("//div[@class='text']");
            for (Object element : divElements){
                TagNode tag = (TagNode) element;
                String temp = tag.getText().toString().trim();
                if (temp == null){
                    temp = "";
                }
                text += temp;
            }
        } catch (XPatherException e) {
            e.printStackTrace();
        }
        return text;
    }
}
