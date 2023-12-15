package com.example.woody;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.woody.adapter.ImageWoodAdapter;
import com.example.woody.model.Wood;
import com.github.islamkhsh.CardSliderIndicator;
import com.github.islamkhsh.CardSliderViewPager;
import com.google.android.material.chip.Chip;
import com.google.gson.Gson;

public class DetailWoodActivity extends AppCompatActivity {
    private Chip preservationStatus;
    private TextView displayName,  wood_cites, scienceName, commercialName, family, distribution, color, character, specificGravity;
    private LinearLayout layout_weight;
    private CardSliderIndicator indicator;
    private ImageView backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_wood);
        initView();
        Intent intent=getIntent();
        if (intent != null) {
            Gson gson = new Gson();
            Wood woodDetail = (Wood) intent.getSerializableExtra("wood");
            if (woodDetail.getVietnameName()!=null){
                displayName.setText(woodDetail.getVietnameName());
                family.setText(woodDetail.getFamily().getVietnamese());
                String distri=woodDetail.getListAreas().get(0).getVietnamese();
                for(int i=1;i<woodDetail.getListAreas().size();i++)
                    distri=distri+", "+woodDetail.getListAreas().get(i).getVietnamese();
                distribution.setText(distri);
                character.setText(Html.fromHtml(woodDetail.getCharacteristic()));

            }else{
                displayName.setText(woodDetail.getScientificName());
                family.setText(woodDetail.getFamily().getEnglish());
                String distri=woodDetail.getListAreas().get(0).getEnglish();
                for(int i=1;i<woodDetail.getListAreas().size();i++)
                    distri=distri+", "+woodDetail.getListAreas().get(i).getEnglish();
                distribution.setText(distri);
                character.setText(Html.fromHtml(woodDetail.getCharacteristic(), Html.TO_HTML_PARAGRAPH_LINES_CONSECUTIVE));
            }

            scienceName.setText(woodDetail.getScientificName());
            commercialName.setText(woodDetail.getCommercialName());
            wood_cites.setText(woodDetail.getAppendixCites().getName()+" ("+woodDetail.getAppendixCites().getContent()+")");
            color.setText(woodDetail.getColor());

            CardSliderViewPager cardSliderViewPager=(CardSliderViewPager)findViewById(R.id.viewPager);
            cardSliderViewPager.setAdapter(new ImageWoodAdapter(woodDetail.getListImage()));
            indicator.setIndicatorsToShow(woodDetail.getListImage().size());

            if(woodDetail.getPreservation()!=null)
                setPreservationStatus(woodDetail.getPreservation().getAcronym());
            else
                preservationStatus.setVisibility(View.GONE);


            if(woodDetail.getSpecificGravity()!=0)
            {
                specificGravity.setText(woodDetail.getSpecificGravity()+" kg/m³");
            }
            else
            {
                layout_weight.setVisibility(View.GONE);
            }


            backBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(intent.getStringExtra("fragment")=="Identify"){
                       Intent intent1=new Intent(DetailWoodActivity.this, IdentifyActivity.class);
                       startActivity(intent1);
                    }
                    else{
                        //Navigation.findNavController(view).popBackStack(R.id.navigation_library,false);

                    }

                }
            });

        }
    }
    private void initView(){
        displayName =  findViewById(R.id.display_name);
        wood_cites =  findViewById(R.id.wood_cites);
        preservationStatus =  findViewById(R.id.preservation_status);
        scienceName =  findViewById(R.id.science_name);
        commercialName =  findViewById(R.id.commercial_name);
        family =  findViewById(R.id.family);
        distribution =  findViewById(R.id.distribution);
        specificGravity =  findViewById(R.id.specificGravity);
        color =  findViewById(R.id.color);
        character =  findViewById(R.id.character);
        layout_weight= findViewById(R.id.layout_weight);
        indicator= findViewById(R.id.indicator);
        backBtn =  findViewById(R.id.backBtn);
    }
    private void setPreservationStatus(String preservation) {
        switch (preservation) {
            case "EX":
                preservationStatus.setText("EX-Tuyệt chủng");
                preservationStatus.setTextColor(Color.parseColor("#93000A"));
                preservationStatus.setChipBackgroundColorResource(R.color.black);
                break;
            case "EW":
                preservationStatus.setText("EW-Tuyệt chủng trong tự nhiên");
                preservationStatus.setTextColor(Color.parseColor("#FFFBFF"));
                preservationStatus.setChipBackgroundColorResource(R.color.black);
                break;
            case "CR":

                preservationStatus.setText("CR-Cực kì nguy cấp");
                preservationStatus.setTextColor(Color.parseColor("#FFFBFF"));
                preservationStatus.setChipBackgroundColorResource(R.color.danger);
                break;
            case "EN":

                preservationStatus.setText("EN-Nguy cấp");
                preservationStatus.setTextColor(Color.parseColor("#FFFBFF"));
                preservationStatus.setChipBackgroundColorResource(R.color.warning);
                break;
            case "VU":

                preservationStatus.setText("VU-Sắp nguy cấp");
                preservationStatus.setTextColor(Color.parseColor("#FFFBFF"));
                preservationStatus.setChipBackgroundColorResource(R.color.yellow);
                break;
            case "NT":

                preservationStatus.setText("NT-Sắp bị đe dọa");
                preservationStatus.setTextColor(Color.parseColor("#FFFBFF"));
                preservationStatus.setChipBackgroundColorResource(R.color.normal);
                break;
            case "DD":
                preservationStatus.setText("DD-Thiếu dữ liệu");
                preservationStatus.setTextColor(Color.parseColor("#FFFBFF"));
                preservationStatus.setChipBackgroundColorResource(R.color.green);
                break;
            case "LC":
                preservationStatus.setText("LC-Ít quan tâm");
                break;
            default:
                preservationStatus.setVisibility(View.INVISIBLE);
                break;
        }
    }
}