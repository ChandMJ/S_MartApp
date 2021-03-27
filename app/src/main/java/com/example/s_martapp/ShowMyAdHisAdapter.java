package com.example.s_martapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class ShowMyAdHisAdapter extends RecyclerView.Adapter<ShowMyAdHisAdapter.myViewHolder> {

    @NonNull
    List<MyAdHisShow> showList;
    Context context;
    String num;

    //firebase
    FirebaseAuth firebaseAuth= FirebaseAuth.getInstance();
    FirebaseDatabase database= FirebaseDatabase.getInstance();
    DatabaseReference ref;


    public ShowMyAdHisAdapter(Context context, List<MyAdHisShow> showList, String num) {
        this.showList = showList;
        this.context=context;
        this.num=num;
    }

    @Override
    public ShowMyAdHisAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.myadhistory_item,parent,false);
        ShowMyAdHisAdapter.myViewHolder viewHolder=new ShowMyAdHisAdapter.myViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ShowMyAdHisAdapter.myViewHolder holder, int position){

        final MyAdHisShow show = showList.get(position);

        holder.category.setText(show.getCategory());
        holder.title.setText(show.getTitle());
        holder.price.setText("₹ "+show.getPrice());
        holder.date.setText("Posted On: "+show.getDate());

        holder.next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(context, ParticularAdActivity.class);
                i.putExtra("Phone",num);
                i.putExtra("category",show.getCategory());
                i.putExtra("Sellerphone",num);
                i.putExtra("date",show.getDate());
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
        TextView category,title,price,date;
        ImageView next;

        public myViewHolder(View itemView){

            super(itemView);
            category=itemView.findViewById(R.id.textView13);
            title=itemView.findViewById(R.id.textView15);
            price=itemView.findViewById(R.id.textView16);
            date=itemView.findViewById(R.id.textView18);
            next=itemView.findViewById(R.id.nxt);
        }
    }

}
