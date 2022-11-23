package com.example.divingintothenews;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class ArticleActivity extends AppCompatActivity {

    private static Article article;
    private static String url;
    private WebView webView;

    public void setArticle(){
        article = NewsListActivity.getSelectedArticle();
    }
    public void setURL(){
        url = article.getLink();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        setArticle();
        setURL();
        // Web View 초기설정
        webView = (WebView) findViewById(R.id.webView);
        webView.setWebViewClient(new WebViewClient()); // WebView 설정
        webView.getSettings().setJavaScriptEnabled(true); // JavaScript 유효화
        webView.loadUrl(url); // URL 읽어들임

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(article.getTitle());
            actionBar.setSubtitle(article.getCompany() + "/" + article.getDate());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();// 백그라운에서 퍼그라운에 돌아왔을 때
        if(webView != null){ // WebView가 비어 있지 않으면
            String url2 = webView.getUrl(); // 현재 웹페이지
            webView.loadUrl(url2); // 다시 표시
        }
    }
    
    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) // WebView 내에서 뒤로 갈 내용이 있을 경우
            webView.goBack();
        else
            super.onBackPressed(); // 전 화면으로 복귀

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}