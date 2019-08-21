package com.ip.region.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class IpTreeNode
    implements Serializable
{
  private static final long serialVersionUID = 164722537330298798L;
  private String parentId;
  private String selfId;
  protected String nodeName;
  protected String lowIp;
  protected String highIp;
  protected IpTreeNode parentNode;
  protected List<IpTreeNode> childList;
  protected int level;
  protected int number;

  public int getLevel()
  {
    return this.level;
  }

  public void setLevel(int level) {
    this.level = level;
  }

  public int getNumber() {
    return this.number;
  }

  public void setNumber(int number) {
    this.number = number;
  }

  public String getParentId() {
    return this.parentId;
  }

  public void setParentId(String parentId) {
    this.parentId = parentId;
  }

  public String getSelfId() {
    return this.selfId;
  }

  public void setSelfId(String selfId) {
    this.selfId = selfId;
  }

  public String getNodeName() {
    return this.nodeName;
  }

  public void setNodeName(String nodeName) {
    this.nodeName = nodeName;
  }

  public String getLowIp() {
    return this.lowIp;
  }

  public void setLowIp(String lowIp) {
    this.lowIp = lowIp;
  }

  public String getHighIp() {
    return this.highIp;
  }

  public void setHighIp(String highIp) {
    this.highIp = highIp;
  }

  public IpTreeNode getParentNode() {
    return this.parentNode;
  }

  public void setParentNode(IpTreeNode parentNode) {
    this.parentNode = parentNode;
  }

  public static long getSerialversionuid() {
    return 164722537330298798L;
  }

  public void setChildList(List<IpTreeNode> childList) {
    this.childList = childList;
  }

  public IpTreeNode() {
    initChildList();
  }

  public IpTreeNode(IpTreeNode parentNode) {
    getParentNode();
    initChildList();
  }

  public boolean isLeaf() {
    if (this.childList == null) {
      return true;
    }
    if (this.childList.isEmpty()) {
      return true;
    }
    return false;
  }

  public void addChildNode(IpTreeNode treeNode)
  {
    initChildList();
    this.childList.add(treeNode);
  }

  public void initChildList() {
    if (this.childList == null)
      this.childList = new ArrayList();
  }

  public boolean isValidTree() {
    return true;
  }

  public List<IpTreeNode> getElders()
  {
    List elderList = new ArrayList();
    IpTreeNode parentNode = getParentNode();
    if (parentNode == null) {
      return elderList;
    }
    elderList.add(parentNode);
    elderList.addAll(parentNode.getElders());
    return elderList;
  }

  public List<IpTreeNode> getJuniors()
  {
    List juniorList = new ArrayList();
    List childList = getChildList();
    if (childList == null) {
      return juniorList;
    }
    int childNumber = childList.size();
    for (int i = 0; i < childNumber; i++) {
      IpTreeNode junior = (IpTreeNode)childList.get(i);
      juniorList.add(junior);
      juniorList.addAll(junior.getJuniors());
    }
    return juniorList;
  }

  public List<IpTreeNode> getLeafes()
  {
    List juniorList = new ArrayList();
    List childList = getChildList();
    if (childList == null) {
      return juniorList;
    }
    int childNumber = childList.size();
    for (int i = 0; i < childNumber; i++) {
      IpTreeNode junior = (IpTreeNode)childList.get(i);
      if (junior.isLeaf())
        juniorList.add(junior);
      juniorList.addAll(junior.getLeafes());
    }
    return juniorList;
  }

  public List<IpTreeNode> getChildList()
  {
    return this.childList;
  }

  public void deleteNode()
  {
    IpTreeNode parentNode = getParentNode();
    String id = getSelfId();

    if (parentNode != null)
      parentNode.deleteChildNode(id);
  }

  public void deleteChildNode(String childId)
  {
    List childList = getChildList();
    int childNumber = childList.size();
    for (int i = 0; i < childNumber; i++) {
      IpTreeNode child = (IpTreeNode)childList.get(i);
      if (child.getSelfId() == childId) {
        childList.remove(i);
        return;
      }
    }
  }

  public boolean insertJuniorNode(IpTreeNode treeNode)
  {
    String juniorParentId = treeNode.getParentId();
    if (this.selfId.equals(juniorParentId)) {
      addChildNode(treeNode);
      return true;
    }
    List childList = getChildList();
    int childNumber = childList.size();

    for (int i = 0; i < childNumber; i++) {
      IpTreeNode childNode = (IpTreeNode)childList.get(i);
      boolean insertFlag = childNode.insertJuniorNode(treeNode);
      if (insertFlag == true)
        return true;
    }
    return false;
  }

  public String getLastNodeName()
  {
    String nodeName = null;
    if (isLeaf()) {
      return this.nodeName;
    }

    List list = getLeafes();
    nodeName = ((IpTreeNode)list.get(list.size() - 1)).getNodeName();

    return nodeName;
  }

  public IpTreeNode findIpTreeById(String id)
  {
    if (this.selfId.equals(id))
      return this;
    if ((this.childList.isEmpty()) || (this.childList == null)) {
      return null;
    }
    int childNumber = this.childList.size();
    for (int i = 0; i < childNumber; i++) {
      IpTreeNode child = (IpTreeNode)this.childList.get(i);
      IpTreeNode resultNode = child.findIpTreeById(id);
      if (resultNode != null) {
        return resultNode;
      }
    }
    return null;
  }
}