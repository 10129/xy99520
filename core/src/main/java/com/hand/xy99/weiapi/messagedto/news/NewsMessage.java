package com.hand.xy99.weiapi.messagedto.news;

import com.hand.xy99.weiapi.messagedto.BaseMessage;

import java.util.List;

/**
 * Created by xieshuai on 2018/1/19.
 */
public class NewsMessage extends BaseMessage{
    private int ArticleCount;
    private List<Articles> Articles;

    public int getArticleCount() {
        return ArticleCount;
    }

    public void setArticleCount(int articleCount) {
        ArticleCount = articleCount;
    }

    public List<com.hand.xy99.weiapi.messagedto.news.Articles> getArticles() {
        return Articles;
    }

    public void setArticles(List<com.hand.xy99.weiapi.messagedto.news.Articles> articles) {
        Articles = articles;
    }
}
