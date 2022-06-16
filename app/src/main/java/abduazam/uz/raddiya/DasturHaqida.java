package abduazam.uz.raddiya;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.TextView;

/**
 * Created by azamjon.saminov on 29.12.2015.
 */
public class DasturHaqida extends Activity {
    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dastur_haqida);

        webView = (WebView)findViewById(R.id.help_html);
        TextView help_title= (TextView)findViewById(R.id.help_title);
        webView.getSettings().setBuiltInZoomControls(true);
        Intent i = getIntent();
        String file_name = i.getStringExtra("help_data");
        if (file_name.contains("k"))
            help_title.setText("Дастур хақида");

        webView.loadUrl("file:///android_res/raw/"+file_name);

    }
}
