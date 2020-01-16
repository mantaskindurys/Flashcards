package com.example.solotask.Fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AlertDialog;
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
import com.example.solotask.CustomAdapters.CustomAdapterNoEmail;
import com.example.solotask.Models.Story;
import com.example.solotask.R;
import com.example.solotask.Repositories.StoryRepository;
import com.example.solotask.ViewModels.StoryViewModel;

public class MyStories extends Fragment {

  private ArrayList<Story> userStories = new ArrayList<>();

  public MyStories() {
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    final View myStoryView = inflater.inflate(R.layout.fragment_my_stories, container, false);
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    final String user = Objects.requireNonNull(firebaseAuth.getCurrentUser()).getEmail();
    FirebaseDatabase.getInstance().getReference().child("Stories");
    StoryViewModel storyViewModel = ViewModelProviders.of(this).get(StoryViewModel.class);

    RecyclerView recyclerView = myStoryView.findViewById(R.id.recyclerView);
    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    final CustomAdapterNoEmail adapter = new CustomAdapterNoEmail(getContext(), userStories);
    DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(),
        DividerItemDecoration.VERTICAL);
    dividerItemDecoration.setDrawable(Objects
        .requireNonNull(ContextCompat.getDrawable(getContext(), R.drawable.recyclerview_devider)));
    recyclerView.addItemDecoration(dividerItemDecoration);
    recyclerView.setAdapter(adapter);

    adapter.setOnItemClickListener(new CustomAdapterNoEmail.ClickListener() {
      @Override
      public void onItemClick(int position, View v) {
        StartPlayActivity(userStories.get(position));
      }

      @Override
      public void onItemLongClick(int position, View v) {
        final int item = position;
        AlertDialog.Builder builder = new AlertDialog.Builder(
            Objects.requireNonNull(getActivity()));
        builder.setTitle("Delete story ?");
        builder.setItems(new CharSequence[]
                {"No", "Yes"},
            new DialogInterface.OnClickListener() {
              public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                  case 0:
                    break;
                  case 1:
                    StoryRepository.deleteStory(userStories.get(item).getId());
                    break;
                }
              }
            });
        builder.create().show();

      }
    });

    storyViewModel.getStoryByUserId().observe(this, new Observer<List<Story>>() {
      @Override
      public void onChanged(List<Story> stories) {

        userStories.clear();

        for (Story story : stories) {
          System.out.println(story.getUserEmail());
          if (story.getUserEmail().equals(user)) {
            userStories.add(story);
          }
        }
        adapter.notifyDataSetChanged();

      }
    });

    return myStoryView;
  }


  private void StartPlayActivity(Story story) {
    Intent intent = new Intent(getActivity(), PlayActivity.class);
    intent.putExtra("PassedObject", story);
    startActivity(intent);
  }

}
