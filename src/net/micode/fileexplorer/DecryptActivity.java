package net.micode.fileexplorer;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Administrator on 2015/1/17 0017.
 */
public class DecryptActivity extends Activity {
    ListView mListView;
    private ArrayList<StringBuffer> mArrList;
    private ArrayAdapter<StringBuffer> mArrAdapter;
    private PrivateDatabaseHelper mDDBH;
    Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.decrypt_list);
        mListView = (ListView)this.findViewById(R.id.decrypt_listview);
        mArrList = new ArrayList<StringBuffer>();
        mArrAdapter = new ArrayAdapter<StringBuffer>(this, android.R.layout.simple_list_item_1, mArrList);
        mDDBH = new PrivateDatabaseHelper(this);
        mContext = this;
        Cursor c = mDDBH.query();//查询并获得游标
        if(c.moveToFirst()){//判断游标是否为空
            do {
                StringBuffer sb = new StringBuffer();
                String location = c.getString(2);
                sb.append(location);
                mArrAdapter.add(sb);
            } while (c.moveToNext());
        }
        c.close();
        mListView.setAdapter(mArrAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                StringBuffer sb = mArrAdapter.getItem(position);
                DecryptFile(sb.toString());
                PrivateDatabaseHelper.getInstance(mContext).delete(sb.toString());
                mArrAdapter.remove(sb);
            }
        });
    }
    public boolean DecryptFile(String f){
        try {
            FileOutputStream fs = new FileOutputStream(f);
            InputStream fs1 = new FileInputStream(f + "sh1");
            InputStream fs2 = new FileInputStream(f + "sh2");
            byte[] buffer = new byte[1024 * 1024];
            int length1 = 0, length2 = 0;
            while(length1 != -1 || length2 != -1){
                length1 = fs1.read(buffer);
                if(length1 != -1){
                    fs.write(buffer, 0, length1);
                }
                length2 = fs2.read(buffer);
                if(length2 != -1){
                    fs.write(buffer, 0, length2);
                }
            }
            fs1.close();
            fs2.close();

            File file1 = new File(f + "sh1");
            File file2 = new File(f + "sh2");
            file1.delete();
            file2.delete();
        }catch (Exception e){
            e.printStackTrace();
        }
        return true;
    }
}
