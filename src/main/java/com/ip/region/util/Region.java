package com.ip.region.util;

public class Region
{
  private String id;
  private String name;
  private String parentId;

  public String getId()
  {
    return this.id;
  }
  public void setId(String id) {
    this.id = id;
  }
  public String getName() {
    return this.name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public String getParentId() {
    return this.parentId;
  }
  public void setParentId(String parentId) {
    this.parentId = parentId;
  }
}