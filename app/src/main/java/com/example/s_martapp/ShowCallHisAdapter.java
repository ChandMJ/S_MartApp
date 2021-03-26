package com.example.s_martapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class ShowCallHisAdapter extends RecyclerView.Adapter<ShowCallHisAdapter.myViewHolder> {

    @NonNull
    List<CallHisShow> showList;
    Context context;
    String num;

    public ShowCallHisAdapter(Context context, List<CallHisShow> showList, String num) {
        this.showList = showList;
        this.context=context;
        this.num=num;
    }

    @Override
    public ShowCallHisAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.callhistory_item,parent,false);
        ShowCallHisAdapter.myViewHolder viewHolder=new ShowCallHisAdapter.myViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ShowCallHisAdapter.myViewHolder holder, int position){

        CallHisShow show = showList.get(position);

        holder.name.setText(show.getName());
        holder.phone.setText(show.getPhone());
        holder.sem.setText(show.getSem());
        holder.date.setText(show.getDate());
        holder.title.setText("Called For: "+show.getTitle());

    }

    @Override
    public int getItemCount() {
        return showList.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder
    {
        TextView name,phone,sem,date,title;

        public myViewHolder(View itemView){

            super(itemView);
            name=itemView.findViewById(R.id.textView13);
            phone=itemView.findViewById(R.id.textView15);
            sem=itemView.findViewById(R.id.textView16);
            date=itemView.findViewById(R.id.textView18);
            title=itemView.findViewById(R.id.textView19);
        }
    }

}
