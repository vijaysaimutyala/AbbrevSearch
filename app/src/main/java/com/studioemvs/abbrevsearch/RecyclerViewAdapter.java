package com.studioemvs.abbrevsearch;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by vijsu on 10-05-2017.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ResultsViewHolder>{
    private List<Abbrevation> abbrevationList;
    private Context context;


    public RecyclerViewAdapter(List<Abbrevation> abbrevationList, Context context) {
        this.abbrevationList = abbrevationList;
        this.context = context;

    }

    @Override
    public ResultsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dummy_layout,parent,false);
        final ResultsViewHolder resultsViewHolder = new ResultsViewHolder(view);
        return resultsViewHolder;
    }

    @Override
    public void onBindViewHolder(ResultsViewHolder holder, int position) {
        Abbrevation abbrevation = abbrevationList.get(position);
        holder.term.setText(abbrevation.getTerm());
        holder.definition.setText(abbrevation.getDefinition());
    }

    @Override
    public int getItemCount() {
        return abbrevationList.size();
    }

    public class ResultsViewHolder extends RecyclerView.ViewHolder{
        TextView term,definition;
        public ResultsViewHolder(View itemView) {
            super(itemView);
            term = (TextView)itemView.findViewById(R.id.term);
            definition = (TextView)itemView.findViewById(R.id.definition);
        }
    }
}
