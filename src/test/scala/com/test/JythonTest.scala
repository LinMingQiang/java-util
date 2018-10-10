package com.test

import java.util.Date
import org.python.core.Py
import com.script.manager.core.JythonManager
import org.python.core.PyFunction

object JythonTest {
  def main(args: Array[String]): Unit = {
    var jythonInterp = JythonManager.getInterp();
    val code = s"""
#这是中文注释
def transform(data):
    r = data[16]
    if int(r)%2==0:
        r='男'
    else :
        r='女'
    return r.decode('utf-8')
"""
    val d = jythonInterp.compile(Py.newStringOrUnicode(code).toString())
    jythonInterp.exec(d)

    for (i <- 1 to 10) {
      var data = Py.newStringUTF8("1010101001101010" + i)
      val ret = jythonInterp.get("transform", classOf[PyFunction])
      val result = ret.__call__(data)
      println("return : " + result)
    }

  }
}