package com.projects.bankpro.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.projects.bankpro.R;

public class TransactionHistoryFragment extends Fragment {

    public TransactionHistoryFragment() {
        // Required empty public constructor
    }

    public static TransactionHistoryFragment newInstance() {
        return new TransactionHistoryFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_transaction_history, container, false);
    }
}
