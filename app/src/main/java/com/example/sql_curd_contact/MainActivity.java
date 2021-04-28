package com.example.sql_curd_contact;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.sql_curd_contact.databinding.ActivityMainBinding;
import com.example.sql_curd_contact.databinding.AddContactBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
ActivityMainBinding binding;
SqliteHelper helper;
ArrayList<Contact> list;
ContactAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());
        helper = new SqliteHelper(MainActivity.this);
        list = ContactDov.readContact(helper);
        adapter = new ContactAdapter(list,this);
        binding.rv.setAdapter(adapter);
        binding.rv.setLayoutManager(new LinearLayoutManager(this));


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("Add Contact");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        String title = item.getTitle().toString();
        if (title.equals("Add Contact")){
            AlertDialog.Builder ab = new AlertDialog.Builder(MainActivity.this);
            AddContactBinding binding = AddContactBinding.inflate(LayoutInflater.from(this));
            View v = binding.getRoot();
            ab.setView(v);
            ab.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    String name = binding.etName.getText().toString();
                    String number = binding.etPhone.getText().toString();
                    Contact c = new Contact(1,name, 1/*Integer.parseInt(number)*/);
                    boolean status =ContactDov.add(c,helper);
                    if(status){
                        list.add(c);
                        adapter.notifyDataSetChanged();
                    }
                    Toast.makeText(MainActivity.this, "Data Saves SucessFulley", Toast.LENGTH_SHORT).show();


                }
            });
            ab.setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {


                }
            });
            ab.show();

        }
        return super.onOptionsItemSelected(item);
    }
}