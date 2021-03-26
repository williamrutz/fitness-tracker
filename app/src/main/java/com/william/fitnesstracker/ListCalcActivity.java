package com.william.fitnesstracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ListCalcActivity extends AppCompatActivity {

    private RecyclerView rv_list_calc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_calc);

        Bundle bundle = getIntent().getExtras();

        rv_list_calc = findViewById(R.id.rl_list);
        rv_list_calc.setLayoutManager(new LinearLayoutManager(ListCalcActivity.this));


        if(bundle != null){
            String type = bundle.getString("type");
            List<Register> registerList = SqlHelper.getInstance(this).getRegisterBy(type);

            ListCalcAdapter adapter = new ListCalcAdapter(registerList);
            rv_list_calc.setAdapter(adapter);
        }

    }

    private class ListCalcAdapter extends RecyclerView.Adapter<ListCalcAdapter.ListCalcViewHolder>{

        private List<Register> registerList;

        public ListCalcAdapter (List<Register> registers){
            this.registerList = registers;
        }

        @NonNull
        @Override
        public ListCalcAdapter.ListCalcViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ListCalcViewHolder(getLayoutInflater().inflate(android.R.layout.simple_list_item_1, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ListCalcAdapter.ListCalcViewHolder holder, int position) {
            Register data = registerList.get(position);
            holder.bind(data);
        }

        @Override
        public int getItemCount() {
            return registerList.size();
        }

        public class ListCalcViewHolder extends RecyclerView.ViewHolder {
            public ListCalcViewHolder(@NonNull View itemView) {
                super(itemView);
            }

            public void bind(Register data){
                String date = "";
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", new Locale("pt", "BR"));
                    Date dateSaved = sdf.parse(data.created_at);
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", new Locale("pt", "BR"));
                    date = dateFormat.format(dateSaved);
                }catch (Exception e){

                }
                ((TextView) itemView).setText(
                        getString(R.string.list_response, data.response, date));
            }
        }
    }
}