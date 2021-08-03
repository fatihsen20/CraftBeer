package com.example.food.activities.ui.home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.food.R;
import com.example.food.adapters.BeerAdapter;
import com.example.food.models.Beer;

import java.util.ArrayList;


public class HomeFragment extends Fragment {
    private GridView gridView;
    private ArrayList<Beer> beers = new ArrayList<>();
    private BeerAdapter beerAdapter;

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        gridView = root.findViewById(R.id.fragment_home_gridview);

        addBeer();
        beerAdapter = new BeerAdapter(getActivity(), beers);
        gridView.setAdapter(beerAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                alert.setTitle(beers.get(position).getName());
                alert.setMessage(beers.get(position).getDescription());
                alert.setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //
                    }
                });
                alert.show();
                System.out.println(beers.get(position).getDescription());
            }
        });
        return root;
    }

    public void addBeer(){
        beers.add(new Beer("Efes Pilsen","Türkiye’de en çok tercih edilen bira markası Efes Pilsen’dir. Biraların alkol oranı %3 ve %7.5 arasında farklılık gösteriyor. Özellikle son dönemlerde +1 bira serisi ve dinlendirilmiş özel bira serileri ile alkol tüketicileri memnun ediyor. Yerli üretim bira markaları arasında satış hacmi konusunda birinci sırada yer almaktadır. Efes Pilsen biralarının çok acı bir tadı olmamakla birlikte arpa maltı ve şerbetçiotu tadı baskındır.",R.drawable.efespilsen));
        beers.add(new Beer("Carlsberg","1847 yılında Danimarka’da üretime başlayan Carlsberg şirketi, günümüzde yaklaşık yüz elli ülkede satış yapmaktadır. Türkiye’de de hatırı sayılır bir kitleye ulaşmıştır. Carlsberg biraların en önemli özelliği hafif ve yumuşak içimidir. 50 cc’lik şişelerinde %5 oranında alkol bulunur. Diğer pek çok biraların aksine rahatsızlık hissi vermeyen özel bir formüle sahiptir. ‘Muhtemelen dünyanın en iyi birası’ sloganıyla ön plana çıkan Carlsberg, en çok tercih edilen bira markalarından biridir.",R.drawable.carlsberg));
        beers.add(new Beer("Beck's","Beck’s pilser Alman birası olmakla birlikte dünyanın beş farklı ülkesinde üretilir. Aynı formülle üretilen biralarda yoğun bir tat ve keskin bir şerbetçiotu kokusu ön plana çıkıyor. Türkiye’de Beck’s üretimi yapan firma Anadolu Grubu’dur. Yoğun ve acı bir lezzete sahip olan bol köpüklü biralardan hoşlanıyorsanız bu markaya şans verebilirsiniz. İlk defa bira içecek olanlar daha yumuşak bir tada sahip olanları tercih etmeli.",R.drawable.becks));
        beers.add(new Beer("Corona","Corona bira markası ile hayatımızı etkisi altına alan Covid-19 salgınının bir ilişkisi olmadığını söyleyerek başlamalıyım. Marka yaklaşık yüz yıl önce üretime başlamış ve o günden bu yana istikrarlı bir satış grafiğine sahiptir. Ancak Corona salgınıyla birlikte markanın satış hacmi bazı ülkelerde yarı yarıya düştü. Meksika birası Corona kendine has açık sarı rengi ile ön plana çıkıyor. Alkol oranı %4.8 olmakla birlikte içimi son derece kolaydır.",R.drawable.corona));
        beers.add(new Beer("Heineken","Hollanda markası Heineken, 1864 yılından beri piyasanın öncü şirketleri arasında bulunuyor. Heineken, 65 farklı ülkede 30 ayrı içkisiyle satış yapmaktadır. Türkiye’de ise sadece Heineken bira ürünleri satılıyor. Dünyada en fazla bira üretimi yapan ikinci firma olmakla birlikte genelde alkol oranı %5’dir. Yumuşak ve hafif bir içime sahip olan bira arayışındaysanız bu markayı tercih edebilirsiniz. Ayrıca mide rahatsızlığı yapmamakla birlikte asit ve alkol oranı dengelidir.",R.drawable.heineken));
        beers.add(new Beer("Guinness","İrlanda birası Guinness siyah rengi ve kremamsı köpüğü ile en iyi biralar arasında bulunuyor. Dublin’de 1756 yılında üretime başlayan firma uzun süren geliştirmeler sonucunda bugün içtiğimiz biranın formüle ulaşmıştır. Daha önce Guinness bira içtiyseniz içinde küçük bir top görmüş veya sesini duymuş olmalısınız. Bu özel küçük top biranın açıldığı anda köpüklenmesini sağlamaktadır. Diğer biralara oranla köpüğü daha dolgun bir his bırakır. 44 cc’lik kutularda %4.2 alkol oranıyla satılıyor.",R.drawable.guinness));
    }
}