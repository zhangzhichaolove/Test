package rxjava.example.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import rxjava.example.R;

/**
 * Created by Chao on 2017/3/31.
 */

public class WebViewActivity extends AppCompatActivity {

    //assets下的文件的JsoupParHtml.html所在的绝对路径
    private static final String DEFAULT_URL = "file:///android_asset/JsoupParHtml.HTML";
    WebView webView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        webView = (WebView) findViewById(R.id.webView);
        init();
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.loadUrl("javascript:javacalljs()");
            }
        });
    }

    private void init() {
        //下面三行设置主要是为了待webView成功加载html网页之后，我们能够通过webView获取到具体的html字符串
        webView.getSettings().setJavaScriptEnabled(true);
        // 开启 DOM storage API 功能
        webView.getSettings().setDomStorageEnabled(true);
        //设置 缓存模式
        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        //设置字符编码
        //webView.getSettings().setDefaultTextEncodingName("GBK");
        //滚动条风格，为SCROLLBARS_INSIDE_OVERLAY指滚动条不占用空间，直接覆盖在网页上
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);//1:SCROLLBARS_INSIDE_OVERLAY 2:SCROLLBARS_INSIDE_INSET  3: SCROLLBARS_OUTSIDE_OVERLAY 4: SCROLLBARS_OUTSIDE_INSET
        webView.addJavascriptInterface(new InJavaScriptLocalObj(), "local_obj");
        webView.addJavascriptInterface(WebViewActivity.this,"android");//window.android.startFunction()
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
//                view.loadUrl("javascript:window.local_obj.showSource('<head>'+"
//                        + "document.getElementsByTagName('html')[0].innerHTML+'</head>');");
            }

            @Override
            public void onReceivedError(WebView view, int errorCode,
                                        String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
            }

        });
        webView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {// 载入进度改变而触发
                Log.e("TAG", progress + "");
                super.onProgressChanged(view, progress);
            }
        });

        webView.loadUrl(DEFAULT_URL);
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    final class InJavaScriptLocalObj {
        @JavascriptInterface
        public void showSource(String html) {
            refreshHtmlContent(html);
        }
    }


    private void refreshHtmlContent(final String html) {
        Log.i("网页内容", html);
        webView.postDelayed(new Runnable() {
            @Override
            public void run() {
                //解析html字符串为对象
                Document document = Jsoup.parse(html);
                //通过类名获取到一组Elements，获取一组中第一个element并设置其html
                Elements elements = document.getElementsByClass("loadDesc");
                elements.get(0).html("<p>加载完成</p>");

                //通过ID获取到element并设置其src属性
                Element element = document.getElementById("imageView");
                element.attr("src", "voice.png");

                document.getElementById("img").attr("src", "ic_launcher.png");
                //通过类名获取到一组Elements，获取一组中第一个element并设置其文本
                elements = document.select("p.hint");
                elements.get(0).text("这里的文字发生了改变！");

                //获取进行处理之后的字符串并重新加载
                String body = document.toString();
                webView.loadDataWithBaseURL(null, body, "text/html", "utf-8", null);
            }
        }, 5000);
    }

    //由于安全原因 targetSdkVersion>=17需要加 @JavascriptInterface
    //JS调用Android JAVA方法名和HTML中的按钮 onclick后的别名后面的名字对应
    @JavascriptInterface
    public void startFunction(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(WebViewActivity.this,"show",Toast.LENGTH_SHORT).show();
            }
        });
    }


}
