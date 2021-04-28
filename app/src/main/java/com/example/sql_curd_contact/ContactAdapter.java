package com.example.sql_curd_contact;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sql_curd_contact.databinding.AddContactBinding;
import com.example.sql_curd_contact.databinding.ShowContactBinding;

import java.util.ArrayList;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.contactViewHolder> {

ArrayList<Contact> list;
Context context;

    public ContactAdapter(ArrayList<Contact> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public contactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ShowContactBinding binding = ShowContactBinding.inflate(LayoutInflater.from(context));
        return new contactViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull contactViewHolder holder, int position) {
        Contact c = list.get(position);
        holder.binding.textView.setText(c.getName());
        holder.binding.textView2.setText(""+c.getNumber());
        holder.binding.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(context,holder.binding.imageView);
                Menu menu = popupMenu.getMenu();
                menu.add("Edit");
                menu.add("Delite");
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        String title = menuItem.getTitle().toString();
                        if (title.equals("Edit")){
                            AlertDialog.Builder ad = new AlertDialog.Builder(context);
                            AddContactBinding binding = AddContactBinding.inflate(LayoutInflater.from(context));
                            View v = binding.getRoot();
                            binding.etName.setText(c.getName());
                            binding.etPhone.setText(""+c.getNumber());
                            ad.setView(v);
                            ad.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    String name = binding.etName.getText().toString();
                                    String number = binding.etPhone.getText().toString();
                                    Contact contact = new Contact(c.getId(),name,1);
                                    boolean status = ContactDov.update(contact,new SqliteHelper(context));
                                    if(status){
                                        list.set(position,contact);
                                        notifyDataSetChanged();
                                    }
                                    Toast.makeText(context, "Contact Update SucessFulley", Toast.LENGTH_SHORT).show();
                                }

                            });
                            ad.setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });

                            ad.show();
                        }
                        else if(title.equals("Delite")){
                            boolean status = ContactDov.delete(c,new SqliteHelper(context));
                            list.remove(position);
                            notifyDataSetChanged();
                        }

                        return false;
                    }
                });
                popupMenu.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class contactViewHolder extends RecyclerView.ViewHolder {
        ShowContactBinding binding;
        public contactViewHolder(ShowContactBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }
}
