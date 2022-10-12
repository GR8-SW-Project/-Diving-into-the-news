package com.example.divingintothenews;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class ArticleActivity extends AppCompatActivity {

    private static String[] article = new String[4];
    private static String URL;
    private WebView webView;

    public void setArticle(){
        article = NewsListActivity.getSelectedArticle();
    }
    public void setURL(){
        URL = article[3];
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
        webView.loadUrl(URL); // URL 읽어들임

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(article[0]);
            actionBar.setSubtitle(article[1] + "/" + article[2]);
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent e){
        if(keyCode == KeyEvent.KEYCODE_BACK){ // 뒤로 가기가 눌렸을 때
            if(webView != null && webView.canGoBack()){ // WebView가NULL이 아니며, 이력이 있으면
                webView.goBack(); // 전에 웹페이지 표시
            }
            return true;
        }else{
            return super.onKeyDown(keyCode, e);
        }
    }
    @Override
    protected void onResume() {
        super.onResume();// 백그라운에서 퍼그라운에 돌아왔을 때
        if(webView != null){ // WebView가 비어 있지 않으면
            String url = webView.getUrl(); // 현재 웹페이지
            webView.loadUrl(url); // 다시 표시
        }
    }
}