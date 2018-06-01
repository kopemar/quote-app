package com.example.quoteforthisday;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.quoteforthisday.model.Quote;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class QuoteAdapter extends RecyclerView.Adapter<QuoteAdapter.QuoteViewHolder> {
    private List<Quote> listOfItems;

    public QuoteAdapter(List<Quote> listOfItems, ListItemClickListener itemClickListener) {
        this.listOfItems = listOfItems;
        this.itemClickListener = itemClickListener;
        Collections.sort(listOfItems, new Comparator<Quote>() {
            @Override
            public int compare(Quote o1, Quote o2) {
                String s1 = o1.getCategory();
                String s2 = o2.getCategory();
                return s1.compareToIgnoreCase(s2);
            }
        });
    }

    class QuoteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textToShow;

        public QuoteViewHolder(View view) {
            super(view);
            textToShow = view.findViewById(R.id.item);
            textToShow.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onListItemClick(getAdapterPosition());
        }
    }

    @NonNull
    @Override
    public QuoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Boolean attachViewImmediatelyToParent = false;
        View singleItemLayout = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, attachViewImmediatelyToParent);
        QuoteViewHolder quoteViewHolder = new QuoteViewHolder(singleItemLayout);
        return quoteViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull QuoteViewHolder holder, int position) {
        holder.textToShow.setText(listOfItems.get(position).getText());
    }

    @Override
    public int getItemCount() {
        return listOfItems.size();
    }
    ListItemClickListener itemClickListener;
    public interface ListItemClickListener {
        void onListItemClick(int position);
    }
}
