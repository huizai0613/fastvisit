package cn.ahyxy.fastvisit.weight;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;


/**
 * Created by zhaofei on 15-10-24.
 */
public class LsWebView extends WebView
{
    private EventListener eventListener;

    public LsWebView(Context context)
    {
        super(context);
        initWebView();
    }

    public LsWebView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        initWebView();
    }

    public LsWebView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        initWebView();
    }

    public void setEventListener(EventListener listener)
    {
        this.eventListener = listener;
    }

    @Override
    public void loadUrl(String url)
    {
//        if ("about:blank".equalsIgnoreCase(url) || url.startsWith("javascript:")) {
        super.loadUrl(url);
//            return;
//        }
    }

    private void initWebView()
    {
        WebViewClient client = new LsWebViewClient();
        WebSettings settings = getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setAllowFileAccess(true);
        settings.setSupportZoom(true);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        setWebViewClient(client);
    }


    public void handleDestory()
    {
        stopLoading();
        loadUrl("about:blank");
        eventListener = null;
        setWebChromeClient(null);
        setWebViewClient(null);
    }

    public interface EventListener
    {
        void onHadlePageStarte(WebView view, String url);

        void onHandlePageLoaded(WebView view, String url);

        void onHandleError(WebView view, int errorCode, String description, String failingUrl);

        boolean onHandleProcessContentUri(WebView view, String url);
    }

    class LsWebViewClient extends WebViewClient
    {
        @Override
        public void onPageFinished(WebView view, String url)
        {
            super.onPageFinished(view, url);
            invalidate();
            if (eventListener != null) {
                eventListener.onHandlePageLoaded(view, url);
            }
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url)
        {
            if (eventListener != null) {
                if (eventListener.onHandleProcessContentUri(view, url)) {
                    return true;
                }
            }
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon)
        {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl)
        {
            super.onReceivedError(view, errorCode, description, failingUrl);
            Log.w("WEBVIEW_ERROR", errorCode + ":" + description);
            if (eventListener != null) {
                eventListener.onHandleError(view, errorCode, description, failingUrl);
            }
        }
    }

}
