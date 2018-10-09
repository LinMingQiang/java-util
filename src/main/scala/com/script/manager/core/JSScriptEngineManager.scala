package com.script.manager.core

import javax.script.ScriptEngine
import javax.script.ScriptEngineManager
import javax.script.Compilable
import java.util.HashMap
import javax.script.CompiledScript
import javax.script.Invocable

object JSScriptEngineManager {
  var engine: ScriptEngine = null
  var invoke: Invocable = null
  val compileMap = new HashMap[Int, CompiledScript]
  def getJSEngineManager() = {
    if (engine == null)
      engine = new ScriptEngineManager().getEngineByName("JavaScript");
    engine
  }
  def getJSInvocable() = {
    getJSEngineManager
    if (invoke == null)
      invoke = engine.asInstanceOf[Invocable];
    invoke
  }
  def getCompile(code: String) = {
    if (compileMap.containsKey(code.hashCode())) {
      compileMap.get(code.hashCode())
    } else {
      val com = engine.asInstanceOf[Compilable].compile(code)
      compileMap.put(code.hashCode(), com)
      com
    }

  }
}