package com.example.solotask.Activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.util.ArrayList;
import java.util.List;

import com.example.solotask.Models.Story;
import com.example.solotask.Models.StoryFragment;
import com.example.solotask.R;

public class PlayActivity extends AppCompatActivity {

  private ArrayList<String> al;
  private ArrayAdapter<String> arrayAdapter;
  protected int id = 0;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_play);
    Story story = (Story) getIntent().getSerializableExtra("PassedObject");
    final List contents = story.getStoryFragments();
    al = new ArrayList<>();
    StoryFragment currentItem = (StoryFragment) contents.get(id);
    al.add(currentItem.getContent());
    al.add("");

    arrayAdapter = new ArrayAdapter<>(this, R.layout.item, R.id.helloText, al);

    SwipeFlingAdapterView flingContainer = findViewById(R.id.frame);

    flingContainer.setAdapter(arrayAdapter);
    flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
      @Override
      public void removeFirstObjectInAdapter() {
        Log.d("LIST", "removed object!");
        al.remove(0);
        arrayAdapter.notifyDataSetChanged();
    }

      @Override
      public void onLeftCardExit(Object dataObject) {
        id--;
        if (id >= 0 && id < contents.size() - 1) {
          al.add("");
          al.set(0,((StoryFragment) contents.get(id)).getContent());
        }
        else
        {
          id=contents.size() - 1;
          al.add("");
          al.set(0,((StoryFragment) contents.get(id)).getContent());
        }
        arrayAdapter.notifyDataSetChanged();
      }

      @Override
      public void onRightCardExit(Object dataObject) {
        id++;
        System.out.println("Current ID = "+id);
        if (id >= 0 && id <= contents.size() - 1) {
          al.add("");
          al.set(0,((StoryFragment) contents.get(id)).getContent());
        }
        else
        {
          id=0;
          al.add("");
          al.set(0,((StoryFragment) contents.get(id)).getContent());
        }
        arrayAdapter.notifyDataSetChanged();
      }

      @Override
      public void onAdapterAboutToEmpty(int itemsInAdapter) {
      }

      @Override
      public void onScroll(float scrollProgressPercent) {
      }
    });

    flingContainer.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
      @Override
      public void onItemClicked(int itemPosition, Object dataObject) {
          String answer = ((StoryFragment) contents.get(id)).getAnswer();
        AlertDialog alertDialog = new AlertDialog.Builder(PlayActivity.this).create();
        alertDialog.setTitle("Answer");
        alertDialog.setMessage(answer);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                  public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                  }
                });
        alertDialog.show();
      }
    });

  }





}
