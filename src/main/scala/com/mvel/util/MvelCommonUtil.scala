package com.mvel.util

import java.io.Serializable
import java.util.HashMap

import org.mvel2.MVEL

import scala.collection.JavaConversions._

object MvelCommonUtil {
  def apply(): MvelCommonUtil = new MvelCommonUtil()
  class MvelCommonUtil {
    var compilers: HashMap[String, Serializable] =
      new HashMap[String, Serializable]

    /**
      * 不采用预编译
      */
    def eval(expression: String, vars: java.util.Map[String, Any]): Boolean = {
      MVEL.eval(expression, vars).asInstanceOf[Boolean]
    }

    /**
      * 采用预编译
      * @param expression
      * @param vars
      */
    def executeExpression(expression: String,
                          vars: java.util.Map[String, Any]): Boolean = {
      MVEL.executeExpression(getCompilerEval(expression), vars).asInstanceOf[Boolean]
    }

    /**
      *
      * @param exprs
      */
    def setCompiler(exprs: Array[String]) {
      compilers.clear()
      exprs.foreach { expr =>
        val compiled = MVEL.compileExpression(expr);
        compilers.put(expr, compiled)
      }
    }

    /**
      *
      * @param expression
      * @return
      */
    def getCompilerEval(expression: String): Serializable = {
      compilers.getOrElse(expression, {
        val compiled = MVEL.compileExpression(expression);
        compilers.put(expression, compiled)
        compiled
      })
    }

  }

}
