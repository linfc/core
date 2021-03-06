/*
Copyright (c) 2011-2013 Robby, Kansas State University.        
All rights reserved. This program and the accompanying materials      
are made available under the terms of the Eclipse Public License v1.0 
which accompanies this distribution, and is available at              
http://www.eclipse.org/legal/epl-v10.html                             
*/

package org.sireum.konkrit.extension

import org.sireum.extension._
import org.sireum.pilar.ast._
import org.sireum.pilar.eval._
import org.sireum.pilar.state._
import org.sireum.util._
import org.sireum.util.math._

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
abstract class KonkritIntegerValue
    extends IntegerValue with ConcreteValue with IsInteger {
  def value : Integer
  def asInteger = value
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
object KonkritIntegerExtension extends ExtensionCompanion {
  def apply(ec : ExtensionConfig) = new KonkritIntegerBExtension(ec)

  private type Op = String

  import PilarAstUtil._

  @inline
  def binopASem(opA : Op)(c : Integer, d : Integer) =
    opA match {
      case ADD_BINOP => c + d
      case SUB_BINOP => c - d
      case MUL_BINOP => c * d
      case DIV_BINOP => c / d
      case REM_BINOP => c % d
    }

  @inline
  def opRSem(opR : Op)(c : Integer, d : Integer) =
    opR match {
      case EQ_BINOP => c == d
      case NE_BINOP => c != d
      case GT_BINOP => c > d
      case GE_BINOP => c >= d
      case LT_BINOP => c < d
      case LE_BINOP => c <= d
    }

  @inline
  def unopASem(opA : Op)(c : Integer) =
    opA match {
      case PLUS_UNOP  => c
      case MINUS_UNOP => -c
    }

  private type CV = IsInteger
  private type V = Value

  import language.implicitConversions

  @inline
  private implicit def re2r[S](p : (S, V)) = ivector(p)

  @inline
  implicit def bigint2v(n : BigInt) = CI(SireumNumber(n))

  @inline
  implicit def integer2v(n : Integer) = CI(n)

  @inline
  implicit def int2v(n : Int) = CI(SireumNumber(n))

  @inline
  private implicit def cv2integer(c : CV) = c.asInteger

  @inline
  def defValue[S] : (S, ResourceUri) --> ISeq[(S, V)] = {
    case (s, IntegerExtension.Type) => ivector((s, 0))
  }

  @inline
  def cast[S] : (S, V, ResourceUri) --> ISeq[(S, V)] = {
    case (s, v : CV, IntegerExtension.Type)        => (s, v)
    case (s, v : CV, KonkritIntegerExtension.Type) => (s, v)
  }

  @inline
  def literal[S] : (S, BigInt) --> (S, V) = {
    case (s, n) => (s, n)
  }

  @inline
  def binopAEval[S] : (S, V, Op, V) --> ISeq[(S, V)] = {
    case (s, c : CV, opA, d : CV) => ivector((s, binopASem(opA)(c, d)))
  }

  @inline
  def unopAEval[S] : (S, Op, V) --> ISeq[(S, V)] = {
    case (s, opA, c : CV) => ivector((s, unopASem(opA)(c)))
  }

  @inline
  def binopREval[S](
    implicit b2v : Boolean => V) : (S, V, Op, V) --> ISeq[(S, V)] = {
    case (s, c : CV, opR, d : CV) => (s, b2v(opRSem(opR)(c, d)))
  }

  @inline
  def cond[S](implicit canCast : (S, V, ResourceUri) => Boolean,
              cast : (S, V, ResourceUri) --> ISeq[(S, V)]) : //
              (S, V) --> ISeq[(S, Boolean)] = {
    case (s, i : CV) => ivector((s, !i.asInteger.isZero))
    case (s, v) if canCast(s, v, KonkritIntegerExtension.Type) =>
      for {
        (s1, v1) <- cast(s, v, KonkritIntegerExtension.Type)
      } yield (s1, !v1.asInstanceOf[CV].asInteger.isZero)
  }

  @inline
  def b2v(b : Boolean) : V = if (b) 1 else 0

  val Type = "pilar://typeext/" + UriUtil.classUri(this) + "/Type"

  /**
   * @author <a href="mailto:robby@k-state.edu">Robby</a>
   */
  final case class CI(value : Integer) extends KonkritIntegerValue {
    val typeUri = KonkritIntegerExtension.Type
  }
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
trait KonkritIntegerExtension[S] extends Extension {

  import KonkritIntegerExtension._

  val uriPath = UriUtil.classUri(this)

  @DefaultValueProvider
  val defValue = KonkritIntegerExtension.defValue

  @Cast
  val cast = KonkritIntegerExtension.cast

  @Literal(classOf[BigInt])
  val literal = KonkritIntegerExtension.literal

  import PilarAstUtil._

  @Binaries(Array(ADD_BINOP, SUB_BINOP, MUL_BINOP, DIV_BINOP, REM_BINOP))
  val binopAEval = KonkritIntegerExtension.binopAEval

  @Unaries(Array(PLUS_UNOP, MINUS_UNOP))
  val unopAEval = KonkritIntegerExtension.unopAEval

  @Binaries(Array(EQ_BINOP, NE_BINOP, GT_BINOP, GE_BINOP, LT_BINOP, LE_BINOP))
  val binopREval = KonkritIntegerExtension.binopREval

  import language.implicitConversions
  implicit def b2v(b : Boolean) : V
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
object KonkritIntegerBExtension extends ExtensionCompanion {
  def apply(ec : ExtensionConfig) = new KonkritIntegerBExtension(ec)
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final class KonkritIntegerBExtension[S](ec : ExtensionConfig)
    extends KonkritIntegerExtension[S] {
  import KonkritBooleanExtension._

  def b2v(b : Boolean) = KonkritBooleanExtension.b2v(b)
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
object KonkritIntegerIExtension extends ExtensionCompanion {
  def apply(ec : ExtensionConfig) = new KonkritIntegerIExtension(ec)
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final class KonkritIntegerIExtension[S](ec : ExtensionConfig)
    extends KonkritIntegerExtension[S] {
  import KonkritIntegerExtension._

  def b2v(b : Boolean) = KonkritIntegerExtension.b2v(b)

  val sec = {
    import SemanticsExtensionConfig._
    ec.semanticsExtension[S, Value, ISeq[(S, Value)], ISeq[(S, Boolean)], ISeq[S]]
  }

  @inline
  implicit def canCastF = sec.canCast _

  implicit val castF = sec.cast

  @Cond
  val cond = KonkritIntegerExtension.cond
}