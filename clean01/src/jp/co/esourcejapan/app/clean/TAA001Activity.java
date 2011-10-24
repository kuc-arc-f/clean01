package jp.co.esourcejapan.app.clean;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
import android.webkit.JsResult;

import jp.co.esourcejapan.fw.*;

public class TAA001Activity extends Activity {
	private String TAG ="TAA001Activity";
	private static final int MENU1_ID = Menu.FIRST;
	private static final int MENU2_ID = Menu.FIRST+1;
	//
	Handler mHandler;
	//
	private WebView       m_Web;
	private String        m_STR_url       ="http://esj-clean.main.jp/dev2";
//	private String        m_STR_url       ="http://kuc-arc-f.com/kinen/php/KIA102.php";
	//
	private jp.co.esourcejapan.fw.ComUtil m_Util = new ComUtil();
    /** Called when the activity is first created. */
	/* class */
	class JsObj {
		private Activity activity;

		public JsObj(Activity androidJs) {
			this.activity = androidJs;
		}

		public void exec_move01(final String s_url) {
			mHandler.post(new Runnable() {
				public void run() {
					try
					{
						proc_move( s_url);
					}catch(Exception e){
						e.printStackTrace();
						m_Util.errorDialog(TAA001Activity.this, e.getMessage());
					}
				}
			});
		}
   }	
	//
	private class WebViewClientLoading extends WebViewClient {
	    @Override
	    public boolean shouldOverrideUrlLoading(WebView view, String url) {
Log.d(TAG, "url=" +url);
			try
			{
				if(url.indexOf("mailto:") ==0){
					return false;
				}
//				if (Uri.parse(url).getHost().equals("maps.google.co.jp")) {
				else if (url.indexOf("maps.google.co.jp") >= 0) {
Log.d(TAG, "maps.google.co.jp.url=" +url);
					Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse( url ));
//		    		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	//	    		getApplicationContext().startActivity(intent);  
					startActivity(intent);
				    return false;
				}else{
			        view.loadUrl(url);
			        return true;
				}
			}catch(Exception e){
				e.printStackTrace();
			}
	        return true;
	    }
	}
	//
	class MyWebChromeClient extends WebChromeClient {
	    @Override
	    public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
	        if (message.length() != 0) {
	            AlertDialog.Builder buildr = new AlertDialog.Builder(TAA001Activity.this);
	            buildr.setPositiveButton("OK", new DialogInterface.OnClickListener() {
	                public void onClick(DialogInterface dialog, int whichButton) {
	                }
	            });	            
	            buildr.setTitle("Alert").setMessage(message).show();
	            result.cancel();
	            return true;
	        }
	        return false;
	    }
	}	
	//
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.taa001 );
        try
        {
        	mHandler = new Handler();
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
        	/*
        	m_Web.setWebChromeClient(new WebChromeClient() {
        	    public boolean onJsAlert(final WebView view,
        	    final String url, final String message, JsResult result) {
        	        Toast.makeText(TAA001Activity.this, message, 3000).show();
        	        result.confirm();
        	        return true;
        	     }
        	 });
*/
        	m_Web.setWebChromeClient(new MyWebChromeClient() );
    		JsObj jo = new JsObj(this);
    		m_Web.addJavascriptInterface(jo, "Android"); 
    		m_Web.loadUrl(m_STR_url);
    	}catch(Exception e){
    		throw e;
    	}
    }
    //
    public void proc_move(String url){
    	try
    	{
    		Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse( url ));
    		startActivity(intent);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }
    
    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
    	boolean result = super.onCreateOptionsMenu(menu);
    	menu.add(0, MENU1_ID, Menu.NONE, "Setting");
    	menu.add(0, MENU2_ID, Menu.NONE, "About").setIcon(android.R.drawable.ic_menu_info_details );
    	
    	return result;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
    	Intent intent;
    	switch( item.getItemId() ){
    	case MENU1_ID :
        	Intent myIntent = new Intent(this, Settings.class);
            startActivityForResult(myIntent, 0); 
    		return true;
    	case MENU2_ID :
    		intent = new Intent(this, TAB002Activity.class);
    		startActivity(intent); 
    		return true;
    	}
    	return super.onOptionsItemSelected(item);
    }    
    */
    
    
    
}