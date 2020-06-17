package com.test

import java.util.HashMap

import com.mvel.util.MvelCommonUtil

object TestMvel {
  def main(args: Array[String]): Unit = {
    test2()

  }
  def executeExpression(): Unit = {
    val expression = "a == '经理 1'"
    val vars = new HashMap[String, Any]();
    vars.put("a", "经理 1");
    val re = MvelCommonUtil()
    println(re.executeExpression(expression, vars))
  }

  def test() {
    val expression =
      "(foobar >3.0 || foobar > 7 ) && foobar2 >= 10.0 && str == 'strs'";
    for (i <- 1 to 10) {
      val vars = new HashMap[String, Any]();
      val a = i
      println(a)
      vars.put("foobar", a.toString());
      vars.put("foobar2", "10.11");
      vars.put("foobar3", "10.11");
      vars.put("str", "str");
      val re = MvelCommonUtil()
      println(re.executeExpression(expression, vars))
    }
  }
  def test2() {
    val expression = "x == 10";
    val vars = new HashMap[String, Any]();
    vars.put("x", 10.0d)
    val re = MvelCommonUtil()
    println(re.executeExpression(expression, vars))

  }

}
