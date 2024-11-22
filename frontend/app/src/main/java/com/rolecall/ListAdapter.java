package com.rolecall;

import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rolecall.R;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    private final List<Pair<String, String>> roles;

    public ListAdapter(List<Pair<String, String>> items) {
        this.roles = items;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        EditText editText;

        public ViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.role_name);
            editText = view.findViewById(R.id.role_count);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_role_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Pair<String, String> item = roles.get(position);
        holder.editText.setText(item.first);
        holder.title.setText(item.second);

    }

    @Override
    public int getItemCount() {
        return roles.size();
    }
}
