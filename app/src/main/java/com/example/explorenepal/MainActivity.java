package com.example.explorenepal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.explorenepal.Fragment.AddData;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DatabaseHelper databaseHelper = new DatabaseHelper(this);

        ArrayList<Integer> id = new ArrayList<>();
        ArrayList<String> name = new ArrayList<>();
        ArrayList<String> category = new ArrayList<>();
        ArrayList<String> phone = new ArrayList<>();
        ArrayList<String> description = new ArrayList<>();
        ArrayList<Integer> image = new ArrayList<>();


        Cursor cursor = databaseHelper.getAllData();
        while (cursor.moveToNext()) {
            id.add(cursor.getInt(0));
            name.add(cursor.getString(1));
            category.add(cursor.getString(2));
            image.add(cursor.getInt(3));
            phone.add(cursor.getString(4));
            description.add(cursor.getString(5));

            ListView listView_database = (ListView) findViewById(R.id.listview_database);
            CustomListViewAdapter adapter;
            adapter = new CustomListViewAdapter(MainActivity.this, id, name, category, image, phone, description );
            listView_database.setAdapter(adapter);

        }


        ImageButton addButton = (ImageButton) findViewById(R.id.addButton);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(new AddData());
            }
        });
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragement_section, fragment);
        fragmentTransaction.commit();
    }
}

class CustomListViewAdapter extends ArrayAdapter<String>{

    Context context;
    ArrayList<String> name;
    ArrayList<String> category;
    ArrayList<String> phone;
    ArrayList<String> description;
    ArrayList<Integer> image;
    ArrayList<Integer> id;


    public CustomListViewAdapter(@NonNull Context context, ArrayList<Integer> id, ArrayList<String> name, ArrayList<String> category , ArrayList<Integer> image, ArrayList<String> phone, ArrayList<String> description) {
        super(context, R.layout.custom_listview);
        this.context =context;
        this.id = id;
        this.name = name;
        this.category = category;
        this.phone = phone;
        this.description = description;
        this.image = image;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup){
        LayoutInflater inflater =LayoutInflater.from(context);
        View rowView =inflater.inflate(R.layout.custom_listview, null, true);

        TextView textView_name = (TextView) rowView.findViewById(R.id.textView_name);
        TextView textView_category = (TextView) rowView.findViewById(R.id.textView_category);
        TextView textView_phone = (TextView) rowView.findViewById(R.id.textView_phone);
        TextView textView_description = (TextView) rowView.findViewById(R.id.textView_description);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.imageView_database);

        textView_name.setText(name.get(i).toString());
        textView_phone.setText(phone.get(i).toString());
        textView_category.setText(category.get(i).toString());
        textView_description.setText(description.get(i).toString());
        imageView.setImageResource(image.get(i).intValue());

        return rowView;
    }
}