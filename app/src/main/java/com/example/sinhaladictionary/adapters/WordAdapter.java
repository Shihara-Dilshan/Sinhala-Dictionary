package com.example.sinhaladictionary.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sinhaladictionary.R;
import com.example.sinhaladictionary.models.EnglishWord;

import java.util.List;

public class WordAdapter extends RecyclerView.Adapter<WordAdapter.ViewHolder> {
    private List<EnglishWord> englishWordList;

    public WordAdapter(List<EnglishWord> englishWordList) {
        this.englishWordList = englishWordList;
    }

    @NonNull
    @Override
    public WordAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.word_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WordAdapter.ViewHolder holder, int position) {
        String word = englishWordList.get(position).getWord();
        String definition = englishWordList.get(position).getDefinition();

        holder.setData(word, definition);
    }

    @Override
    public int getItemCount() {
        return englishWordList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView =itemView.findViewById(R.id.word_container);
        }

        public void setData(String word, String definition){
            textView.setText(word);
        }
    }
}
