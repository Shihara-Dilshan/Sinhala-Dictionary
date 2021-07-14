package com.example.sinhaladictionary.adapters;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sinhaladictionary.R;
import com.example.sinhaladictionary.fragments.EnglishWords;
import com.example.sinhaladictionary.models.EnglishWord;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

public class WordAdapter extends RecyclerView.Adapter<WordAdapter.ViewHolder> implements Filterable {
    private List<EnglishWord> englishWordList;
    private List<EnglishWord> englishWordListBackup;
    private ItemClickListener itemClickListener;

    public WordAdapter(List<EnglishWord> englishWordList, ItemClickListener itemClickListener) {
        this.englishWordList = englishWordList;
        this.itemClickListener = itemClickListener;
        this.englishWordListBackup = new ArrayList<>(englishWordList);
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

        holder.itemView.setOnClickListener(v -> {
            itemClickListener.onItemClick(englishWordList.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return englishWordList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String searchKeyword = constraint.toString();
                List<EnglishWord> tempList = new ArrayList<>();
                if(searchKeyword.length() == 0 || searchKeyword.isEmpty()){
                    tempList.addAll(englishWordListBackup);
                }else{
                    for(EnglishWord englishWord: englishWordListBackup){
                        if(englishWord.getWord().contains(searchKeyword)){
                            tempList.add(englishWord);
                        }

//                        tempList.sort(new Comparator<EnglishWord>() {
//                            @Override
//                            public int compare(EnglishWord o1, EnglishWord o2) {
//                                if(o1.getWord().length() > o2.getWord().length()){
//                                    return 1;
//                                }else{
//                                    return -1;
//                                }
//                            }
//                        });
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = tempList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                englishWordList.clear();
                englishWordList.addAll((Collection<? extends EnglishWord>) results.values);
                notifyDataSetChanged();
            }
        };
    }

    public interface ItemClickListener{
        void onItemClick(EnglishWord englishWord);
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
