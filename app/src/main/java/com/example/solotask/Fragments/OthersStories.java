package com.example.solotask.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.example.solotask.Activities.PlayActivity;
import com.example.solotask.CustomAdapters.CustomAdapter;
import com.example.solotask.Models.Story;
import com.example.solotask.R;
import com.example.solotask.ViewModels.StoryViewModel;

public class OthersStories extends Fragment {

  private ArrayList<Story> otherStories = new ArrayList<>();

  public OthersStories() {
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    final View otherStoryView = inflater.inflate(R.layout.fragment_my_stories, container, false);
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    final String user = Objects.requireNonNull(firebaseAuth.getCurrentUser()).getEmail();
    FirebaseDatabase.getInstance().getReference().child("Stories");
    StoryViewModel storyViewModel = ViewModelProviders.of(this).get(StoryViewModel.class);

    RecyclerView recyclerView = otherStoryView.findViewById(R.id.recyclerView);
    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    final CustomAdapter adapter = new CustomAdapter(getContext(), otherStories);
    DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
        Objects.requireNonNull(getContext()), DividerItemDecoration.VERTICAL);
    dividerItemDecoration.setDrawable(Objects
        .requireNonNull(ContextCompat.getDrawable(getContext(), R.drawable.recyclerview_devider)));
    recyclerView.addItemDecoration(dividerItemDecoration);
    recyclerView.setAdapter(adapter);

    adapter.setOnItemClickListener(new CustomAdapter.ClickListener() {
      @Override
      public void onItemClick(int position, View v) {
        StartPlayActivity(otherStories.get(position));
      }

      @Override
      public void onItemLongClick(int position, View v) {

      }
    });

    storyViewModel.getOtherStoryByUserId().observe(this, new Observer<List<Story>>() {
      @Override
      public void onChanged(List<Story> stories) {

        otherStories.clear();

        for (Story story : stories) {
          System.out.println(story.getUserEmail());
          if (!story.getUserEmail().equals(user)) {
            otherStories.add(story);
          }
        }
        adapter.notifyDataSetChanged();

      }
    });

    return otherStoryView;
  }

  private void StartPlayActivity(Story story) {
    Intent intent = new Intent(getActivity(), PlayActivity.class);
    intent.putExtra("PassedObject", story);
    startActivity(intent);
  }


}
