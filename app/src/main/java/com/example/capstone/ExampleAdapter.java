package com.example.capstone;

import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ExampleAdapter extends RecyclerView.Adapter<ExampleAdapter.ExampleViewHolder> {

    private ArrayList<ContactModel> mExampleList;
    private OnContactListener mOnContactListener;


    public static class ExampleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView mTextViewLine1;
        OnContactListener onContactListener;

        public ExampleViewHolder(View itemView, OnContactListener onContactListener) {
            super(itemView);
            mTextViewLine1 = itemView.findViewById(R.id.textview_line1);
            this.onContactListener = onContactListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onContactListener.onContactClick(getAdapterPosition());
        }
    }
    public ExampleAdapter(ArrayList<ContactModel> exampleList, OnContactListener onContactListener) {
        mExampleList = exampleList;
        this.mOnContactListener = onContactListener;
    }
    @Override
    public ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_list, parent, false);
        ExampleViewHolder evh = new ExampleViewHolder(v, mOnContactListener);
        return evh;
    }
    @Override
    public void onBindViewHolder(ExampleViewHolder holder, int position) {
        ContactModel currentItem = mExampleList.get(position);
        holder.mTextViewLine1.setText(currentItem.getLine1());
    }
    @Override
    public int getItemCount() {
        return mExampleList.size();
    }

    public interface OnContactListener {
        void onContactClick(int position);
    }

}