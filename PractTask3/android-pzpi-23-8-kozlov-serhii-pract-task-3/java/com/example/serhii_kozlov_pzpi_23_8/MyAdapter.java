package com.example.serhii_kozlov_pzpi_23_8;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private final List<String> mDataset;

    // Конструктор адаптера
    public MyAdapter(List<String> dataset) {
        mDataset = dataset;
    }

    // Створення ViewHolder
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_view, parent, false);
        return new MyViewHolder(view);
    }

    // Зв'язування даних із ViewHolder
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.textView.setText(mDataset.get(position));
    }

    // Кількість елементів у списку
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    // ViewHolder для зберігання посилань
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public final TextView textView;

        public MyViewHolder(View view) {
            super(view);
            textView = view.findViewById(R.id.itemText);
        }
    }
}
