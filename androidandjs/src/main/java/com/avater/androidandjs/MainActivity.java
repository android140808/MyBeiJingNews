package com.avater.androidandjs;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity implements View.OnClickListener{


    private EditText et_number;
    private EditText et_password;
    private Button btn_login;

    private WebView webView;
    private WebSettings webSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_login = (Button)findViewById(R.id.btn_login);
        et_password = (EditText)findViewById(R.id.et_password);
        et_number = (EditText)findViewById(R.id.et_number);
        btn_login.setOnClickListener(this);
        initWebView();
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btn_login) {
            String user = et_number.getText().toString().trim();
            String pass = et_password.getText().toString().trim();
            if(TextUtils.isEmpty(user)||TextUtils.isEmpty(pass)) {
                Toast.makeText(MainActivity.this, "账户或者密码不能为空", Toast.LENGTH_SHORT).show();
            }else {
                //进行java调用javascript
                webView.loadUrl("javascript:javaCallJS('"+user+"')");
                setContentView(webView);
            }
        }
    }

    private void initWebView(){
        webView = new WebView(this);
        webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);//设置支持javascript脚本语言
        webSettings.setUseWideViewPort(true);//设置支持双击
        webSettings.setBuiltInZoomControls(true);//支持缩放按钮
        webView.setWebViewClient(new WebViewClient());//默认在APP在访问网页(不跳转到浏览器内)

        //TODO 设置支持JS调用JAVA
        webView.addJavascriptInterface(new JavaScriptCallAndroidInterface(),"Android");
        webView.loadUrl("file:///android_asset/JavaAndJavaScriptCall.html");//
    }

    class JavaScriptCallAndroidInterface{
        @JavascriptInterface
        public void showToast(){
            Toast.makeText(MainActivity.this, "javascript调用了android的方法", Toast.LENGTH_SHORT).show();
        }
    }
}
