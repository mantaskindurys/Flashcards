package com.example.solotask.Repositories;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.solotask.Models.StoryFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import com.example.solotask.Models.Story;
import com.example.solotask.Models.StoryFragment;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class StoryRepository {

  private static final StoryRepository ourInstance = new StoryRepository();

  public static StoryRepository getInstance() {
    return ourInstance;
  }

  private StoryRepository() {

  }

  private FirebaseFirestore fireStore = FirebaseFirestore.getInstance();

  void save(Story story) {
    DocumentReference document = fireStore.collection("Stories").document();
    document.set(story);
  }

  public CollectionReference getStories() {
    return fireStore.collection("Stories");
  }

  public static void deleteStory(String selectedStoryID) {

    FirebaseFirestore fireStore = FirebaseFirestore.getInstance();
    final CollectionReference storiesRef = fireStore.collection("Stories");
    Query query = storiesRef.whereEqualTo("id", selectedStoryID);

    query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
      @Override
      public void onComplete(@NonNull Task<QuerySnapshot> task) {
        if (task.isSuccessful()) {
          for (DocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
            storiesRef.document(document.getId()).delete();
          }
        } else {
          Log.d(TAG, "Error getting documents: ", task.getException());
        }
      }
    });

  }


  public static void saveNewStory(String title, ArrayList<StoryFragment> storyFragmentList) {

    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    final String email = Objects.requireNonNull(firebaseAuth.getCurrentUser()).getEmail();

    Date now = new Date();
    long timestamp = now.getTime();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX", Locale.US);
    String dateStr = sdf.format(timestamp);

    Story testStory = new Story(dateStr, title, email, storyFragmentList);
    StoryRepository.getInstance().save(testStory);
  }

}


