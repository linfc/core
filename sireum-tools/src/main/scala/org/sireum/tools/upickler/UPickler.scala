/*
Copyright (c) 2014 Robby, Kansas State University.        
All rights reserved. This program and the accompanying materials      
are made available under the terms of the Eclipse Public License v1.0 
which accompanies this distribution, and is available at              
http://www.eclipse.org/legal/epl-v10.html                             
*/

package org.sireum.tools.upickler

import org.sireum.option._
import org.sireum.util._

import org.stringtemplate.v4._

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
object UPickler {
  def run(option : UPicklerMode) {
    new UPickler().execute(option)
  }
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
class UPickler {
  def execute(option : UPicklerMode) {
    val r = pickler(option)
    System.out.println(r)
    System.out.flush
  }

  def pickler(option : UPicklerMode) : String = {
      def p(name : String) =
        if (option.packageName == "" || name.contains(".")) name
        else option.packageName + "." + name
    pickler(option.packageName,
      Class.forName(p(option.rootClass)),
      option.importClasses.map(c => Class.forName(p(c))),
      option.leafClasses.map(c => Class.forName(p(c))) : _*)
  }

  def pickler(packageName : String,
              root : Class[_],
              imports : ISeq[Class[_]],
              leaves : Class[_]*) : String = {
    val stg = new STGroupFile(UPickler.getClass
      .getResource("upickler.stg").toURI().toURL(), "UTF-8", '$', '$')
    val stMain = stg.getInstanceOf("main")

    val st = stg.getInstanceOf("pickler").
      add("package", root.getPackage.getName).
      add("class", root.getSimpleName)
    stMain.add("member", st)

    for (c <- imports) {
      if (c.getPackage.getName != packageName)
        st.add("member", s"import ${c.getPackage.getName}._")
      st.add("member", s"import ${c.getSimpleName}Picklers._")
    }

    val stRoot = stg.getInstanceOf("root").add("class", root.getSimpleName)

    for (c <- leaves) {
      val name = c.getSimpleName

      st.add("member", stg.getInstanceOf("name").add("class", name))

      val stLeaf = stg.getInstanceOf("leaf").add("class", name)
      val stPy = stg.getInstanceOf("leafPy").add("class", name)
      st.add("member", stLeaf)
      stMain.add("member", stPy)

      val cc = Reflection.CaseClass.caseClassType(c, false)
      for (p <- cc.params) {
        val pName = p.name
        val pType = simpleName(packageName, p.tipe.toString)
        stLeaf.
          add("wfield", stg.getInstanceOf("leafWriteField").add("name", pName)).
          add("rfield", stg.getInstanceOf("leafReadField").add("type", pType)
            .add("name", pName))
        stPy.add("field", pName).
          add("fieldAssign", stg.getInstanceOf("leafPyAssign").add("name", pName))
      }

      stRoot.
        add("wfield", stg.getInstanceOf("rootWriteField").add("class", name)).
        add("rfield", stg.getInstanceOf("rootReadField").add("class", name))

    }

    st.add("member", stRoot)

    stMain.render
  }

  def simpleName(packageName : String, name : String) = {
    if (packageName != "") name.replaceAll(packageName + ".", "") else name
  }
}