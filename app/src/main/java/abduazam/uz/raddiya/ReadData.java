package abduazam.uz.raddiya;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ReadData extends Activity {
    private static final String LOG_APP_TAG = "tag";
    WebView webView;
    String html_data;
    String css_data;
    String status;
    String alifbo;
    SQLiteDatabase db;
    String file_name, sign_text;
    private String file_B = "kun_tun";
    final Context context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_data);

        db = MainActivity.db;

        webView = (WebView)findViewById(R.id.webView);
        webView.getSettings().setBuiltInZoomControls(true);
        Intent i = getIntent();
        file_name = i.getStringExtra("read_data");
        InputStream inputStream = null;

        try {
            //switch(file_name)
            //{
                //case "bulim0mavzu0": inputStream = getResources().openRawResource(R.raw.bulim0mavzu0);
                    //break;
                //default:inputStream = getResources().openRawResource(R.raw.vhb_slf);
                   // break;
            //}
            //this.getResources().getIdentifier("nameOfDrawable", "drawable", this.getPackageName());
            inputStream = getResources().openRawResource(this.getResources().getIdentifier(file_name, "raw", this.getPackageName()));

            byte[] reader = new byte[inputStream.available()];
            while (inputStream.read(reader) != -1) {}
            html_data = new String(reader);
        } catch(IOException e) {
            Log.e(LOG_APP_TAG, e.getMessage());
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    Log.e(LOG_APP_TAG, e.getMessage());
                }
            }
        }
        loadText();

        if(status.contains("ku"))
        {
            css_data ="<html><head><meta charset=\"utf-8\"/></head><style type=\"text/css\">@font-face{font-family:MyFont;src: url(\"trado.ttf\")}#i{text-indent:1.5em;text-align:justify;font-family:Arial;font-size:16;}#ia{font-family:MyFont;direction:rtl;text-indent:1.5em;text-align:justify;font-size:18;}#title {text-align:center;font-family:Arial;font-size:16;font-weight:bold;}#manba{font-size: 80%; font-style: italic;}</style><body style=\"background: #fbf4e4; overflow-y: auto; overflow-x: hidden;color:#313131;\">";
            webView.loadData(css_data + html_data, "text/html; charset=utf-8", "UTF-8");
            status = "kun";
        }
        else if(status.contains("tu"))
        {
            css_data ="<html><head><meta charset=\"utf-8\"/></head><style type=\"text/css\">@font-face{font-family:MyFont;src: url(\"trado.ttf\")}#i{text-indent:1.5em;text-align:justify;font-family:Arial;font-size:16;}#ia{font-family:MyFont;direction:rtl;text-indent:1.5em;text-align:justify;font-size:18;}#title {text-align:center;font-family:Arial;font-size:16;font-weight:bold;}#manba{font-size: 80%; font-style: italic;}</style><body style=\"background: #1e1e1e;overflow-y: auto; overflow-x: hidden;color:#cbcdcb;\">";
            webView.loadData(css_data + html_data, "text/html; charset=utf-8", "UTF-8");
            status = "tun";
        }

        //registerForContextMenu(webView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_read_data, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (item.getItemId()) {
            case R.id.kun:
                if(status != "kun")
                {
                    for(int i=0; i<=1; i++)
                    {
                        css_data ="<html><head><meta charset=\"utf-8\"/></head><style type=\"text/css\">@font-face{font-family:MyFont;src: url(\"trado.ttf\")}#i{text-indent:1.5em;text-align:justify;font-family:Arial;font-size:16;}#ia{font-family:MyFont;direction:rtl;text-indent:1.5em;text-align:justify;font-size:18;}#title {text-align:center;font-family:Arial;font-size:16;font-weight:bold;}#manba{font-size: 80%; font-style: italic;}</style><body style=\"background: #fbf4e4; overflow-y: auto; overflow-x: hidden;color:#313131;\">";
                        webView.loadData(css_data + html_data, "text/html; charset=utf-8", "UTF-8");
                    }
                }
                status = "kun";
                saveText();
                return true;
            case R.id.tun:
                if(status != "tun")
                {
                    for(int i=0; i<=1; i++) {
                        css_data = "<html><head><meta charset=\"utf-8\"/></head><style type=\"text/css\">@font-face{font-family:MyFont;src: url(\"trado.ttf\")}#i{text-indent:1.5em;text-align:justify;font-family:Arial;font-size:16;}#ia{font-family:MyFont;direction:rtl;text-indent:1.5em;text-align:justify;font-size:18;}#title {text-align:center;font-family:Arial;font-size:16;font-weight:bold;}#manba{font-size: 80%; font-style: italic;}</style><body style=\"background: #1e1e1e;overflow-y: auto; overflow-x: hidden;color:#cbcdcb;\">";
                        webView.loadData(css_data + html_data, "text/html; charset=utf-8", "UTF-8");
                    }
                }
                status = "tun";
                saveText();
                return true;
            case R.id.belgi:
                    loadAlertDialog();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    void loadAlertDialog()
    {
        // get prompts.xml view
        LayoutInflater li = LayoutInflater.from(context);
        View promptsView = li.inflate(R.layout.promts, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);

        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

        final EditText userInput = (EditText) promptsView
                .findViewById(R.id.editTextDialogUserInput);
        alertDialogBuilder.setTitle("Изоҳ");
        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // get user input and set it to result
                                // edit text
                                sign_text = userInput.getText().toString().trim();
                                savedataDB();
                            }
                        })
                .setNegativeButton("Бекор",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }
    // load  alifbo
    void loadText() {

        status ="";
        try{
            FileInputStream fin = openFileInput(file_B);
            int c;
            String temp="";

            while( (c = fin.read()) != -1){
                temp = temp + Character.toString((char)c);
            }
            status = temp.trim();
        }
        catch(Exception e){

        }
        if (status == "" || status == null)
        {
            status = "kun";
        }
    }
    // save  alifbo
    void saveText() {
        try {
            FileOutputStream fOut = openFileOutput(file_B, MODE_PRIVATE);
            fOut.write(status.getBytes());
            fOut.close();
        }

        catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    void savedataDB()
    {
        try {
            String div_name="";
            switch(file_name.substring(0,6))
            {
                case "l_bul0": div_name = "Vahhobiy salafiylar - 1"; break;
                case "l_bul1": div_name = "Vahhobiy salafiylar - 2"; break;
                case "l_bul2": div_name = "Hizbut-tahrir"; break;
                case "l_bul3": div_name = "Boshqa oqimlar"; break;
                case "l_bul4": div_name = "Soxta so`fiylar"; break;
                case "l_bul5": div_name = "Umumiy raddiyalar"; break;
                case "k_bul0": div_name = "Ваҳҳобийлик-салафийлик - 1"; break;
                case "k_bul1": div_name = "Ваҳҳобийлик-салафийлик - 2"; break;
                case "k_bul2": div_name = "Ҳизбут-таҳрир"; break;
                case "k_bul3": div_name = "Бошқа оқимлар"; break;
                case "k_bul4": div_name = "Сохта сўфийлар"; break;
                case "k_bul5": div_name = "Умумий раддиялар"; break;
            }
            String datetime ="";
            Calendar c = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
            String strDate = sdf.format(c.getTime());
            datetime = strDate;
            sdf = new SimpleDateFormat("HH:mm");
            strDate = sdf.format(c.getTime());
            datetime = datetime +"//"+ strDate;
            if(sign_text.length()<1)
            {
                sign_text = "йўқ";
            }
            db.execSQL("insert into notification values('" + div_name + "','"+file_name.trim()+"','"+ sign_text +"','"+datetime+"');");
            Toast.makeText(getApplicationContext(), "Изоҳ киритилди!!!", Toast.LENGTH_LONG).show();
        }
         catch (SQLiteException e) {
            showMessage("Xатолик", e.toString());
            return;
        }
    }

    public void showMessage(String title,String message)
    {
        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setNegativeButton("ОК",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        builder.show();
    }

}
