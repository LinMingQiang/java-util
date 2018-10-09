package com.script.manager.core

import java.util.Properties
import org.python.util.PythonInterpreter

object JythonManager {
  var jythonInterp: PythonInterpreter = null
  /**
   * @author LinMingqiang
   * @time 2018-10-10
   * @desc 指定python的第三方路径和sitepack路径
   */
  def getInterp(pythonsitePackPath: String,
                pythonLibPath: String) = {
    if (jythonInterp == null) {
      val props = new Properties();
      props.put("python.console.encoding", "UTF-8");
      props.put("python.security.respectJavaAccessibility", "false");
      props.put("python.import.site", "false");
      val preprops = System.getProperties();
      PythonInterpreter.initialize(preprops, props, null);
      jythonInterp = new PythonInterpreter();
      jythonInterp.exec("import sys")
      jythonInterp.exec(s"""sys.path.append(${pythonsitePackPath})""")
      jythonInterp.exec(s"""sys.path.append(${pythonLibPath})""")
    }
    jythonInterp
  }
  /**
   * @author LinMingqiang
   * @time 2018-10-10
   */
  def getInterp() = {
    if (jythonInterp == null) {
      val props = new Properties();
      props.put("python.console.encoding", "UTF-8");
      props.put("python.security.respectJavaAccessibility", "false");
      props.put("python.import.site", "false");
      val preprops = System.getProperties();
      PythonInterpreter.initialize(preprops, props, null);
      jythonInterp = new PythonInterpreter();
    }
    jythonInterp
  }
  /**
   * @author LMQ
   */
  def appendSysPath(jythonInterp: PythonInterpreter, paths: String*) {
    jythonInterp.exec("import sys")
    paths.foreach(path =>
      jythonInterp.exec(s"""sys.path.append(${path})"""))

  }
  def removeSysPath(jythonInterp: PythonInterpreter, paths: String*) {
    jythonInterp.exec("import sys")
    paths.foreach(path =>
      jythonInterp.exec(s"""sys.path.remove(${path})"""))

  }
}