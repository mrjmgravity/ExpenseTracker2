package com.example.expensetracker;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ExpenseAdapter adapter;
    private List<Expense> expenseList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializácia RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Inicializácia zoznamu výdavkov
        expenseList = new ArrayList<>();
        adapter = new ExpenseAdapter(expenseList);
        recyclerView.setAdapter(adapter);

        // Tlačidlo "Pridať výdavok"
        findViewById(R.id.fab).setOnClickListener(v -> showAddExpenseDialog());
    }

    private void showAddExpenseDialog() {
        // Vytvorenie dialógového okna
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_expense, null);
        builder.setView(dialogView);

        AlertDialog dialog = builder.create();

        // Odkazy na EditTexty a tlačidlo v dialógovom okne
        EditText etCategory = dialogView.findViewById(R.id.etCategory);
        EditText etAmount = dialogView.findViewById(R.id.etAmount);
        EditText etDate = dialogView.findViewById(R.id.etDate);
        Button btnSave = dialogView.findViewById(R.id.btnSave);

        // Kliknutie na tlačidlo "Uložiť"
        btnSave.setOnClickListener(v -> {
            String category = etCategory.getText().toString();
            String amountText = etAmount.getText().toString();
            String date = etDate.getText().toString();

            if (category.isEmpty() || amountText.isEmpty() || date.isEmpty()) {
                Toast.makeText(MainActivity.this, "Vyplňte všetky polia!", Toast.LENGTH_SHORT).show();
            } else {
                try {
                    double amount = Double.parseDouble(amountText);

                    // Pridanie nového výdavku
                    expenseList.add(new Expense(category, amount, date));
                    adapter.notifyItemInserted(expenseList.size() - 1);

                    Toast.makeText(MainActivity.this, "Výdavok pridaný!", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                } catch (NumberFormatException e) {
                    Toast.makeText(MainActivity.this, "Neplatná suma!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        dialog.show();
    }
}