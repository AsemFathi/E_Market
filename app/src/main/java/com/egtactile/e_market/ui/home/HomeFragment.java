package com.egtactile.e_market.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.egtactile.e_market.MyAdapter;
import com.egtactile.e_market.RecyclerViewInterface;
import com.egtactile.e_market.databinding.FragmentHomeBinding;
import com.egtactile.e_market.items;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements RecyclerViewInterface {

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        RecyclerView recyclerView = binding.recyclerView;

       /* Bundle bundle = this.getArguments();
        ArrayList<items> itemsArrayList =(ArrayList<items>) bundle.getSerializable("items");
        List<items> itemsList = new ArrayList<>();
        itemsList.addAll(itemsArrayList);
        recyclerView.setAdapter(new MyAdapter(getContext() ,itemsList , HomeFragment.this ));*/
        //final TextView textView = binding.textHome;
        //homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onItemClick(int pos) {

    }
}