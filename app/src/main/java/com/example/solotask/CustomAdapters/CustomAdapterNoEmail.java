package com.example.solotask.CustomAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.solotask.Models.Story;

import java.util.ArrayList;
import java.util.List;

import com.example.solotask.Models.Story;
import com.example.solotask.R;

public class CustomAdapterNoEmail extends RecyclerView.Adapter<CustomAdapterNoEmail.MyViewHolder> {

  private Context context;
  private List<Story> stories;
  private static ClickListener clickListener;

  public interface ClickListener {

    void onItemClick(int position, View v);

    void onItemLongClick(int position, View v);
  }


  public CustomAdapterNoEmail(Context context, ArrayList<Story> stories) {
    this.context = context;
    this.stories = stories;
  }

  @NonNull
  @Override
  public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);

    return new MyViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

    holder.title.setText(stories.get(position).getTitle());

  }

  @Override
  public int getItemCount() {
    return stories.size();
  }

  public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
      View.OnLongClickListener {

    TextView title;

    MyViewHolder(@NonNull View itemView) {
      super(itemView);
      itemView.setOnClickListener(this);
      itemView.setOnLongClickListener(this);
      title = itemView.findViewById(R.id.txt_title);
    }


    @Override
    public void onClick(View view) {
      clickListener.onItemClick(getAdapterPosition(), view);
    }

    @Override
    public boolean onLongClick(View view) {
      clickListener.onItemLongClick(getAdapterPosition(), view);
      return false;
    }
  }

  public void setOnItemClickListener(ClickListener clickListener) {
    CustomAdapterNoEmail.clickListener = clickListener;
  }

}
