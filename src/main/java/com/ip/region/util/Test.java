package com.ip.region.util;

public class Test {

  public static void main(String[] args) {

    IpUtil u =  IpUtil.newInstance();
    Geo geo = u.findGeo("61.174.15.247");
    System.out.println(geo.getProvince());

  }
}
