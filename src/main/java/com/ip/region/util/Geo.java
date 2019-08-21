package com.ip.region.util;

import java.io.Serializable;

public class Geo
    implements Serializable
{
  private static final long serialVersionUID = -2322079818253597199L;
  private String country;
  private String province;
  private String city;
  private String region;

  public Geo()
  {
    this.country = "";
    this.province = "";
    this.city = "";
    this.region = "";
  }

  public String getCountry()
  {
    return this.country;
  }
  public void setCountry(String country) {
    this.country = country;
  }
  public String getProvince() {
    return this.province;
  }
  public void setProvince(String province) {
    this.province = province;
  }
  public String getCity() {
    return this.city;
  }
  public void setCity(String city) {
    this.city = city;
  }
  public String getRegion() {
    return this.region;
  }
  public void setRegion(String region) {
    this.region = region;
  }
}