package com.ip.region.util;

import java.util.List;

public class RTBAsiaChinaCodeVO
{
  private long locationCode;
  private String name_zh;
  private List<RTBAsiaChinaCodeVO> subList;

  public RTBAsiaChinaCodeVO(long locationCode, String name_zh)
  {
    this.locationCode = locationCode;
    this.name_zh = name_zh;
  }

  public long getLocationCode() {
    return this.locationCode;
  }

  public void setLocationCode(Integer locationCode) {
    this.locationCode = locationCode.intValue();
  }

  public String getName_zh() {
    return this.name_zh;
  }

  public void setName_zh(String name_zh) {
    this.name_zh = name_zh;
  }

  public List<RTBAsiaChinaCodeVO> getSubList() {
    return this.subList;
  }

  public void setSubList(List<RTBAsiaChinaCodeVO> subList) {
    this.subList = subList;
  }
}
