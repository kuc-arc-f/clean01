package jp.co.esourcejapan.app.clean;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import jp.co.esourcejapan.fw.*;

public class TAA001Activity extends Activity {
	private String TAG ="TAA001Activity";
	//
	private WebView       m_Web;
	private String        m_STR_url       ="http://esj-clean.main.jp/dev";
	//
	private jp.co.esourcejapan.fw.ComUtil m_Util = new ComUtil();
    /** Called when the activity is first created. */
	private class WebViewClientLoading extends WebViewClient {
	    @Override
	    public boolean shouldOverrideUrlLoading(WebView view, String url) {
	        view.loadUrl(url);
	        return true;
	    }
	}
	//
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.taa001 );
        try
        {
            init_proc();
        }catch(Exception e){
        	e.printStackTrace();
        	m_Util.errorDialog(this, e.toString());
        }
    }
    //
    private void init_proc() throws Exception{
    	try
    	{
        	m_Web = (WebView) findViewById(R.id.web01 );
        	m_Web.clearCache(true);
        	m_Web.getSettings().setJavaScriptEnabled(true);
        	m_Web.getSettings().setBuiltInZoomControls(true);
        	m_Web.setWebViewClient(new WebViewClientLoading());
    		m_Web.loadUrl(m_STR_url);
    	}catch(Exception e){
    		throw e;
    	}
    }
    
    
}