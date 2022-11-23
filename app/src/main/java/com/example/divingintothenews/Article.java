package com.example.divingintothenews;

public class Article {
    private String title, company, date, link;

    public String getTitle()
    {
        return title;
    }

    public String getCompany()
    {
        return company;
    }

    public String getDate()
    {
        return date;
    }

    public String getLink()
    {
        return link;
    }

    public Article(String title, String company, String date, String link)
    {
        this.title = title;
        this.company = company;
        this.date = date;
        this.link = link;
    }
}
