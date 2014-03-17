/*
Copyright (c) 2011-2013 Robby, Kansas State University.        
All rights reserved. This program and the accompanying materials      
are made available under the terms of the Eclipse Public License v1.0 
which accompanies this distribution, and is available at              
http://www.eclipse.org/legal/epl-v10.html                             
*/

package org.sireum.example

import org.sireum.util._

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
object Examples {
  val PILAR_FILE_EXT = ".plr"

  def exampleFiles(dirUri : FileResourceUri,
                   ext : String = Examples.PILAR_FILE_EXT) : ISeq[FileResourceUri] =
    FileUtil.listFiles(dirUri, ext, true)

  def sourceDirUri(claz : Class[_], path : String) = {
    FileUtil.fileUri(claz, path)
  }
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
trait Examples {
  def sourceDirUri(claz : Class[_], path : String) =
    Examples.sourceDirUri(claz, path)

  def exampleFiles(dirUri : FileResourceUri,
                   ext : String = Examples.PILAR_FILE_EXT) : ISeq[FileResourceUri] =
    Examples.exampleFiles(dirUri, ext)
}