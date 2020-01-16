package com.example.solotask.Models;

import java.io.Serializable;

public class StoryFragment implements Serializable {

  private String content;
  private String answer;
  private int position;

  public StoryFragment() {
  }

  public StoryFragment(String content, String answer, int position) {
    this.content = content;
    this.answer = answer;
    this.position = position;
  }

  public String getAnswer() {
    return answer;
  }

  public void setAnswer(String answer) {
    this.answer = answer;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public int getPosition() {
    return position;
  }

  public void setPosition(int position) {
    this.position = position;
  }
}
