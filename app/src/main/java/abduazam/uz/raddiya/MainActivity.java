package abduazam.uz.raddiya;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//import android.content.Context;
public class MainActivity extends Activity {


    ExpandableListView expListView;
    ExpandableListAdapter listAdapter;
    List<String> listDataHeader;
    String alifbo;
    ImageView mundarija;
    private String file_A = "alifbo_data";
    HashMap<String, List<String>> listDataChild;
    public static SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //create base and table
        //deleteDatabase("NoteDB");
        db=openOrCreateDatabase("NoteDB", Context.MODE_PRIVATE, null);
        db.execSQL("create table if not exists notification (div_name VARCHAR NOT NULL, div_title VARCHAR NOT NULL, note_text VARCHAR, created_at varchar);");
        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.lvExp);
        TextView app_title = (TextView)findViewById(R.id.app_title);
        mundarija = (ImageView)findViewById(R.id.mundarija);
        // set data as alifbo
        loadText();

        if (alifbo.contains("kir"))
        {
            alifbo = "kiril";
            app_title.setText("ОҚИМЛАРГА РАДДИЯ");
            mundarija.setImageResource(R.drawable.mundar2);
            prepareDataKiril();

        }
        else if(alifbo.contains("lot"))
        {
            alifbo = "lotin";
            app_title.setText("OQIMLARGA RADDIYA");
            mundarija.setImageResource(R.drawable.mundar);
            prepareDataLotin();
        }
        // Listview Group click listener
        expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                // Toast.makeText(getApplicationContext(),
                // "Group Clicked " + listDataHeader.get(groupPosition),
                // Toast.LENGTH_SHORT).show();
                return false;
            }
        });


        // Listview Group expanded listener
        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                //Toast.makeText(getApplicationContext(),
                //        listDataHeader.get(groupPosition) + " Expanded",
                //        Toast.LENGTH_SHORT).show();
            }
        });

        // Listview Group collasped listener
        expListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                //Toast.makeText(getApplicationContext(),
                //        listDataHeader.get(groupPosition) + " Collapsed",
                //        Toast.LENGTH_SHORT).show();

            }
        });

        // Listview on child click listener
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                // TODO Auto-generated method stub
                /*Toast.makeText(
                        getApplicationContext(),
                        listDataHeader.get(groupPosition)
                                + " : "
                                + listDataChild.get(
                                listDataHeader.get(groupPosition)).get(
                                childPosition), Toast.LENGTH_SHORT)
                        .show();*/

                Intent intent = new Intent(getApplicationContext(), ReadData.class);
                if(alifbo == "kiril")
                    intent.putExtra("read_data","k_bul"+ groupPosition + "_m"+ childPosition);
                else
                    intent.putExtra("read_data", "l_bul" + groupPosition + "_m" + childPosition);
                startActivity(intent);
                return false;
            }
        });
    }

    /*
     * Preparing the list data
     */
    private void prepareListDataKiril() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("1. Ваҳҳобий салафийлар - 1");
        listDataHeader.add("2. Ваҳҳобий салафийлар - 2");
        listDataHeader.add("3. Ҳизбут-таҳрир");
        listDataHeader.add("4. Бошқа оқимлар");
        listDataHeader.add("5. Сохта сўфийлар");
        listDataHeader.add("6. Умумий раддиялар");

        // Adding child data
        List<String> maqola = new ArrayList<String>();
        maqola.add("\"Аҳли суннат ва жамоат\" кимлар?");
        maqola.add("Мужтаҳид имомларга отилган таъналар");
        maqola.add("Уламоларни ҳақорат қилганга таъзир берилади");
        maqola.add("Имоми Аъзам Абу Ҳанифа -1");
        maqola.add("Имоми Аъзам Абу Ҳанифа -2");
        maqola.add("Тақлид");
        maqola.add("Тақлид ҳақида");
        maqola.add("Саъдуддин ат-тафтазоний Ашъарийми ёки Мотуридийми?");
        maqola.add("Султон Салоҳиддин Айюбий");
        maqola.add("Уламоларни яҳудийликда айблаш");
        maqola.add("Исломда раҳбарга муносабат");
        maqola.add("Дам солиш ва тумор тақиш");
        maqola.add("Яхшилик ҳам, ёмонлик ҳам Аллоҳдандир");
        maqola.add("Китобларга хиёнат");
        maqola.add("Қуръони Каримни шеър назмида тафсир қилиш");

        List<String> hizb = new ArrayList<String>();
        hizb.add("Ҳизбут-таҳрир");
        hizb.add("Ҳизбут-таҳрир ҳақида ҳақиқат");
        hizb.add("Ҳизбут-таҳрир ва таблиғчилар");
        hizb.add("Ҳизбут-таҳрирнинг ботил ақидалари");
        hizb.add("Ҳизб даъвати");
        hizb.add("Ҳизбут-таҳрирнинг \"иймон келтириш\"даги бидъати");
        hizb.add("Ҳизбут-таҳрирнинг \"қазо ва қадар\"даги бидъати");
        hizb.add("Бидъатчининг оқибати");
        hizb.add("Тақиюддин Набаҳоний ва руҳ тушунчаси");
        hizb.add("Ҳанафий мазҳаби ақидасида \"халифалик\" масаласи");
        hizb.add("Фаҳш фильмлар");
        hizb.add("Исломда гуруҳ тузиш ҳукми");

        List<String> tariqat = new ArrayList<String>();
        tariqat.add("Аллоҳдан бошқага сажда қилиш ҳукми");
        tariqat.add("Тасаввуф шариатга мослиги билан мўътабар");
        tariqat.add("Ғайб масаласи");
        tariqat.add("Ҳиндистонда \"такфир\" балоси");
        tariqat.add("Диний таассуб");
        tariqat.add("Таассуб, такфир ва барлавийлик");
        tariqat.add("Домла Ҳиндистоний раҳматуллоҳи алайҳ");
        tariqat.add("\"Нур\" ҳадиси саҳиҳми? ");
        tariqat.add("Сохта сўфийларнинг саждакорлиги");
        tariqat.add("Пайғамбар алайҳиссаломнинг қабрлари зиёрати ҳақида");
        tariqat.add("Қабрларни айланиб тавоф қилиш ҳукми");
        tariqat.add("Ўлганга савоб бағишлаш ҳукми");
        tariqat.add("Назр ҳақида");
        tariqat.add("Диний мутаассиблик ва экстремизм");
        tariqat.add("Исломда мутаассибликка муносабат");
        tariqat.add("Маҳалли қиём");


        List<String> oqim = new ArrayList<String>();
        oqim.add("Оқимларнинг кўриниши");
        oqim.add("Фирқа ва оқимларга қўшилиш");
        oqim.add("Фирқалардан огоҳлик");
        oqim.add("Шайтон изидан эргашганлар");
        oqim.add("Фитналардан огоҳлантириш");
        oqim.add("Террор – исломга ёт ғоя");
        oqim.add("Замонавий ҳижрат");
        oqim.add("Зулм");
        oqim.add("Жиходчилар диний экстремистик оқими");
        oqim.add("Жиҳод никоҳи - ҳаром");
        oqim.add("Илм ўрганиб адашганлар");
        oqim.add("Мазҳабсизлик");
        oqim.add("Ғаззолийдан ҳақиқат");
        oqim.add("\"Такфир\"га қўшилмаймиз ва унга қаршимиз");
        oqim.add("Ироқ ва Шом давлати – мусулмонларнинг фитнаси");
        oqim.add("ИШИД");
        oqim.add("Ироқ ва Сурия ислом давлати");
        oqim.add("ИШИДга қўшилиб ўлган жоҳилиятда ўлади");
        oqim.add("Нотиқлик – ақидаси тўғрилик эмас");
        oqim.add("Қодиёнийлик ёхуд Аҳмадийлик ");
        oqim.add("Таблиғ");
        oqim.add("Акромийлар");

        List<String> vhb_slf1 = new ArrayList<String>();
        vhb_slf1.add("Салафийлар");
        vhb_slf1.add("Салафийларнинг шубуҳотлари");
        vhb_slf1.add("Ваҳҳобийми ёки салафий");
        vhb_slf1.add("Ваҳҳобийлар тарихи ва уларнинг белгилари");
        vhb_slf1.add("Ваҳҳобийларнинг ўзига хос белгилари");
        vhb_slf1.add("Ваҳҳобийларнинг адашуви");
        vhb_slf1.add("Усаймин");
        vhb_slf1.add("Салафийликаро зиддиятлар - 1");
        vhb_slf1.add("Салафийликаро зиддиятлар - 2");
        vhb_slf1.add("Салафийликаро зиддиятлар - 3");
        vhb_slf1.add("Салафийликаро зиддиятлар - 4");
        vhb_slf1.add("Салафийликаро зиддиятлар - 5");
        vhb_slf1.add("Мунозара");
        vhb_slf1.add("\"Шадд ар-риҳол\"нинг ҳукми");
        vhb_slf1.add("Ҳоизанинг талоғи масаласида ибн Таймиянинг фикри");
        vhb_slf1.add("Тасбеҳ ишлатишнинг ҳақиқати");
        vhb_slf1.add("Тавассул масаласида умматга қарши бўлганлар");
        vhb_slf1.add("Муташобиҳ оятларни таъвил қилиш ҳақида");
        vhb_slf1.add("Марҳумларга тиловат савобини бағишламоқ");
        vhb_slf1.add("Сафарда суннат намозларини ўқиш");
        vhb_slf1.add("Васила");
        vhb_slf1.add("Пайғамбаримиз а.с зиёратларига бориш ҳақида рисола");


        List<String> vhb_slf2 = new ArrayList<String>();
        vhb_slf2.add("Ибн Таймия");
        vhb_slf2.add("Ибн Таймия (р.ҳ.): ўликлар эшитади");
        vhb_slf2.add("Ибн Таймия (р.ҳ.): ўликка Қуръон тиловати савобини бағишлаш");
        vhb_slf2.add("Ибн Таймия (р.ҳ.)нинг аҳли суннага зид фикрлари");
        vhb_slf2.add("Ибн Боз ҳақида");
        vhb_slf2.add("Мазҳаб танқидчиларига");
        vhb_slf2.add("Мазҳабларни инкор қилиш");
        vhb_slf2.add("\"Агар ҳадис саҳиҳ бўлса, бу менинг мазҳабимдир\" қавли ҳақида");
        vhb_slf2.add("Девбанд уламолари ваҳҳобийлар ҳақида");
        vhb_slf2.add("Анваршоҳ Кашмирий (р.ҳ.) ваҳҳобийлар бошлиғи ҳақида");
        vhb_slf2.add("Жума муборак");
        vhb_slf2.add("Намоз ва такфир");
        vhb_slf2.add("Ботил тенгситув");
        vhb_slf2.add("Мактуб");
        vhb_slf2.add("Қазо вожиб");
        vhb_slf2.add("Мавлид масаласи");
        vhb_slf2.add("Одам Ато");
        vhb_slf2.add("Муаллақ талоқ");
        vhb_slf2.add("Ер шакли");
        vhb_slf2.add("Ҳулул ақийдаси");
        vhb_slf2.add("Қаршилик");
        vhb_slf2.add("Мен қандай қилиб ҳанафий бўлдим");

        listDataChild.put(listDataHeader.get(0), vhb_slf1); // Header, Child data
        listDataChild.put(listDataHeader.get(1), vhb_slf2);
        listDataChild.put(listDataHeader.get(2), hizb);
        listDataChild.put(listDataHeader.get(3), oqim);
        listDataChild.put(listDataHeader.get(4), tariqat);
        listDataChild.put(listDataHeader.get(5), maqola);
    }
    private void prepareListDataLotin() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("1. Vahhobiy salafiylar - 1");
        listDataHeader.add("2. Vahhobiy salafiylar - 2");
        listDataHeader.add("3. Hizbut-tahrir");
        listDataHeader.add("4. Boshqa oqimlar");
        listDataHeader.add("5. Soxta so`fiylar");
        listDataHeader.add("6. Umumiy raddiyalar");

        // Adding child data
        List<String> maqola = new ArrayList<String>();
        maqola.add("\"Ahli sunnat va jamoat\" kimlar?");
        maqola.add("Mujtahid imomlarga otilgan ta`nalar");
        maqola.add("Ulamolarni haqorat qilganga ta`zir beriladi");
        maqola.add("Imomi A`zam Abu Hanifa -1");
        maqola.add("Imomi A`zam Abu Hanifa -2");
        maqola.add("Taqlid");
        maqola.add("Taqlid haqida");
        maqola.add("Sa`duddin at-taftazoniy Ash`ariymi yoki Moturidiymi?");
        maqola.add("Sulton Salohiddin Ayyubiy");
        maqola.add("Ulamolarni yahudiylikda ayblash");
        maqola.add("Islomda rahbarga munosabat");
        maqola.add("Dam solish va tumor taqish");
        maqola.add("Yaxshilik ham, yomonlik ham Allohdandir");
        maqola.add("Kitoblarga hiyonat");
        maqola.add("Qur`oni Karimni she`r nazmida tafsir qilish");

        List<String> hizb = new ArrayList<String>();
        hizb.add("Hizbut-tahrir");
        hizb.add("Hizbut-tahrir haqida haqiqat");
        hizb.add("Hizbut-tahrir va tablig`chilar");
        hizb.add("Hizbut-tahrirning botil aqidalari");
        hizb.add("Hizb da`vati");
        hizb.add("Hizbut-tahrirning \"iymon keltirish\"dagi bid`ati");
        hizb.add("Hizbut-tahrirning \"qazo va qadar\"dagi bid`ati");
        hizb.add("Bid`atchining oqibati");
        hizb.add("Taqiyuddin Nabahoniy va ruh tushunchasi");
        hizb.add("Hanafiy mazhabi aqidasida \"xalifalik\" masalasi");
        hizb.add("Fahsh filmlar");
        hizb.add("Islomda guruh tuzish hukmi");

        List<String> tariqat = new ArrayList<String>();
        tariqat.add("Allohdan boshqaga sajda qilish hukmi");
        tariqat.add("Tasavvuf shariatga mosligi bilan mo`tabar");
        tariqat.add("G`ayb masalasi");
        tariqat.add("Hindistonda \"takfir\" balosi");
        tariqat.add("Diniy taassub");
        tariqat.add("Taassub, takfir va barlaviylik");
        tariqat.add("Domla Hindistoniy rahmatullohi alayh");
        tariqat.add("\"Nur\" hadisi sahihmi? ");
        tariqat.add("Soxta so`fiylarning sajdakorligi");
        tariqat.add("Payg`ambar alayhissalomning qabrlari ziyorati haqida");
        tariqat.add("Qabrlarni aylanib tavof qilish hukmi");
        tariqat.add("O`lganga savob bag`ishlash hukmi");
        tariqat.add("Nazr haqida");
        tariqat.add("Diniy mutaassiblik va ekstremizm");
        tariqat.add("Islomda mutaassiblikka munosabat");
        tariqat.add("Mahalli qiyom");


        List<String> oqim = new ArrayList<String>();
        oqim.add("Oqimlarning ko`rinishi");
        oqim.add("Firqa va oqimlarga qo`shilish");
        oqim.add("Firqalardan ogohlik");
        oqim.add("Shayton izidan ergashganlar");
        oqim.add("Fitnalardan ogohlantirish");
        oqim.add("Terror – islomga yot g`oya");
        oqim.add("Zamonaviy hijrat");
        oqim.add("Zulm");
        oqim.add("Jihodchilar diniy ekstremistik oqimi");
        oqim.add("Jihod nikohi - harom");
        oqim.add("Ilm o`rganib adashganlar");
        oqim.add("Mazhabsizlik");
        oqim.add("G`azzoliydan haqiqat");
        oqim.add("\"Takfir\"ga qo`shilmaymiz va unga qarshimiz");
        oqim.add("Iroq va Shom davlati – musulmonlarning fitnasi");
        oqim.add("ISHID");
        oqim.add("Iroq va Suriya islom davlati");
        oqim.add("ISHIDga qo`shilib o`lgan johiliyatda o`ladi");
        oqim.add("Notiqlik – aqidasi to`g`rilik emas");
        oqim.add("Qodiyoniylik yohud Ahmadiylik ");
        oqim.add("Tablig`");
        oqim.add("Akromiylar");

        List<String> vhb_slf1 = new ArrayList<String>();
        vhb_slf1.add("Salafiylar");
        vhb_slf1.add("Salafiylarning shubuhotlari");
        vhb_slf1.add("Vahhobiymi yoki salafiy");
        vhb_slf1.add("Vahhobiylar tarixi va ularning belgilari");
        vhb_slf1.add("Vahhobiylarning o`ziga xos belgilari");
        vhb_slf1.add("Vahhobiylarning adashuvi");
        vhb_slf1.add("Usaymin");
        vhb_slf1.add("Salafiylikaro ziddiyatlar - 1");
        vhb_slf1.add("Salafiylikaro ziddiyatlar - 2");
        vhb_slf1.add("Salafiylikaro ziddiyatlar - 3");
        vhb_slf1.add("Salafiylikaro ziddiyatlar - 4");
        vhb_slf1.add("Salafiylikaro ziddiyatlar - 5");
        vhb_slf1.add("Munozara");
        vhb_slf1.add("\"Shadd ar-rihol\"ning hukmи");
        vhb_slf1.add("Hoizaning talog`i masalasida ibn Taymiyaning fikri");
        vhb_slf1.add("Tasbeh ishlatishning haqiqati");
        vhb_slf1.add("Tavassul masalasida ummatga qarshi bo`lganlar");
        vhb_slf1.add("Mutashobih oyatlarni ta`vil qilish haqida");
        vhb_slf1.add("Marhumlarga tilovat savobini bag`ishlamoq");
        vhb_slf1.add("Safarda sunnat namozlarini o`qish");
        vhb_slf1.add("Vasila");
        vhb_slf1.add("Payg`ambarimiz a.s ziyoratlariga borish haqida risola");


        List<String> vhb_slf2 = new ArrayList<String>();
        vhb_slf2.add("Ibn Taymiya");
        vhb_slf2.add("Ibn Taymiya (r.h.): o`liklar eshitadi");
        vhb_slf2.add("Ibn Tay`miya (r.h.): o`likka Qur`on tilovati savobini bag`ishlash");
        vhb_slf2.add("Ibn Taymiya (r.h.)ning ahli sunnaga zid fikrlari");
        vhb_slf2.add("Ibn Boz haqida");
        vhb_slf2.add("Mazhab tanqidchilariga");
        vhb_slf2.add("Mazhablarni inkor qilish");
        vhb_slf2.add("\"Agar hadis sahih bo`lsa, bu mening mazhabimdir\" qavli haqida");
        vhb_slf2.add("Devband ulamolari vahhobiylar haqida");
        vhb_slf2.add("Anvarshox Kashmiriy (r.h.) vahhobiylar boshlig`i haqida");
        vhb_slf2.add("Juma muborak");
        vhb_slf2.add("Namoz va takfir");
        vhb_slf2.add("Botil tengsituv");
        vhb_slf2.add("Maktub");
        vhb_slf2.add("Qazo vojib");
        vhb_slf2.add("Mavlid masalasi");
        vhb_slf2.add("Odam Ato");
        vhb_slf2.add("Muallaq taloq");
        vhb_slf2.add("Yer shakli");
        vhb_slf2.add("Hulul aqiydasi");
        vhb_slf2.add("Qarshilik");
        vhb_slf2.add("Men qanday qilib hanafiy bo`ldim");

        listDataChild.put(listDataHeader.get(0), vhb_slf1); // Header, Child data
        listDataChild.put(listDataHeader.get(1), vhb_slf2);
        listDataChild.put(listDataHeader.get(2), hizb);
        listDataChild.put(listDataHeader.get(3), oqim);
        listDataChild.put(listDataHeader.get(4), tariqat);
        listDataChild.put(listDataHeader.get(5), maqola);
    }
    public  void prepareDataKiril()
    {
        // preparing list data
        prepareListDataKiril();

        //listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);
        listAdapter = new ExpandableListAdapter(this, listDataHeader,listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);
    }
    public  void prepareDataLotin()
    {
        // preparing list data
        prepareListDataLotin();

         listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.my_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        TextView app_title = (TextView)findViewById(R.id.app_title);
        switch (item.getItemId())
        {
            case R.id.kiril:prepareDataKiril();
                alifbo = "kiril";
                saveText();
                app_title.setText("ОҚИМЛАРГА РАДДИЯ");
                mundarija.setImageResource(R.drawable.mundar2);
            break;
            case R.id.lotin:prepareDataLotin();alifbo = "lotin";
                app_title.setText("OQIMLARGA RADDIYA");
                mundarija.setImageResource(R.drawable.mundar);
                saveText();
                break;
            case R.id.about: loadHelp();
                break;
            case R.id.signs:
                Intent intent = new Intent(getApplicationContext(), ViewNotifications.class);
                intent.putExtra("alifbo",alifbo);
                startActivity(intent);
                break;
        }

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }
    public void loadHelp()
    {

        Intent intent = new Intent(getApplicationContext(), DasturHaqida.class);
        if(alifbo == "kiril")
            intent.putExtra("help_data","dastur_k.htm");
        else
            intent.putExtra("help_data", "dastur_l.htm");
        startActivity(intent);
    }

    // load  alifbo
    void loadText() {

        alifbo ="";
        try{
            FileInputStream fin = openFileInput(file_A);
            int c;
            String temp="";

            while( (c = fin.read()) != -1){
                temp = temp + Character.toString((char)c);
            }
            alifbo=temp.trim();
        }
        catch(Exception e){

        }
        if (alifbo == "" || alifbo == null)
        {
            alifbo = "kiril";
        }
    }
    // save  alifbo
    void saveText() {
        try {
            FileOutputStream fOut = openFileOutput(file_A, MODE_PRIVATE);
            fOut.write(alifbo.getBytes());
            fOut.close();
        }

        catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
