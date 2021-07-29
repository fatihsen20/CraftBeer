package com.example.food.activities.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.food.R;
import com.example.food.adapters.BeerAdapter;
import com.example.food.models.Beer;

import java.util.ArrayList;


public class HomeFragment extends Fragment {
    private ListView listView;
    private ArrayList<Beer> beers = new ArrayList<>();
    private BeerAdapter beerAdapter;

    private HomeViewModel homeViewModel;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        listView = root.findViewById(R.id.fragment_home_listview);
        beers.add(new Beer("Efes Malt", "Efes Pilsen, Türkiye'de üretilen bir bira markası. Efes Pilsen 1969 yılında İstanbul ve İzmir'de üretilmeye başlamış ve kısa sürede dünyaya açılmıştır.\n" +
                "\n" +
                "50'nin üzerinde ülkeye ihracat gerçekleştiren ve Türkiye'nin yanı sıra Rusya, Moldova, Sırbistan, Romanya ve Kazakistan'da yatırımları olan Efes'in dünyada toplam 17 bira, 4 malt fabrikası ve 1 adet şerbetçiotu işleme tesisi bulunmaktadır. ",R.drawable.efesmalt));
        beers.add(new Beer("Tuborg Gold", "Türk Tuborg Bira ve Malt Sanayii A.Ş., 1967 yılında İzmir-Pınarbaşı'nda kurulan şirkettir.\n" +
                "\n" +
                "1969 yılında üretime geçen Türk Tuborg, Türkiye'de özel sektörün ilk birasını Tuborg markasıyla piyasaya sundu. Dünyanın en büyük 5 bira şirketi arasında yer alan 140'ı aşkın ülkede faaliyet gösteren, 40'tan fazla ülkede üretim yapan bir biracılık kuruluşu olan Carlsberg Breweries ve Türk Tuborg’un güçlü iş birliği bulunmaktadır. ",R.drawable.tuborggold));
        beers.add(new Beer("Bomonti IPA", "Bomonti Bira Fabrikası, adını İstanbul'un en eski semtlerinden birine vermiş olup Osmanlı İmparatorluğu'nda modern bira üretim tekniği ile imalata başlamış olan ilk bira üretim tesisidir. Sahibi, Efes Pilsen'dir.\n" +
                "\n" +
                "1839 senesi sonrasında Batı'ya açılma sürecinde, bira Osmanlı'ya üretim safhasında dahil olmuştur. Biranın 1840'ların öncesinde tüketildiği ve üretildiği söylenebilir. 1840'lı yıllarda ise, birahaneler açılmaya başladı. Osmanlı'da bira üretimi 1896 yılında 12.000 hl idi (1.2 milyon litre). Bu oran hızla artmıştır ve 1913-1914 yılları arası 9.9 milyon litreye ulaşmıştır. Türkiye Cumhuriyeti'nde bu rakama ancak 1940'lı yıllarda ulaşılmıştır. ",R.drawable.bomontipa));
        beers.add(new Beer("Heineken", "Heineken International, 1864 yılında Gerard Adriaan Heineken tarafından Amsterdam'da kurulan Hollandalı bira şirketidir. 2007 bilgilerine göre 65 ülkede[2] 54,004 [2] çalışana 119'u aşkın bira fabrikasına sahiptir . Heineken International, Cruzcampo, Tiger Beer, Żywiec, Starobrno, Zagorka, Birra Moretti, Ochota, Murphy’s, Star ve tabii ki Heineken Pilsener başta olmak üzere 170 yerel, bölgesel ve uluslararası şirkete sahiptir. ",R.drawable.heineken));

        beerAdapter = new BeerAdapter(getActivity(), beers);
        listView.setAdapter(beerAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), beers.get(position).getName(), Toast.LENGTH_SHORT).show();
            }
        });
        return root;
    }
}