package com.example.applicationcv;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.http.SslError;
import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.Timer;

public class WebviewActivity extends AppCompatActivity {

    static final String TAG = "[WebviewActivity]";

    private WebView webView = null;
    private TextView web_title_name = null;
    private TextView webview_empty = null;
    private Boolean isChecked = true;
    private Boolean start = true;
    //    private Response orderInfoResponse;
    private String orderUUID = null;
    private Timer timer = new Timer();
    private Boolean isResponse = true;

    protected Activity getActivity() {
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        log = new TseLogger("WebviewActivity");
        try {
            setContentView(R.layout.activity_webview);

            if (isNetworkConnected(this)) {
                Button button_back = findViewById(R.id.tos_button_back);
                Button button_test = findViewById(R.id.button_test);
                button_test.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (isChecked) {
                            webView.setVisibility(View.INVISIBLE);
                            webview_empty.setVisibility(View.VISIBLE);
                            isChecked = false;
                        } else {
                            webView.setVisibility(View.VISIBLE);
                            webview_empty.setVisibility(View.INVISIBLE);
                            isChecked = true;
                        }
                    }
                });
                button_back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
                web_title_name = findViewById(R.id.webview_title_name);
                webView = findViewById(R.id.webview_view);
                webview_empty = findViewById(R.id.webview_view_empty);
                webView.setWebViewClient(new WebViewClient() {

                    @Override
                    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                        handler.proceed(); // Ignore SSL certificate errors
                    }

                    // When page loading
                    @Override
                    public void onPageStarted(WebView view, String url, Bitmap favicon) {
//                        log.info("onPageStarted");
//                        webView.loadUrl("javascript:(function() {document.getElementsByTagName('pre')[0].style.display='none';})()");
                        super.onPageStarted(view, url, favicon);
                    }

                    // When page load finish.
                    @Override
                    public void onPageFinished(WebView view, String url) {
//                        log.info("onPageFinished");
                        webView.loadUrl("javascript:window.HTMLOUT.processHTML('<head>'+document.getElementsByTagName('html')[0].innerHTML+'</head>');");
//                        super.onPageFinished(view, url);
                    }

                    @Override
                    public void onLoadResource(WebView view, String url) {
//                        log.info("onLoadResource");
                        super.onLoadResource(view, url);
                    }
                });
            } else {
                finish();
            }

        } catch (Exception ex) {
//            log.error(ex.getMessage(), ex);
//            showToast(R.string.error_browser);
            finish();
        }
        Intent intent = getIntent();
        String url = intent.getStringExtra("web");
//        orderUUID = intent.getStringExtra("orderUUID");
        web_title_name.setText(intent.getStringExtra("title"));

//        goUrl("https://tokhaiyte.vn/?page=login");
        goUrl(url);
    }

    private void goUrl(String url) {
        webView.addJavascriptInterface(new JavaScriptInterface(), "HTMLOUT");
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.loadUrl(url);
    }

    class JavaScriptInterface {
        @JavascriptInterface
        @SuppressWarnings("unused")
        public void processHTML(String html) {
//            log.info("processHTML");

//            {"RspCode":"00","Message":"Confirm Success"}
//            log.error(html);
//            if (html.contains("RspCode") && html.contains("Message")) {
//                try {
//                    char[] ch = new char[((html.indexOf("}") + 1) - html.indexOf("{"))];
//                    html.getChars(html.indexOf("{"), html.indexOf("}") + 1, ch, 0);
//                    String subJson = String.valueOf(ch).trim();
//                    log.info(subJson);
//
//                    OrtherRes convertedObject = (OrtherRes) (new Gson().fromJson(subJson, OrtherRes.class));
//
//                    switch (convertedObject.RspCode) {
//                        case 00:
//                            showToast(R.string.error_order_payment_success);
//                            break;
//                        case 99:
//                            showToast(getActivity().getResources().getString(R.string.error_order_payment_failed));
//                            getOrdersInfo();
//                            break;
//                        default:
//                            showToast(convertedObject.Message);
//                            break;
//                    }
//
//                    finish();
//                } catch (Exception ex) {
////                    log.error(ex);
//                }
//            }
        }
    }

    private static boolean isNetworkConnected(Activity activity) {
        ConnectivityManager cm = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }


    class OrtherRes {
        private int RspCode;
        private String Message;

    }
//
//    private void getOrdersInfo() {
//        Intent intent = new Intent(this, OrdersProfilesActivity.class);
//        intent.putExtra("caller", TAG);
//        intent.putExtra("OrderUUID", orderUUID);
//        getActivity().startActivity(intent);
//    }

    protected void showToast(String msg) {
        CustomToast.create(getActivity(), msg);
//        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    protected void showToast(@StringRes int id) {
        CustomToast.create(getActivity(), id);
//        Toast.makeText(this, id, Toast.LENGTH_LONG).show();
    }
//    private void getOrdersInfo(String orderUUID) {
//        HttpWaitingDialog.getRequest(getActivity())
//                .setAsyncRequest(new HttpWaitingDialog.AsyncRequest() {
//                    @Override
//                    public Response process() {
//                        try {
//                            log.info("<getOrdersInfo><AsyncRequest>");
//                            Response response = AWSRequest.tse_orders_info(
//                                    getActivity(),
//                                    orderUUID,
//                                    true,
//                                    getAccessToken());
//                            orderInfoResponse = response;
//                            return response;
//                        } catch (final Exception ex) {
//                            log.error(ex.getMessage(), ex);
//                            getActivity().runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    showToast(ex.getMessage());
//                                }
//                            });
//                            return null;
//                        }
//                    }
//                })
//                .setAsyncResponse(new HttpWaitingDialog.AsyncResponse() {
//                    @Override
//                    public void process(boolean successful, Response response) {
//                        if (successful) {
//                            log.info("<getOrdersInfo><AsyncGetResponse>");
//                            orderInfoResponse = response;
////                            checkPaid();
//                        } else {
//                            orderInfoResponse = null;
//                        }
//                    }
//                })
//                .executeAsync();
//    }

}