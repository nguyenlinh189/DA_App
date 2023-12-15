package com.example.woody.ui.glossary;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.example.woody.R;
import com.example.woody.model.Glossary;
import com.google.gson.Gson;


public class DetailGlossary extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_glossary_detail, container, false);

        Bundle bundle = this.getArguments();
        TextView vietnamese = view.findViewById(R.id.vietnamese);
        TextView english = view.findViewById(R.id.english);
        TextView description = view.findViewById(R.id.description);
        ImageView image=view.findViewById(R.id.image);
        ImageView backBtn = view.findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            //    Navigation.findNavController(view).popBackStack(R.id.navigation_search, true);
                Navigation.findNavController(view).navigate(R.id.navigation_search);
            }
        });
        if (bundle != null) {
            Gson gson = new Gson();
            Glossary glossaryDetail = gson.fromJson(bundle.getString("glossary"), Glossary.class);
            vietnamese.setText(glossaryDetail.getVietnamese());
            english.setText(glossaryDetail.getEnglish());
            description.setText(glossaryDetail.getDefinition());
            if(glossaryDetail.getImage()!=null){
                Glide.with(getContext()).load(glossaryDetail.getImage()).into(image);
            }
        }
        return view;
    }
}