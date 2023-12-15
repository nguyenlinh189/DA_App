package com.example.woody.ui.glossary;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.woody.R;
import com.example.woody.api.APIUtils;
import com.example.woody.model.Glossary;
import com.google.gson.Gson;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GlossaryFragment extends Fragment {
    private List<Glossary> glossaries;
    private ListView listView;
    private SearchView searchView;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_glossary,container,false);
        searchView=view.findViewById(R.id.search_bar);
        listView =view.findViewById(R.id.list_item);
        getDataGlossary("");
        if(glossaries!=null){
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    getDataGlossary(s);
                    return true;
                }
            });
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Glossary glossary= glossaries.get(i);
                    Bundle bundle = new Bundle();
                    Gson gson = new Gson();
                    String woodDetailJson = gson.toJson(glossary);
                    bundle.putString("glossary", woodDetailJson);
                    Navigation.findNavController(view).navigate(R.id.detailWord, bundle);
                }
            });
        }
        return view;
    }

    private void getDataGlossary(String key) {
        glossaries = new ArrayList<>();
        APIUtils.getApiServiceInterface().getGlossary(key).enqueue(new Callback<List<Glossary>>() {
            @Override
            public void onResponse(Call<List<Glossary>> call, Response<List<Glossary>> response) {
                if(response.isSuccessful() && response.body()!=null){
                    glossaries=response.body();
                    ArrayAdapter arrayAdapter= new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1,glossaries);
                    listView.setAdapter(arrayAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<Glossary>> call, Throwable t) {
                Toast.makeText(getContext(),"Goi API khong thanh cong", Toast.LENGTH_SHORT).show();
            }
        });
    }

}