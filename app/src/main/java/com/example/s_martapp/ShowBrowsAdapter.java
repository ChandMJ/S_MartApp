package com.example.s_martapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class ShowBrowsAdapter extends RecyclerView.Adapter<ShowBrowsAdapter.myViewHolder> {

    @NonNull
    List<BrowsShow> showList;
    Context context;
    String num,user,category;

    public ShowBrowsAdapter(Context context, List<BrowsShow> showList, String num, String user, String category) {
        this.showList = showList;
        this.context=context;
        this.num=num;
        this.user=user;
        this.category=category;
    }

    @Override
    public ShowBrowsAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.brows_items,parent,false);
        ShowBrowsAdapter.myViewHolder viewHolder=new ShowBrowsAdapter.myViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ShowBrowsAdapter.myViewHolder holder, int position){

        BrowsShow show = showList.get(position);

        holder.title.setText(show.getTitle());
        holder.desc.setText(show.getDesc());
        holder.price.setText(show.getPrice());
        holder.postedby.setText(show.getPostedby());
        holder.postedon.setText(show.getPostedon());

        Glide.with(context).load(show.getUrl()).into(holder.image);

        System.out.println("user:"+user);

        holder.next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(context, ParticularAdActivity.class);
                i.putExtra("Phone",num);
                i.putExtra("category",category);
                i.putExtra("Sellerphone",user);
                i.putExtra("date",show.getPostedon());
                context.startActivity(i);

            }
        });

    }

    @Override
    public int getItemCount() {
        return showList.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder
    {
        TextView title, desc,price,postedby,postedon;
        ImageView image;

        ImageView next;

        public myViewHolder(View itemView){

            super(itemView);
            title=itemView.findViewById(R.id.textView13);
            desc=itemView.findViewById(R.id.textView15);
            price=itemView.findViewById(R.id.textView16);
            postedby=itemView.findViewById(R.id.textView18);
            postedon=itemView.findViewById(R.id.textView19);

            next=itemView.findViewById(R.id.nxt);

            image=itemView.findViewById(R.id.imageView4);
        }
    }

}
