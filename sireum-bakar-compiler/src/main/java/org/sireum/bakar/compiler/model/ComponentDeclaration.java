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

public final class ComponentDeclaration extends Node {
  protected ArrayList<IDName> theIDNames;

  protected Name theName;

  public final static int DESCRIPTOR = 22;

  public ComponentDeclaration() {
  }

  @Override
  public final int getDescriptor() {
    return ComponentDeclaration.DESCRIPTOR;
  }

  public final ArrayList<IDName> getTheIDNames() {
    return this.theIDNames;
  }

  public final Name getTheName() {
    return this.theName;
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

  /**
   * <ul>
   * <li>{@code NonNull}</li>
   * </ul>
   */
  public final void setTheName(final Name theName) {
    assert theName != null;
    this.theName = theName;
  }
}
