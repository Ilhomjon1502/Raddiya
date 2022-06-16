package abduazam.uz.raddiya;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by azamjon.saminov on 29.12.2015.
 */
public class ViewNotifications extends Activity  {

    ListView listView;
    public static final String[] titles = new String[] { "Strawberry",
            "Banana", "Orange", "Mixed" };

    public static final String[] descriptions = new String[] {
            "It is an aggregate accessory fruit",
            "It is the largest herbaceous flowering plant", "Citrus Fruit",
            "Mixed Fruits" };
    List<RowItem> rowItems;
    List<String> file_nm = new ArrayList<>();
    List<String> note_time = new ArrayList<>();
    SQLiteDatabase db;
    String alifbo;
    int delete_item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_notification);
        listView = (ListView) findViewById(R.id.list_notif_item);
        TextView notif_title = (TextView)findViewById(R.id.notif_title);

        Intent in = getIntent();
        if(in.getStringExtra("alifbo").contains("kir"))
            alifbo = "kiril";
        else
            alifbo = "lotin";

        db = MainActivity.db;
        TextView textView = (TextView)findViewById(R.id.null_text);
        try{
            get_titles();
        }
        catch (Exception e)
        {
            listView.setVisibility(View.INVISIBLE);
            if (alifbo == "kiril")
            {
                textView.setText("Изоҳлар сони: 0 та");
                notif_title.setText("Киритилган изоҳлар");
            }
            else
            {
                notif_title.setText("Kiritilgan izohlar");
                textView.setText("Izohlar soni: 0 ta");
            }
            //showMessage("Хабар!", "Маълумот мавжуд эмас! МАЪЛУМОТЛАР КИРИТИШ бўлимига киринг.");
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //int i = position;
               //showMessage("xatolik",file_nm.get(position));
                Intent intent = new Intent(getApplicationContext(), ReadData.class);
                intent.putExtra("read_data",file_nm.get(position));
                startActivity(intent);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                delete_item = position;
                if(alifbo == "kiril")
                    showDeleteMessage("Диққат!", "Сиз ҳақиқатда танлаган изоҳингизни ўчирмоқчимисиз!");
                else
                    showDeleteMessage("Diqqat!", "Siz haqiqatda tanlagan izohingizni o`chirmoqchimisiz!");
                return true;
            }
        });
        //rowItems = new ArrayList<RowItem>();
        //for (int i = 0; i < titles.length; i++) {
        //    RowItem item = new RowItem(R.drawable.belgi, titles[i], descriptions[i]);
        //    rowItems.add(item);
        //}


        //CustomBaseAdapter adapter = new CustomBaseAdapter(this, rowItems);
        //listView.setAdapter(adapter);

    }

    public void get_titles()
    {
        Cursor cur;
        file_nm.clear();
        note_time.clear();
        //String division = in.getStringExtra("division");
        cur = db.rawQuery("SELECT * FROM notification", null);
        cur.moveToFirst();
        List<String> array = new ArrayList<>();
        rowItems = new ArrayList<RowItem>();
        int i=0;
        if(cur != null)
        {
            do{
                //String name = cur.getString(cur.getColumnIndex("title"));
                //array.add(name);
                i++;
                file_nm.add(cur.getString(cur.getColumnIndex("div_title")).trim());
                note_time.add(cur.getString(cur.getColumnIndex("created_at")));
                if (alifbo == "kiril")
                {
                    String name = "Бўлим: "+ cur.getString(cur.getColumnIndex("div_name"));
                    String note = "Изоҳ: " + cur.getString(cur.getColumnIndex("note_text"))+" | вақт: " + cur.getString(cur.getColumnIndex("created_at"))+"";
                    RowItem item = new RowItem(R.drawable.belgi, name, note);
                    rowItems.add(item);
                }
                else
                {
                    String name = "Bo`lim: "+ cur.getString(cur.getColumnIndex("div_name"));
                    String note = "Izoh: " + cur.getString(cur.getColumnIndex("note_text"))+" | vaqt: " + cur.getString(cur.getColumnIndex("created_at"))+"";
                    RowItem item = new RowItem(R.drawable.belgi, name, note);
                    rowItems.add(item);
                }
            }
            while(cur.moveToNext());
            CustomBaseAdapter adapter = new CustomBaseAdapter(this, rowItems);
            listView.setAdapter(adapter);
        }

        TextView textView = (TextView)findViewById(R.id.null_text);
        if (alifbo == "kiril")
            textView.setText("Изоҳлар сони: " + i + " та");
        else
            textView.setText("Izohlar soni: " + i + " ta");
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

    public void showDeleteMessage(String title,String message)
    {
        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("OК",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        try {

                            if (file_nm.get(delete_item) != null || file_nm.get(delete_item) != "") {
                                db.execSQL("delete from notification where div_title = '" + file_nm.get(delete_item).trim() + "'" +
                                        "and created_at ='"+note_time.get(delete_item)+"'");
                                if (alifbo == "kiril")
                                    Toast.makeText(getApplicationContext(), "Муваффаққиятли ўчирилди!!!", Toast.LENGTH_LONG).show();
                                else
                                    Toast.makeText(getApplicationContext(), "Muvaffaqqiyatli o`chirildi!!!", Toast.LENGTH_LONG).show();
                            }
                            try{
                                get_titles();

                            }
                            catch (Exception e)
                            {

                                listView.setVisibility(View.INVISIBLE);
                                TextView textView = (TextView)findViewById(R.id.null_text);
                                if (alifbo =="kiril")
                                    textView.setText("Изоҳлар сони: 0 та");
                                else
                                    textView.setText("Izohlar soni: 0 ta");
                                //showMessage("Хабар!", "Маълумот мавжуд эмас! МАЪЛУМОТЛАР КИРИТИШ бўлимига киринг.");
                            }
                        } catch (Exception e) {
                            //showMessage("Xатолик", "Мавзуни ўчиришда хатолик юз берди!");
                        }
                    }
                });
        builder.setNegativeButton("Бекор",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        builder.show();
    }
}
