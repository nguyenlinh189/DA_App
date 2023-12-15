package com.example.woody.ui.library_wood;

import android.content.Intent;
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.woody.IdentifyActivity;
import com.example.woody.R;
import com.example.woody.RegisterServiceActivity;
import com.example.woody.SearchWoodByQRCode;
import com.example.woody.api.APIUtils;
import com.example.woody.api.TokenManager;
import com.example.woody.model.AppendixCITES;
import com.example.woody.model.CategoryWood;
import com.example.woody.model.GeographicalArea;
import com.example.woody.model.PlantFamily;
import com.example.woody.model.Preservation;
import com.example.woody.model.SaveAccount;
import com.example.woody.model.UsedService;
import com.example.woody.model.User;
import com.example.woody.model.Wood;
import com.example.woody.model.WoodPagination;

import org.checkerframework.checker.units.qual.A;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LibraryWoodFragment extends Fragment {
    private RecyclerView recyclerView;
    private Spinner spinner;
    private SearchView searchView;
    private NestedScrollView nestedScrollView;
    private ImageButton btnFilter;
    private TextView countWood;
    private TokenManager tokenManager;
    private User user;

    private Spinner spinnerFamily, spinnerLocation, spinnerColor, spinnerCites, spinnerIucn;
    private List<PlantFamily>listFamily=new ArrayList<>();
    private List<GeographicalArea>listArea=new ArrayList<>();
    private List<AppendixCITES>listCites=new ArrayList<>();
    private List<Preservation>listPreservation=new ArrayList<>();
    private List<CategoryWood>lisCategory=new ArrayList<>();

    private ArrayAdapter<CategoryWood> adapterCategory;
    private ArrayAdapter<PlantFamily> adapterFamily;
    private ArrayAdapter<GeographicalArea> adapterArea;
    private ArrayAdapter<AppendixCITES> adapterCites;
    private ArrayAdapter<String> adapterColorVN;
    private ArrayAdapter<String> adapterColorEnglish;
    private ArrayAdapter<Preservation> adapterPresercation;
    private String arrColorVietNam[]={"Chọn một lựa chọn","Đen", "Xanh", "Đỏ", "Hồng", "Vàng"};
    private String arrColorEnglish[]={"Chọn một lựa chọn","brown","red","yellow","white or grey","black","purple","green"};
    private String sfamily=null, slocation=null, scolor=null, scites=null, siucn=null;
    private String key="";
    private LinearLayout layout_preservation;
    private LinearLayout layout_filter;

    private Button btnApDung, btnReset, btnNhanDang, btnQR;


    private List<Wood> woods=new ArrayList<>();
    private WoodAdapter woodAdapter;
    private int category=1;
    private int pageCur=1;
    private int totalPage=1;
    private boolean isLoading=false;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_library_wood, container, false);

        initView(view);
        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(layout_filter.getVisibility()==View.VISIBLE)
                    layout_filter.setVisibility(View.GONE);
                else
                {
                    layout_filter.setVisibility(View.VISIBLE);
                }
            }
        });

       // Toast.makeText(getContext(), "thong tin email trong library fragment wood:"+SaveAccount.email,Toast.LENGTH_LONG).show();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                int categoryCur=lisCategory.get(spinner.getSelectedItemPosition()).getId();
                if(categoryCur!=category){
                    category=categoryCur;
                    setSpinnerFamily();
                    setSpinnerLocation();
                    reset();
                    if(category==1)
                    {
                        spinnerColor.setAdapter(adapterColorVN);
                        layout_preservation.setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        spinnerColor.setAdapter(adapterColorEnglish);
                        layout_preservation.setVisibility(View.GONE);
                    }
                    callAPIGetWoodWithPagination(key,null, null,null, null, null);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        if(woods!=null) {
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    key=s;
                    spinnerFamily.setSelection(0);
                    spinnerLocation.setSelection(0);
                    spinnerColor.setSelection(0);
                    spinnerCites.setSelection(0);
                    spinnerIucn.setSelection(0);
                    siucn=null; scolor=null; slocation=null; sfamily=null; scites=null;
                    layout_filter.setVisibility(View.GONE);
                    reset();
                    callAPIGetWoodWithPagination(key,null, null,null, null, null);
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    return false;
                }
            });
            nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
                @Override
                public void onScrollChange(@NonNull NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    if(scrollY==(v.getChildAt(0).getMeasuredHeight()-v.getMeasuredHeight())){
                        if(pageCur<=totalPage){
                            callAPIGetWoodWithPagination(key,sfamily, slocation, scolor, scites, siucn);
                        }
                    }
                }
            });

        }

        btnApDung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int family= spinnerFamily.getSelectedItemPosition();
                int location=spinnerLocation.getSelectedItemPosition();
                int color=spinnerColor.getSelectedItemPosition();
                int cites=spinnerCites.getSelectedItemPosition();
                int iucn=spinnerIucn.getSelectedItemPosition();
                sfamily=null; slocation=null; scolor=null;scites=null; siucn=null;
                if (cites!=0){
                    scites=listCites.get(color).getName();
                }
                reset();
                if (family!=0)
                    sfamily=listFamily.get(family).getEnglish();
                if (location!=0)
                    slocation=listArea.get(location).getEnglish();
                if (category==1){
                    if (color!=0)
                        scolor=arrColorVietNam[color];
                    if(iucn!=0)
                        siucn=listPreservation.get(iucn).getAcronym();
                }else{
                    if (color!=0)
                        scolor=arrColorEnglish[color];
                }
                callAPIGetWoodWithPagination(key, sfamily, slocation, scolor, scites, siucn);
            }
        });
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reset();
                spinnerFamily.setSelection(0);
                spinnerLocation.setSelection(0);
                spinnerColor.setSelection(0);
                spinnerCites.setSelection(0);
                spinnerIucn.setSelection(0);
                siucn=null; scolor=null; slocation=null; sfamily=null; scites=null;
                callAPIGetWoodWithPagination(key,null, null, null, null, null);
            }
        });
        btnNhanDang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // kiem tra xem da dang ki dich vu nao chua
                APIUtils.getApiServiceInterface().getUsedServiceById(SaveAccount.id).enqueue(new Callback<UsedService>() {
                    @Override
                    public void onResponse(Call<UsedService> call, Response<UsedService> response) {
                    //    Toast.makeText(getContext(),"covaoday",Toast.LENGTH_SHORT).show();
                        if(response.body()!=null && response.isSuccessful()) {
                            Intent intent=new Intent(getContext(), IdentifyActivity.class);
                            intent.putExtra("user",user);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onFailure(Call<UsedService> call, Throwable t) {
                        Intent intent=new Intent(getContext(), RegisterServiceActivity.class);
                        intent.putExtra("user",user);
                        startActivity(intent);
                    }
                });
            }
        });
        btnQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(), SearchWoodByQRCode.class);
                startActivity(intent);
            }
        });
        return view;
    }
    private void initView(View view){
        countWood=view.findViewById(R.id.countWood);
        recyclerView = view.findViewById(R.id.recyclerView);
        searchView = view.findViewById(R.id.search_bar);
        spinner=view.findViewById(R.id.spinner);
        nestedScrollView=view.findViewById(R.id.nestedScrollView);
        btnFilter=view.findViewById(R.id.btnfilter);
        btnApDung=view.findViewById(R.id.btnApdung);
        btnReset=view.findViewById(R.id.btnReset);
        btnNhanDang=view.findViewById(R.id.btn_nhandanghinhanh);
        btnQR=view.findViewById(R.id.btn_searchByQRCode);
        tokenManager=new TokenManager(getContext());

        layout_filter=view.findViewById(R.id.layout_filter);
        layout_preservation=view.findViewById(R.id.layout_iucn);

        user=new User();
        user.setId(SaveAccount.id);
        user.setEmail(SaveAccount.email);
        user.setAddress(SaveAccount.address);
        user.setName(SaveAccount.name);
        user.setProvider(SaveAccount.provider);
        user.setAddress(SaveAccount.address);
        user.setCreateAt(SaveAccount.createAt);
        user.setCreateUpdate(SaveAccount.createUpdate);
        user.setListFavouriteWood(SaveAccount.listFavouriteWood);
        user.setListIdentify(SaveAccount.listIdentify);
        user.setRole(SaveAccount.role);
        user.setEnabled(SaveAccount.enabled);
        user.setPassword(SaveAccount.password);
        woodAdapter=new WoodAdapter(getContext(), woods, user);
        recyclerView.setAdapter(woodAdapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this.getContext(),2,GridLayoutManager.VERTICAL,false);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, 30, false));
        recyclerView.setLayoutManager(gridLayoutManager);

        spinnerColor=view.findViewById(R.id.spinner_color);
        spinnerCites=view.findViewById(R.id.spinner_cites);
        spinnerFamily=view.findViewById(R.id.spinner_family);
        spinnerIucn=view.findViewById(R.id.spinner_iucn);
        spinnerLocation=view.findViewById(R.id.spinner_location);

        APIUtils.getApiServiceInterface().getCategoryWood().enqueue(new Callback<List<CategoryWood>>() {
            @Override
            public void onResponse(Call<List<CategoryWood>> call, Response<List<CategoryWood>> response) {
                if(response.isSuccessful() && response.body()!=null){
                    lisCategory=new ArrayList<>();
                    lisCategory.addAll(response.body());
                    adapterCategory=new ArrayAdapter<>(getContext(), R.layout.item_spinner, lisCategory);
                    spinner.setAdapter(adapterCategory);
                }
            }

            @Override
            public void onFailure(Call<List<CategoryWood>> call, Throwable t) {

            }
        });
        setSpinnerFamily();
        setSpinnerLocation();
        APIUtils.getApiServiceInterface().getListCites().enqueue(new Callback<List<AppendixCITES>>() {
            @Override
            public void onResponse(Call<List<AppendixCITES>> call, Response<List<AppendixCITES>> response) {
                if(response.isSuccessful() && response.body()!=null){
                    listCites.add(new AppendixCITES("Chọn một lựa chọn"));
                    listCites.addAll(response.body());
                    adapterCites=new ArrayAdapter<>(getContext(), R.layout.item_spinner, listCites);
                    spinnerCites.setAdapter(adapterCites);
                }
            }
            @Override
            public void onFailure(Call<List<AppendixCITES>> call, Throwable t) {
                Toast.makeText(getContext(), "goi api loi", Toast.LENGTH_SHORT).show();
            }
        });

        APIUtils.getApiServiceInterface().getListPreservation().enqueue(new Callback<List<Preservation>>() {
            @Override
            public void onResponse(Call<List<Preservation>> call, Response<List<Preservation>> response) {
                if(response.isSuccessful() && response.body()!=null){
                    listPreservation.add(new Preservation("Chọn một lựa chọn", null));
                    listPreservation.addAll(response.body());
                    adapterPresercation=new ArrayAdapter<>(getContext(), R.layout.item_spinner, listPreservation);
                    spinnerIucn.setAdapter(adapterPresercation);
                }
            }
            @Override
            public void onFailure(Call<List<Preservation>> call, Throwable t) {

            }
        });
        adapterColorVN=new ArrayAdapter<>(getContext(), R.layout.item_spinner,arrColorVietNam);
        adapterColorEnglish=new ArrayAdapter<>(getContext(), R.layout.item_spinner,arrColorEnglish);
        spinnerColor.setAdapter(adapterColorVN);
        woodAdapter.setWoodList(SaveAccount.listFavouriteWood);
