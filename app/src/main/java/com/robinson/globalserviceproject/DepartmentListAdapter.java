package com.robinson.globalserviceproject;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.robinson.globalserviceproject.interfaces.ItemClickListener;
import com.robinson.globalserviceproject.model.EmailItem;
import com.robinson.globalserviceproject.utilities.SharedPreference;

import java.util.List;

public class DepartmentListAdapter extends RecyclerView.Adapter<DepartmentListAdapter.ViewHolder> {
    public List<EmailItem> rowBeans;
    public Context context;
    public ItemClickListener mItemClickListener;

    String EMAIL_REGEX = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private SharedPreference sharedPreference;
    private EmailItem mEmailItem;

    public DepartmentListAdapter(Context context, List<EmailItem> list, ItemClickListener mItemClickListener) {
        this.context = context;
        this.rowBeans = list;
        this.mItemClickListener = mItemClickListener;
    }

    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_home, viewGroup, false));
    }

    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        sharedPreference = new SharedPreference();
        mEmailItem = rowBeans.get(i);
        viewHolder.etEmail.setText(mEmailItem.getEmail());
        viewHolder.etEmail.setId(i);

        viewHolder.swEmail.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.d("getDATA", "------" + new Gson().toJson(mEmailItem));
                rowBeans.remove(i);
                notifyItemRemoved(i);
                notifyItemRangeChanged(i, rowBeans.size());
                //delete(mEmailItem);
                try {
                    SharedPreference.userList.remove(i);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    sharedPreference.removeData(context, mEmailItem);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        viewHolder.etEmail.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //    viewHolder.etEmail.setText("");
                if (viewHolder.etEmail.hasFocus()) {
                   // String email = viewHolder.etEmail.getText().toString().trim();
                    String emailData = viewHolder.etEmail.getText().toString().trim();
                    if (!emailData.equalsIgnoreCase("") && !emailData.matches(EMAIL_REGEX)) {
                        viewHolder.etEmail.setError("Please enter valid email");
                        Log.d("getDATA", "------" + "notvalid");
                    } else if (!emailData.equalsIgnoreCase("") && emailData.matches(EMAIL_REGEX)) {
                   /* if(sharedPreference.contains(new EmailItem(mEmailItem.getId(), email, mEmailItem.getType()),mEmailItem.getEmail()){

                    }*/

                        //  int getId = s.ge
                        // SharedPreference.userList.remove(i);.
                        mEmailItem.setEmail(emailData);
                        if (!sharedPreference.contains(SharedPreference.userList, mEmailItem.getEmail())) {
                            SharedPreference.userList.add(mEmailItem);
                            Log.d("getDATA", "------" + "add" + new Gson().toJson(SharedPreference.userList));

                        }else {
                            Log.d("getDATA", "------" + "notAdd");

                        }


                        // sharedPreference.removeData(context,  mEmailItem);
                        //sharedPreference.addData(context,  new EmailItem(mEmailItem.getId(), email, mEmailItem.getType()));
                        //    Log.d("getDATA", "------" + new Gson().toJson(sharedPreference.getData(context)));
                    }
                }
            }
        });
    }

    public int getItemCount() {
        return this.rowBeans.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        EditText etEmail;
        SwitchCompat swEmail;

        public ViewHolder(View view) {
            super(view);
            this.swEmail = (SwitchCompat) view.findViewById(R.id.swEmail);
            this.etEmail = (EditText) view.findViewById(R.id.etEmail);
        }
    }

}
