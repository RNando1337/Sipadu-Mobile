package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class LaporAdapter extends RecyclerView.Adapter<LaporAdapter.LaporAdpterViewHolder> {

    public interface ItemClickListener{
        void onClick(View v, int position);
    }

    private ItemClickListener clickListener;

    private ArrayList<DataLaporan> dataList;
    private Context context;
    private Activity activity;

    public LaporAdapter(ArrayList<DataLaporan> dataList,Activity activity, Context context) {
        this.dataList = dataList;
        this.activity = activity;
        this.context = context;
    }

    @Override
    public LaporAdpterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_list, parent, false);
        return new LaporAdpterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LaporAdpterViewHolder holder, int position) {
        holder.KAT_ADUAN.setText(dataList.get(position).getKat_aduan());
        if(dataList.get(position).getDesk().length() >= 50) {
            holder.Desk.setText(dataList.get(position).getDesk().substring(0,50)+"...");
        }else{
            holder.Desk.setText(dataList.get(position).getDesk());
        }
        holder.Tanggal.setText(Html.fromHtml("&#xf073;")+" "+dataList.get(position).getTanggal());
        if(dataList.get(position).getStatus().equalsIgnoreCase("Diterima")){
            holder.status.setText(Html.fromHtml("&#xf02e;")+" "+Html.fromHtml("<font color='#5cb85c'>"
                    +dataList.get(position).getStatus()+"</font>"));
            holder.delete.setVisibility(View.GONE);
//            holder.cog.setVisibility(View.GONE);
        }else if(dataList.get(position).getStatus().equalsIgnoreCase("Ditindaklanjuti")){
            holder.status.setText(Html.fromHtml("&#xf02e;")+" "+Html.fromHtml("<font color='#f0ad4e'>"
                    +dataList.get(position).getStatus()+"</font>"));
            holder.delete.setVisibility(View.GONE);
        }
        else if(dataList.get(position).getStatus().equalsIgnoreCase("Diproses")){
            holder.status.setText(Html.fromHtml("&#xf02e;")+" "+dataList.get(position).getStatus());
        }
        else if(dataList.get(position).getStatus().equalsIgnoreCase("Ditolak")){
            holder.status.setText(Html.fromHtml("&#xf02e;")+" "+Html.fromHtml("<font color='#FF5858'>"
                    +dataList.get(position).getStatus()+"</font>"));
           holder.delete.setVisibility(View.GONE);
//           holder.cog.setVisibility(View.GONE);
        }

        Picasso.with(context)
                .load("http://192.168.43.175/ci/sitaro_crud/"+dataList.get(position).getGambar())
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .fit()
                .into(holder.Gambar);
    }

    @Override
    public int getItemCount() {
        return (dataList != null) ? dataList.size() : 0;
    }

    public void setClickListener(ItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public class LaporAdpterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView KAT_ADUAN;
        private ImageView Gambar;
        private TextView Desk;
        private TextView status;
        private TextView Tanggal;
        private Button cog,delete;

        public LaporAdpterViewHolder(@NonNull View itemView) {
            super(itemView);

            KAT_ADUAN = itemView.findViewById(R.id.kat_aduan);
            Desk = itemView.findViewById(R.id.desk);
            Gambar = itemView.findViewById(R.id.image);
            cog = itemView.findViewById(R.id.cog);
            delete = itemView.findViewById(R.id.hapus);
            Tanggal  = itemView.findViewById(R.id.tanggal);
            status = itemView.findViewById(R.id.status);
            cog.setOnClickListener(this);
            delete.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(clickListener != null) clickListener.onClick(v, getAdapterPosition());
        }
    }
}