//        Toast.makeText(getContext(),"so luong go yeu thich"+SaveAccount.listFavouriteWood.size(),Toast.LENGTH_LONG).show();
        callAPIGetWoodWithPagination(key,null, null, null,null, null);
    }
    private void callAPIGetWoodWithPagination(String key, String sfamily, String sarea, String scolor, String scites, String spreservation) {
        if (!isLoading) {
            woodAdapter.addLoadingEffect();
            isLoading = true;
        }
        Toast.makeText(getContext(), ""+pageCur+"/"+totalPage, Toast.LENGTH_SHORT).show();
        APIUtils.getApiServiceInterface().getListWood(category, pageCur, key, sfamily, sarea, scolor, scites, spreservation).enqueue(new Callback<WoodPagination>() {
            @Override
            public void onResponse(Call<WoodPagination> call, Response<WoodPagination> response) {
                if (isLoading) {
                    woodAdapter.removeLoadingEffect();
                    isLoading = false;
                }
                if (response.isSuccessful() && response.body() != null) {
                    WoodPagination pagination = response.body();
                    woods.addAll(pagination.getContent());
                    woodAdapter.setList(woods);
                    totalPage = pagination.getTotalPages();
                    pageCur++;
                    countWood.setText("Số lượng gỗ: " + pagination.getTotalElements());
                    //Toast.makeText(getContext(), "Tong so:" + pagination.getTotalElements(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<WoodPagination> call, Throwable t) {
                Toast.makeText(getContext(), "goi khong thanh cong", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void setSpinnerFamily(){
        APIUtils.getApiServiceInterface().getListFamilyByCategory(category).enqueue(new Callback<List<PlantFamily>>() {
            @Override
            public void onResponse(Call<List<PlantFamily>> call, Response<List<PlantFamily>> response) {
                if(response.isSuccessful() && response.body()!=null)
                {
                    listFamily=new ArrayList<>();
                    listFamily.add(new PlantFamily("Chọn một tùy chọn"));
                    listFamily.addAll(response.body());
                    adapterFamily=new ArrayAdapter<>(getContext(), R.layout.item_spinner,listFamily);
                    spinnerFamily.setAdapter(adapterFamily);
                }
            }
            @Override
            public void onFailure(Call<List<PlantFamily>> call, Throwable t) {
            }
        });
    }

    private void setSpinnerLocation(){
        APIUtils.getApiServiceInterface().getListAreaByCategoryWood(category).enqueue(new Callback<List<GeographicalArea>>() {
            @Override
            public void onResponse(Call<List<GeographicalArea>> call, Response<List<GeographicalArea>> response) {
                if(response.isSuccessful() && response.body()!=null){
                    listArea=new ArrayList<>();
                    listArea.add(new GeographicalArea("Chọn một lựa chọn"));
                    listArea.addAll(response.body());
                    adapterArea=new ArrayAdapter<>(getContext(), R.layout.item_spinner,listArea);
                    spinnerLocation.setAdapter(adapterArea);
                }
            }
            @Override
            public void onFailure(Call<List<GeographicalArea>> call, Throwable t) {
            }
        });
    }
    private void reset(){
        pageCur=1;
        totalPage=1;
        woods=new ArrayList<>();
        isLoading=false;
    }
}