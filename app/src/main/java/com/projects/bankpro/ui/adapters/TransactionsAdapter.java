package com.projects.bankpro.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.projects.bankpro.R;
import com.projects.bankpro.data.model.Transaction;

import java.util.List;

public class TransactionsAdapter extends RecyclerView.Adapter<TransactionsAdapter.TransactionViewHolder> {

    private List<Transaction> transactionList;
    private Context context;

    public TransactionsAdapter(List<Transaction> transactionList, Context context) {
        this.transactionList = transactionList;
        this.context = context;
    }

    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.transaction_item_layout, parent, false);
        return new TransactionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionViewHolder holder, int position) {
        Transaction transaction = transactionList.get(position);
        holder.bindData(transaction);
    }

    @Override
    public int getItemCount() {
        return transactionList.size();
    }

    static class TransactionViewHolder extends RecyclerView.ViewHolder {

        private TextView transactionIdTextView;
        private TextView dateTextView;
        private TextView amountTextView;
        private TextView accountIdTextView;

        public TransactionViewHolder(@NonNull View itemView) {
            super(itemView);
            transactionIdTextView = itemView.findViewById(R.id.transactionIdTextView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
            amountTextView = itemView.findViewById(R.id.amountTextView);
            accountIdTextView = itemView.findViewById(R.id.accountIdTextView);
        }

        public void bindData(Transaction transaction) {
            transactionIdTextView.setText(String.valueOf(transaction.getTransactionId()));
            dateTextView.setText(transaction.getDate());
            amountTextView.setText(String.valueOf(transaction.getAmount()));
            accountIdTextView.setText(String.valueOf(transaction.getAccountId()));
        }
    }
}
