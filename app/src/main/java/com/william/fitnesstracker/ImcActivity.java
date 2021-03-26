package com.william.fitnesstracker;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ImcActivity extends AppCompatActivity {

    private EditText et_height;
    private EditText et_weight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imc);

        et_height = findViewById(R.id.et_height);
        et_weight = findViewById(R.id.et_weight);

        Button btn_calc = findViewById(R.id.btn_calc);

        btn_calc.setOnClickListener(view -> {
            if (!validate()) {
                Toast.makeText(this, R.string.fields_invalidate, Toast.LENGTH_LONG).show();
                return;
            }

            String sHeight = et_height.getText().toString();
            String sWeight = et_weight.getText().toString();

            int height = Integer.parseInt(sHeight);
            int weight = Integer.parseInt(sWeight);

            double result = calculateImc(height, weight);

            int imcResponseID = imcResponse(result);

            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle(getString(R.string.imc_response, result))
                    .setMessage(imcResponseID)
                    .setPositiveButton(android.R.string.ok, (dialogInterface, i) -> {
                    })
                    .setNegativeButton(R.string.save, (dialogInterface, i) -> {
                        new Thread(() -> {
                            long calcId = SqlHelper.getInstance(ImcActivity.this).addItem("imc", result);
                            runOnUiThread(() -> {
                                if (calcId > 0) {
                                    Toast.makeText(ImcActivity.this, R.string.saved, Toast.LENGTH_LONG).show();
                                    openListCalcActivity();
                                }
                            });
                        }).start();

                    })
                    .create();

            dialog.show();

            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(et_height.getWindowToken(), 0);
            inputMethodManager.hideSoftInputFromWindow(et_weight.getWindowToken(), 0);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_list:
                openListCalcActivity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void openListCalcActivity(){
        Intent intent = new Intent(ImcActivity.this, ListCalcActivity.class);
        intent.putExtra("type", "imc");
        startActivity(intent);
    }

    @StringRes
    private int imcResponse(double imc) {
        if (imc < 15)
            return R.string.imc_severely_low_weight;
        else if (imc < 16)
            return R.string.imc_very_low_weight;
        else if (imc < 18.5)
            return R.string.imc_low_weight;
        else if (imc < 25)
            return R.string.normal;
        else if (imc < 30)
            return R.string.imc_high_weight;
        else if (imc < 35)
            return R.string.imc_so_high_weight;
        else if (imc < 40)
            return R.string.imc_severely_high_weight;
        else
            return R.string.imc_extreme_weight;
    }

    private double calculateImc(int height, int weight) {
        double dHeight = (double) height / 100;
        return weight / (dHeight * dHeight);
    }

    private boolean validate() {
        if (!et_weight.getText().toString().startsWith("0")
                && !et_height.getText().toString().startsWith("0")
                && !et_weight.getText().toString().isEmpty()
                && !et_height.getText().toString().isEmpty()) {
            return true;
        }

        return false;
    }
}