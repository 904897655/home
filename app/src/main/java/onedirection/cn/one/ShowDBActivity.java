package onedirection.cn.one;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import java.util.ArrayList;
import java.util.List;

public class ShowDBActivity extends Activity {
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showdb);
        ListView show = (ListView)findViewById(R.id.show);
        db = SQLiteDatabase.openOrCreateDatabase(this.getFilesDir().toString()+ "/translate.db3", null);
        TranslateAdapter adapter = new TranslateAdapter(ShowDBActivity.this, R.layout.line);
        List<Translate> list = queryAll();
        for(Translate translate:list)
                adapter.add(translate);
        show.setAdapter(adapter);
    }

    public List<Translate> queryAll(){

        try {
            Cursor cursor = db.rawQuery("select * from history", null);
            List<Translate> list = new ArrayList<Translate>();
            while (cursor.moveToNext())
            {
                String src = cursor.getString(1);
                String dst = cursor.getString(2);
                list.add(new Translate(list.size()+1+"",src,dst));
                Log.v("debug","-----------------------------------"+"index："+list.size()+1+"src："+src+"\t"+"dst: "+dst+"-----------------------------------");
            }
            return  list;
        }catch (SQLiteException se){
            db.execSQL("create table history(index integer"
                    + " primary key autoincrement,"
                    + " src varchar(255),"
                    + " dst varchar(255))");
            return null;
        }
    }

    class TranslateAdapter extends ArrayAdapter<Translate> {

        private int mResourceId;

        public TranslateAdapter(Context context, int textViewResourceId) {
            super(context, textViewResourceId);
            this.mResourceId = textViewResourceId;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Translate user = getItem(position);
            LayoutInflater inflater = getLayoutInflater();
            View view = inflater.inflate(mResourceId, null);


            TextView index = (TextView) view.findViewById(R.id.index);
            TextView srcText = (TextView) view.findViewById(R.id.src);
            TextView dstText = (TextView) view.findViewById(R.id.dst);

            index.setText(user.getIndex());
            srcText.setText(user.getSrc());
            dstText.setText(user.getDst());

            return view;
        }
    }
}
