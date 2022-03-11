package com.example.dessertclicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ShareCompat;
import androidx.databinding.DataBindingUtil;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.dessertclicker.databinding.ActivityMainBinding;

import java.util.List;

class Dessert {
    int imageId;
    int price;
    int startProductionAmount;

    Dessert(int imageId, int price, int startProductionAmount) {
        this.imageId = imageId;
        this.price = price;
        this.startProductionAmount = startProductionAmount;
    }
}

public class MainActivity extends AppCompatActivity {
    private int revenue = 0;
    private int dessertsSold = 0;
    private ActivityMainBinding binding;

    private List<Dessert> allDesserts = List.of(
        new Dessert(R.drawable.cupcake, 5, 0),
        new Dessert(R.drawable.donut, 10, 5),
        new Dessert(R.drawable.eclair, 15, 20),
        new Dessert(R.drawable.froyo, 30, 50),
        new Dessert(R.drawable.gingerbread, 50, 100),
        new Dessert(R.drawable.honeycomb, 100, 200),
        new Dessert(R.drawable.icecreamsandwich, 500, 500),
        new Dessert(R.drawable.jellybean, 1000, 1000),
        new Dessert(R.drawable.kitkat, 2000, 2000),
        new Dessert(R.drawable.lollipop, 3000, 4000),
        new Dessert(R.drawable.marshmallow, 4000, 8000),
        new Dessert(R.drawable.nougat, 5000, 16000),
        new Dessert(R.drawable.oreo, 6000, 20000)
    );

    private Dessert currentDessert = allDesserts.get(0);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        binding.dessertButton.setOnClickListener((v) -> {
            onDessertClicked();
        });
        binding.setRevenue(revenue);
        binding.setAmountSold(dessertsSold);

        binding.dessertButton.setImageResource(currentDessert.imageId);
    }

    private void onDessertClicked() {
        revenue += currentDessert.price;
        dessertsSold += 1;

        binding.setRevenue(revenue);
        binding.setAmountSold(dessertsSold);

        showCurrentDessert();
    }

    private void showCurrentDessert() {
        Dessert newDessert = allDesserts.get(0);
        for (Dessert dessert : allDesserts) {
            if (dessertsSold >= dessert.startProductionAmount) {
                newDessert = dessert;
            } else {
                break;
            }
        }

        if (newDessert != currentDessert) {
            currentDessert = newDessert;
            binding.dessertButton.setImageResource(newDessert.imageId);
        }
    }

    private void onShare() {
        Intent shareIntent = ShareCompat.IntentBuilder.from(this)
                .setText(getString(R.string.share_text, dessertsSold, revenue))
                .setType("text/plain")
                .getIntent();

        try {
            startActivity(shareIntent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, getString(R.string.sharing_not_available), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.shareMenuButton: onShare(); break;
        }
        return super.onOptionsItemSelected(item);
    }
}