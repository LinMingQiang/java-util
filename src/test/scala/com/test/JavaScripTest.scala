package com.test

import javax.script.ScriptEngineManager
import javax.script.Compilable
import javax.script.Invocable
import java.util.Date
import com.script.manager.core.JSScriptEngineManager

object JavaScripTest {
  def main(args: Array[String]): Unit = {
    val code = s"""
      function merge(data) { 
           a=data.substring(16,17)
           if (parseInt(a)%2==0)
                    return ("男");
               else 
                   return ('女');
        }
        """
    val invoke = JSScriptEngineManager.getJSInvocable()
    val com = JSScriptEngineManager.getCompile(code)
    com.eval()
    
    for (i <- 1 to 10) {
      val re = invoke.invokeFunction("merge", "2020202002202020" + i);
      println(re)
    }
  }
}