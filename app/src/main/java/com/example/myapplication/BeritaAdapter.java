package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BeritaAdapter extends RecyclerView.Adapter<BeritaAdapter.BeritaViewHolder> {

    public interface ItemClickListener{
        void onClick(View v, int position);
    }

    private ItemClickListener clickListener;

    ArrayList<ModelBerita> modelBeritas;
    Context context;
    private Activity activity;


    public BeritaAdapter(ArrayList<ModelBerita> modelBeritas, Activity activity,Context context){
        this.modelBeritas = modelBeritas;
        this.activity = activity;
        this.context = context;
    }

    @Override
    public BeritaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_berita, parent, false);
        return new BeritaAdapter.BeritaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BeritaViewHolder holder, int position) {
        holder.jdlbrt.setText(modelBeritas.get(position).getJdl_brt());
        holder.tanggal.setText(Html.fromHtml("&#xf073;")+" "+modelBeritas.get(position).getTimeupload());
        if(modelBeritas.get(position).getInformasi().length() >= 30) {
            holder.desk.setText(modelBeritas.get(position).getInformasi().substring(0,40)+"...");
        }else{
            holder.desk.setText(modelBeritas.get(position).getInformasi());
        }
        Picasso.with(activity)
                .load("http://192.168.43.175/ci/sitaro_crud/"+modelBeritas.get(position).getGambar())
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .fit()
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return modelBeritas.size();
    }

    public void setClickListener(ItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public class BeritaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;
        TextView jdlbrt,tanggal,desk;
        CardView view;

        public BeritaViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView.findViewById(R.id.view);
            imageView = itemView.findViewById(R.id.image);
            jdlbrt = itemView.findViewById(R.id.jdlBrt);
            tanggal = itemView.findViewById(R.id.tgl);
            desk = itemView.findViewById(R.id.textDescription);
            view.setOnClickListener(this);
            imageView.setOnClickListener(this);
            jdlbrt.setOnClickListener(this);
            tanggal.setOnClickListener(this);
            desk.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(clickListener != null) clickListener.onClick(v, getAdapterPosition());
        }
    }
}
