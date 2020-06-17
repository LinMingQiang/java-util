**Java 调用 Python** <br> 
```
val jythonInterp = JythonManager.getInterp();
    val code = s"""
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
```
**Java 表达式解析 Mvel** <br>
```
val expression = "word == 'hello world'"
    val vars = new HashMap[String, Any]();
    vars.put("word", "hello world");
    val re = MvelCommonUtil()
    println(re.executeExpression(expression, vars))
```
