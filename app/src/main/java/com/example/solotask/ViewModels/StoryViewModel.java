package com.example.solotask.ViewModels;


import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import com.example.solotask.Models.Story;
import com.example.solotask.Repositories.StoryRepository;

public class StoryViewModel extends ViewModel {

  private StoryRepository repository = StoryRepository.getInstance();
  private MutableLiveData<List<Story>> mutableLiveData = new MutableLiveData<>();


  public LiveData<List<Story>> getStoryByUserId() {
    repository.getStories().addSnapshotListener(new EventListener<QuerySnapshot>() {
      @Override
      public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots,
          @Nullable FirebaseFirestoreException e) {
        List<Story> temp = new ArrayList<>();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        final String currentEmail = user.getEmail();
        assert queryDocumentSnapshots != null;
        for (QueryDocumentSnapshot snapshot : queryDocumentSnapshots) {
          Story story = snapshot.toObject(Story.class);
          if (story.getUserEmail().equals(currentEmail)) {
            temp.add(story);
          }
        }
        mutableLiveData.setValue(temp);
      }
    });
    return mutableLiveData;
  }


  public LiveData<List<Story>> getOtherStoryByUserId() {
    repository.getStories().addSnapshotListener(new EventListener<QuerySnapshot>() {
      @Override
      public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots,
          @Nullable FirebaseFirestoreException e) {
        List<Story> temp = new ArrayList<>();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        final String currentEmail = user.getEmail();
        assert queryDocumentSnapshots != null;
        for (QueryDocumentSnapshot snapshot : queryDocumentSnapshots) {
          Story story = snapshot.toObject(Story.class);
          if (!story.getUserEmail().equals(currentEmail)) {
            temp.add(story);
          }
        }
        mutableLiveData.setValue(temp);
      }
    });
    return mutableLiveData;
  }

}
