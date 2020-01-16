package com.example.solotask.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import com.example.solotask.Models.StoryFragment;
import com.example.solotask.R;
import com.example.solotask.Repositories.StoryRepository;

public class Create extends Fragment {

  private ArrayList<StoryFragment> fragmentList = new ArrayList<>();
  private ArrayList<StoryFragment> leafsList = new ArrayList<>();
  private EditText title;
  private EditText fragmentText;
  private TextView topText;
  private int maxId = 0;
  private ArrayList<String> dropItems = new ArrayList<>();
  private ArrayAdapter<String> dropAdapter;
  private String titleString;
  private String fragmentTextString;
  private boolean isQuestion = true;

  public Create() {
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {

    dropAdapter = new ArrayAdapter<>(getContext(),R.layout.spinner_list_item,R.id.spiinertxt,dropItems);
    final View view = inflater.inflate(R.layout.fragment_create, container, false);

    Button saveButton = view.findViewById(R.id.button_save);
    final Button addOne = view.findViewById(R.id.button_add_1);
    title = view.findViewById(R.id.titleText);
    fragmentText = view.findViewById(R.id.textCreateView);
    topText = view.findViewById(R.id.topText);


    saveButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (!fragmentList.isEmpty()) {
          titleString = title.getText().toString();
          StoryRepository.saveNewStory(titleString, fragmentList);
          fragmentList = new ArrayList<>();
          maxId = 0;
        } else {
          Toast.makeText(view.getContext(), "Cannot save an empty story", Toast.LENGTH_LONG).show();
        }
      }
    });

    addOne.setOnClickListener(new View.OnClickListener() {

      @Override
      public void onClick(View v) {
        fragmentTextString = fragmentText.getText().toString();
        if(isQuestion)
        {
          addSingle();
          isQuestion=false;
          Toast.makeText(view.getContext(), "Question " + maxId + "has been added", Toast.LENGTH_LONG)
                  .show();
        }
        else
        {
          addAns();
          isQuestion = true;
          Toast.makeText(view.getContext(), "Answer " + maxId + "has been added", Toast.LENGTH_LONG)
                  .show();
        }
        dropAdapter.clear();
        dropAdapter.notifyDataSetChanged();

      }
    });

    return view;
  }

  private void addSingle(){
    StoryFragment start = new StoryFragment();
    fragmentTextString = fragmentText.getText().toString();
      start.setPosition(maxId);
      start.setContent(fragmentTextString);
      fragmentList.add(start);
      maxId++;
      fragmentText.setText("");
      topText.setText("Enter the answer");

  }

  private void addAns(){
    StoryFragment parent = fragmentList.get(maxId-1);
    parent.setAnswer(fragmentTextString);
    topText.setText("Enter Question");
    fragmentText.setText("");
    isQuestion=false;
  }

}
