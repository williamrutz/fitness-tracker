package com.william.fitnesstracker;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rv_main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<MainItem> itemList = new ArrayList<>();
        itemList.add(new MainItem(1, R.drawable.ic_baseline_wb_sunny_24, Color.YELLOW, R.string.label_imc));
        itemList.add(new MainItem(2, R.drawable.ic_baseline_tmb_24, Color.GREEN, R.string.label_tmb));

        rv_main = findViewById(R.id.rv_main);
        rv_main.setLayoutManager(new GridLayoutManager(this, 2));

        MainAdapter adapter = new MainAdapter(itemList);
        adapter.setListner(id -> {
            switch (id){
                case 1:
                    Intent intent = new Intent(MainActivity.this, ImcActivity.class);
                    startActivity(intent);
                    break;
                case 2:
                    Toast.makeText(this, "Aguarde este tópico!", Toast.LENGTH_LONG).show();
                    break;
            }
        });
        rv_main.setAdapter(adapter);


    }

    private class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainViewHolder>{

        private List<MainItem> mainItems;
        private OnItemClickListner listner;

        public MainAdapter(List<MainItem> mainItems){
            this.mainItems = mainItems;
        }

        public void setListner(OnItemClickListner listner) {
            this.listner = listner;
        }

        @NonNull
        @Override
        public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new MainViewHolder(getLayoutInflater().inflate(R.layout.main_item,parent,false));
        }

        @Override
        public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {
            MainItem itemCurrent = mainItems.get(position);
            holder.bind(itemCurrent);
        }

        @Override
        public int getItemCount() {
            return mainItems.size();
        }

        private class MainViewHolder extends RecyclerView.ViewHolder{

            public MainViewHolder(@NonNull View itemView) {
                super(itemView);
            }

            public void bind(MainItem item){
                TextView txt_item_description = itemView.findViewById(R.id.item_txt_name);
                ImageView imageView = itemView.findViewById(R.id.item_img_icon);
                LinearLayout container = (LinearLayout) itemView.findViewById(R.id.btn_imc);

                container.setOnClickListener(view -> listner.onClick(item.getId()));

                txt_item_description.setText(item.getDescription());
                imageView.setImageResource(item.getDrawableId());
                container.setBackgroundColor(item.getColor());
            }
        }
    }




}