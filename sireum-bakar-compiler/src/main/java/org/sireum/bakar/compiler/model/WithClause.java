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

public final class WithClause extends ContextItem {
  protected ArrayList<Name> theNames;

  public final static int DESCRIPTOR = 135;

  public WithClause() {
  }

  @Override
  public final int getDescriptor() {
    return WithClause.DESCRIPTOR;
  }

  public final ArrayList<Name> getTheNames() {
    return this.theNames;
  }

  /**
   * <ul>
   * <li>{@code NonNull}</li>
   * <li>{@code NonNullElements}</li>
   * </ul>
   */
  public final void setTheNames(final ArrayList<Name> theNames) {
    assert theNames != null;

    assert Util.nonNullElements(theNames);
    this.theNames = theNames;
  }
}
