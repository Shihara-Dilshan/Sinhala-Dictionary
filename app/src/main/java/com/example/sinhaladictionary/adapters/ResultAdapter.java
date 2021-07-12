package com.example.sinhaladictionary.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sinhaladictionary.R;
import com.example.sinhaladictionary.models.SinhalaMeaning;

import java.util.List;

public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.ViewHolder> {
    private List<SinhalaMeaning> sinhalaMeanings;

    public ResultAdapter(List<SinhalaMeaning> sinhalaMeanings) {
        this.sinhalaMeanings = sinhalaMeanings;
    }

    @NonNull
    @Override
    public ResultAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.result_word_card, parent, false);
        return new ResultAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ResultAdapter.ViewHolder holder, int position) {
        String meaing = sinhalaMeanings.get(position).getMean();

        holder.setData(meaing);

    }

    @Override
    public int getItemCount() {
        return sinhalaMeanings.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView =itemView.findViewById(R.id.textView3);
        }

        public void setData(String meaning){
            textView.setText(meaning);
        }
    }
}
