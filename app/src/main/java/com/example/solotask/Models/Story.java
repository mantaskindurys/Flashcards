package com.example.solotask.Models;

import java.io.Serializable;
import java.util.List;

public class Story implements Serializable {

  private String id;
  private String title;
  private String userEmail;
  private List<StoryFragment> storyFragments;

  public Story() {
  }

  public Story(String id, String title, String userEmail, List<StoryFragment> storyFragments) {
    this.id = id;
    this.title = title;
    this.userEmail = userEmail;
    this.storyFragments = storyFragments;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getUserEmail() {
    return userEmail;
  }

  public void setUserEmail(String userEmail) {
    this.userEmail = userEmail;
  }

  public List<StoryFragment> getStoryFragments() {
    return storyFragments;
  }

  public void setStoryFragments(List<StoryFragment> storyFragments) {
    this.storyFragments = storyFragments;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }
}
