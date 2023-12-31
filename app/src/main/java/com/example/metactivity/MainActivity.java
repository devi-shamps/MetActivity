package com.example.metactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.renderscript.Sampler;
import android.view.View;
import android.widget.Toast;

import com.example.metactivity.databinding.ActivityMainBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private String ChoixVille;
    private ForecastData forecastData;
    private String text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        binding.buttonLoupe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChoixVille = binding.editTextChoixVille.getText().toString();
                binding.textViewAlerte.setText("");
                binding.textViewTemp.setText("");

                OpenWeatherServices service = RetrofitClientInstance.getRetrofitInstance().create(OpenWeatherServices.class);
                Call<ForecastData> call = service.getForcast(ChoixVille, "e075a0b59517e88cc46940bb262add13", "metric");
                call.enqueue(new Callback<ForecastData>() {
                    @Override
                    public void onResponse(Call<ForecastData> call, Response<ForecastData> response) {
                       if (response.isSuccessful()) {
                           forecastData = response.body();
                           if (forecastData != null && forecastData.getForecasts() != null && forecastData.getForecasts().size() > 0) {
                               String temp = String.format("%.2f", forecastData.getForecasts().get(0).getMain().getTemperature());
                               binding.textViewTemp.setText("La température extérieur sera de : " + temp);
                               binding.textView.setText(ChoixVille);
                               double tempT = forecastData.getForecasts().get(0).getMain().getTemperature();
                               if (tempT > 30) {
                                   text = "1 - Eviter d'aller courire";
                               } else if (tempT > 20 && tempT < 30) {
                                   text = "1 - Aller courire";
                               } else if (tempT > 0 && tempT < 15) {
                                   text = "1- Petit cinéma";
                               } else if (tempT < -1 && tempT > -6) {
                                   text = "Aller en montagne pour skier ";
                               } else if (tempT < -6) {
                                   text = "Aller faire du patins à glace sur un lac gelée entourer d'ourse polaire";
                               }
                               binding.textViewActivite.setText(text);
                           }
                           else {
                               Toast.makeText(MainActivity.this, "Aucune donnée météo disponible pour cette ville", Toast.LENGTH_SHORT).show();
                           }
                       }
                       else {
                           binding.textViewAlerte.setText("Erreur.. La ville n'as pas été trouvée !");
                           binding.textView.setText("Choisi ta ville");
                       }
                    }

                    @Override
                    public void onFailure(Call<ForecastData> call, Throwable t) {
                        binding.textViewAlerte.setText("Erreur.. La ville n'as pas était trouvée !");
                    }
                });
            }
        });

    }
}