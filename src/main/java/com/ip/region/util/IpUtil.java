package com.ip.region.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class IpUtil
{
  private static IpUtil ipInstance = null;

  private Log log = LogFactory.getLog(getClass());
  private static TreeMap<Long, String> ipTree;
  private static Hashtable<String, Region> regionTable;
  private static List<RTBAsiaChinaCodeVO> provinces;
  private static List<RTBAsiaChinaCodeVO> cities;

  private IpUtil()
  {
    ipTree = new TreeMap();
    regionTable = new Hashtable();
    provinces = new ArrayList();
    cities = new ArrayList();
    load();
    generatProvincesList();
    generatCityList();
  }

  public static IpUtil newInstance() {
    if (ipInstance == null) {
      synchronized (IpUtil.class) {
        if (null == ipInstance) {
          ipInstance = new IpUtil();
        }
      }
    }
    return ipInstance;
  }

  private void load() {
    try {
      BufferedReader br1 = new BufferedReader(new InputStreamReader(IpUtil.class.getResourceAsStream("/ip.csv")));
      BufferedReader br2 = new BufferedReader(new InputStreamReader(IpUtil.class.getResourceAsStream("/region.csv")));
      String str = null;
      while ((str = br2.readLine()) != null) {
        String regionId = str.split("\\t")[0];
        String regionName = str.split("\\t")[1];
        String parentRegionId = str.split("\\t")[2];
        Region region = new Region();
        region.setId(regionId);
        region.setName(regionName);
        region.setParentId(parentRegionId);
        regionTable.put(regionId, region);
      }
      while ((str = br1.readLine()) != null) {
        String lowIp = str.split(",")[0].trim();
        String highIp = str.split(",")[1].trim();
        String regionId = str.split(",")[2].trim();
        Long ip1 = Long.valueOf(ip2long(lowIp));
        ipTree.put(ip1, ip2long(highIp) + "_" + regionId);
      }
      br1.close();
      br2.close();
    }
    catch (Exception e) {
      this.log.error("加载ip库出错", e);
    }
  }

  public Geo findGeo(String ip) {
    Geo geo = new Geo();
    try {
      Long longIp = Long.valueOf(ip2long(ip));
      Entry entry = ipTree.floorEntry(longIp);
      if (entry != null) {
        String value = (String)entry.getValue();
        Long ip2 = Long.valueOf(Long.parseLong(value.substring(0, value.indexOf("_"))));
        if (ip2.longValue() >= longIp.longValue()) {
          String regionId = value.substring(value.indexOf("_") + 1);
          if (regionTable.containsKey(regionId)) {
            Region region = (Region)regionTable.get(regionId);
            if ((regionId.equals("1000000000")) || (region.getParentId().equals("1000000000"))) {
              geo.setRegion("");
              geo.setCity("");
              geo.setProvince("");
              geo.setCountry(region.getName());
            }
            else {
              Region region1 = null;
              Region region2 = null;
              region1 = (Region)regionTable.get(region.getParentId());
              if ((region.getId().equals("1156310000")) || (region.getId().equals("1156120000")) || (region.getId().equals("1156110000")) || (region.getId().equals("1156500000")))
              {
                geo.setCountry("中国");
                geo.setProvince(region.getName());
                geo.setCity(region.getName());
              }
              else if ((region1.getId().equals("1156310000")) || (region1.getId().equals("1156120000")) || (region1.getId().equals("1156110000")) || (region1.getId().equals("1156500000")))
              {
                geo.setCountry("中国");
                geo.setProvince(region1.getName());
                geo.setCity(region1.getName());
                geo.setRegion(region.getName());
              }
              else if (region.getParentId().equals("1156000000")) {
                geo.setCountry("中国");
                geo.setProvince(region.getName());
              }
              else if (region1.getParentId().equals("1156000000")) {
                geo.setCountry("中国");
                geo.setProvince(region1.getName());
                geo.setCity(region.getName());
              }
              else {
                region2 = (Region)regionTable.get(region1.getParentId());
                geo.setCountry("中国");
                geo.setProvince(region2.getName());
                geo.setCity(region1.getName());
                geo.setRegion(region.getName());
              }
            }
          }
        }
      }
    }
    catch (Exception e) {
      this.log.error("获取地域信息出错", e);
      geo = null;
    }
    return geo;
  }

  public List<RTBAsiaChinaCodeVO> getProvinceList() {
    return provinces;
  }

  public List<RTBAsiaChinaCodeVO> getCitysList() {
    return cities;
  }

  private void generatProvincesList()
  {
    try {
      RTBAsiaChinaCodeVO china = getSubAreaById("1156000000");
      if (china.getSubList() != null) {
        for (int i = 0; i < china.getSubList().size(); i++)
          provinces.add(china.getSubList().get(i));
      }
    }
    catch (Exception e)
    {
      this.log.error("获取省信息出错", e);
    }
  }

  private void generatCityList() {
    try {
      for (int i = 0; i < provinces.size(); i++) {
        if ((((RTBAsiaChinaCodeVO)provinces.get(i)).getLocationCode() == 1156310000L) || (((RTBAsiaChinaCodeVO)provinces.get(i)).getLocationCode() == 1156120000L) || (((RTBAsiaChinaCodeVO)provinces.get(i)).getLocationCode() == 1156110000L) || (((RTBAsiaChinaCodeVO)provinces.get(i)).getLocationCode() == 1156500000L)) {
          cities.add(provinces.get(i));
        }
        else
          cities.addAll(((RTBAsiaChinaCodeVO)provinces.get(i)).getSubList());
      }
    }
    catch (Exception e)
    {
      this.log.error("获取市信息出错", e);
    }
  }

  public boolean isValidProvince(String province) {
    boolean result = false;
    for (int i = 0; i < provinces.size(); i++) {
      if (((RTBAsiaChinaCodeVO)provinces.get(i)).getName_zh().equals(province)) {
        result = true;
      }
    }
    return result;
  }

  public boolean isValidProvinceCity(String province, String city) {
    boolean result = false;
    if (!isValidProvince(province)) return result;
    for (int i = 0; i < provinces.size(); i++) {
      if (((RTBAsiaChinaCodeVO)provinces.get(i)).getName_zh().equals(province)) {
        if ((((RTBAsiaChinaCodeVO)provinces.get(i)).getLocationCode() == 1156310000L) || (((RTBAsiaChinaCodeVO)provinces.get(i)).getLocationCode() == 1156120000L) || (((RTBAsiaChinaCodeVO)provinces.get(i)).getLocationCode() == 1156110000L) || (((RTBAsiaChinaCodeVO)provinces.get(i)).getLocationCode() == 1156500000L)) {
          if (city.equals(province))
            result = true;
        }
        else
        {
          List cities = ((RTBAsiaChinaCodeVO)provinces.get(i)).getSubList();
          for (int j = 0; j < cities.size(); j++) {
            if (((RTBAsiaChinaCodeVO)cities.get(j)).getName_zh().equals(city)) {
              result = true;
            }
          }
        }
      }
    }
    return result;
  }

  public boolean isValidProvinceCityArea(String province, String city, String area) {
    boolean result = false;
    if (!isValidProvince(province)) return result;
    if (!isValidProvinceCity(province, city)) return result;
    for (int i = 0; i < cities.size(); i++) {
      if (((RTBAsiaChinaCodeVO)cities.get(i)).getName_zh().equals(city)) {
        List temp = ((RTBAsiaChinaCodeVO)cities.get(i)).getSubList();
        for (int j = 0; j < temp.size(); j++) {
          if (((RTBAsiaChinaCodeVO)temp.get(j)).getName_zh().equals(area)) {
            result = true;
            break;
          }
        }
      }
    }
    return result;
  }

  private static RTBAsiaChinaCodeVO getSubAreaById(String id) {
    RTBAsiaChinaCodeVO result = null;
    if (!regionTable.containsKey(id)) return result;
    result = new RTBAsiaChinaCodeVO(Long.parseLong(id), ((Region)regionTable.get(id)).getName());
    List subArea = new ArrayList();
    Set set = regionTable.keySet();
    Iterator it = set.iterator();
    while (it.hasNext()) {
      Region region = (Region)regionTable.get(it.next());
      if (region.getParentId().equals(id)) {
        if ((id.equals("1156000000")) && ((region.getId().equals("1156310000")) || (region.getId().equals("1156120000")) || (region.getId().equals("1156110000")) || (region.getId().equals("1156500000")))) {
          RTBAsiaChinaCodeVO mainCity = new RTBAsiaChinaCodeVO(Long.parseLong(region.getId()) * 10L, region.getName());
          List tempsubArea = new ArrayList();
          tempsubArea.add(getSubAreaById(region.getId()));
          mainCity.setSubList(tempsubArea);
          subArea.add(mainCity);
        }
        else {
          RTBAsiaChinaCodeVO temp = getSubAreaById(region.getId());
          subArea.add(temp);
        }
      }
    }
    result.setSubList(subArea);
    return result;
  }

  private static int str2Ip(String ip) {
    String[] ss = ip.split("\\.");

    int a = Integer.parseInt(ss[0]);
    int b = Integer.parseInt(ss[1]);
    int c = Integer.parseInt(ss[2]);
    int d = Integer.parseInt(ss[3]);
    return a << 24 | b << 16 | c << 8 | d;
  }

  private static long ip2long(String ip) {
    return int2long(str2Ip(ip));
  }

  private static long int2long(int i) {
    long l = i & 0x7FFFFFFF;
    if (i < 0) {
      l |= 2147483648L;
    }
    return l;
  }

  public static void main(String[] args)
  {

  }
}
