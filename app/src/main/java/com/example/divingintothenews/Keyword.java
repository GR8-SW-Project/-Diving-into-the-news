package com.example.divingintothenews;

public class Keyword {
    private String date, category, keyword;
    private float importance;

    public float getImportance()
    {
        return importance;
    }

    public String getKeyword()
    {
        return keyword;
    }

    @Override
    public String toString()
    {
        return "Keyword{" +
                "date='" + date + '\'' +
                ", category='" + category + '\'' +
                ", keyword='" + keyword + '\'' +
                ", importance=" + importance +
                '}';
    }

    public Keyword(String date, String category, String keyword, float importance){
        this.date = date;
        this.category = category;
        this.keyword = keyword;
        this.importance = importance;
    }
}
