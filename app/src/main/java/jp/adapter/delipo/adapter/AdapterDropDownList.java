package jp.adapter.delipo.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class AdapterDropDownList extends BaseAdapter {
    String[] array = new String[]{"1990", "1991", "1992", "1993",
            "1994", "1995", "1996", "1997", "1998", "1999",
            "2000", "2001", "2002", "2003", "2004", "2005", "2006", "2007", "2008", "2009"};

    private Context context;

    public AdapterDropDownList(Context context) {
        this.context = context;
    }

    public int getCount() {
        // TODO Auto-generated method stub
        return array.length;
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return arg0;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        TextView text = new TextView(context);
        text.setHeight(100);
        text.setPadding(0, 0, 0, 0);
        text.setTextColor(Color.BLACK);
        text.setText(array[position]);
        text.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Log.i("clicked", array[position]);
            }
        });
        return text;
    }
}