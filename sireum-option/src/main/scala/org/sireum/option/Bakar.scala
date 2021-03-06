/*
Copyright (c) 2011-2014 Robby, Kansas State University.
All rights reserved. This program and the accompanying materials
are made available under the terms of the Eclipse Public License v1.0
which accompanies this distribution, and is available at
http://www.eclipse.org/legal/epl-v10.html
*/

package org.sireum.option

import org.sireum.util._

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
@Mode(command = "bakar",
  desc = "Sireum Bakar Tools",
  header = """
Sireum for Spark
(c) 2012-2015, SAnToS Laboratory, Kansas State University
""")
case class SireumBakarMode(
  typ : SireumBakarTypeMode = SireumBakarTypeMode(),
  program : SireumBakarProgramMode = SireumBakarProgramMode(),
  kiasan : KiasanBakarMode = EmptySireumMode.internal(KiasanBakarMode()))

object TypeTarget extends Enum {
  sealed abstract class Type extends EnumElem
  object Ocaml extends Type
  object Coq extends Type

  def elements = ivector(Ocaml, Coq)
}

@Main(value = "type",
  className = "org.sireum.bakar.tools.BakarType",
  featureName = "Sireum Bakar Tools:Gnat.sapp",
  desc = "Generate Type Definitions")
case class SireumBakarTypeMode(
  @Option(shortKey = "t", longKey = "type", desc = "") var typ : TypeTarget.Type = TypeTarget.Coq,

  @OptionalArg(index = 0, value = "Output file") var outFile : String = "")

object ProgramTarget extends Enum {
  sealed abstract class Type extends EnumElem
  object Ocaml extends Type
  object Coq extends Type
  object Java extends Type

  def elements = ivector(Ocaml, Coq, Java)
}

@Main(value = "program",
  className = "org.sireum.bakar.tools.BakarProgram",
  featureName = "Sireum Bakar Tools:Gnat.sapp",
  desc = "Translation of Spark/Ada Programs")
case class SireumBakarProgramMode(
  @Option(shortKey = "p", longKey = "program", desc = "") var typ : ProgramTarget.Type = ProgramTarget.Coq,

  @Option(shortKey = "g", longKey = "gnatpath", desc = "path to bin directory of GNAT") var gnatpath : String = "",

  @Arg(index = 0, value = "src-files") var srcFiles : ISeq[String] = ivectorEmpty,

  @OptionalArg(index = 1, value = "Output file") var outFile : String = "")
