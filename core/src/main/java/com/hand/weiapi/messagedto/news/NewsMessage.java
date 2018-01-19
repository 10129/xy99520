package com.hand.weiapi.messagedto.news;

import com.hand.weiapi.messagedto.BaseMessage;

import java.util.List;

/**
 * Created by xieshuai on 2018/1/19.
 */
public class NewsMessage extends BaseMessage{
    private int ArticleCount;
    private List<item> Articles;
    public int getArticleCount() {
        return ArticleCount;
    }

    public void setArticleCount(int articleCount) {
        ArticleCount = articleCount;
    }

    public List<item> getArticles() {
        return Articles;
    }

    public void setArticles(List<item> articles) {
        Articles = articles;
    }
}
