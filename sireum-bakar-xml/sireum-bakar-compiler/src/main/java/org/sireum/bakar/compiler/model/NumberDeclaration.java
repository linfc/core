/******************************************************************************
 * Copyright (c) 2009 Robby, Kansas State University, and others.             *
 * All rights reserved. This program and the accompanying materials           *
 * are made available under the terms of the Eclipse Public License v1.0      *
 * which accompanies this distribution, and is available at                   *
 * http://www.eclipse.org/legal/epl-v10.html                                  *
 ******************************************************************************/
package org.sireum.bakar.compiler.model;

import java.util.ArrayList;

import org.sireum.bakar.util.Util;

public final class NumberDeclaration extends BasicDeclaration {
  protected ArrayList<IDName> theIDNames;

  protected Exp theExp;

  public final static int DESCRIPTOR = 9;

  public NumberDeclaration() {
  }

  @Override
  public final int getDescriptor() {
    return NumberDeclaration.DESCRIPTOR;
  }

  public final Exp getTheExp() {
    return this.theExp;
  }

  public final ArrayList<IDName> getTheIDNames() {
    return this.theIDNames;
  }

  /**
   * <ul>
   * <li>{@code NonNull}</li>
   * </ul>
   */
  public final void setTheExp(final Exp theExp) {
    assert theExp != null;
    this.theExp = theExp;
  }

  /**
   * <ul>
   * <li>{@code NonNull}</li>
   * <li>{@code NonNullElements}</li>
   * </ul>
   */
  public final void setTheIDNames(final ArrayList<IDName> theIDNames) {
    assert theIDNames != null;

    assert Util.nonNullElements(theIDNames);
    this.theIDNames = theIDNames;
  }
}
