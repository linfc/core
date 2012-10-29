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

public final class GenericInstantiation extends DeclarativeItem {
  protected String theIDString;

  protected Name theName;

  protected ArrayList<GenericAssociation> theOptionalGenericAssociations;

  public final static int DESCRIPTOR = 139;

  public GenericInstantiation() {
  }

  @Override
  public final int getDescriptor() {
    return GenericInstantiation.DESCRIPTOR;
  }

  public final String getTheIDString() {
    return this.theIDString;
  }

  public final Name getTheName() {
    return this.theName;
  }

  public final ArrayList<GenericAssociation> getTheOptionalGenericAssociations() {
    return this.theOptionalGenericAssociations;
  }

  /**
   * <ul>
   * <li>{@code NonNull}</li>
   * </ul>
   */
  public final void setTheIDString(final String theIDString) {
    assert theIDString != null;
    if (theIDString != null) {
      this.theIDString = theIDString.intern();
    } else {
      this.theIDString = null;
    }
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

  /**
   * <ul>
   * <li>{@code NonNullElements}</li>
   * </ul>
   */
  public final void setTheOptionalGenericAssociations(
      final ArrayList<GenericAssociation> theOptionalGenericAssociations) {
    assert Util.nonNullElements(theOptionalGenericAssociations);
    this.theOptionalGenericAssociations = theOptionalGenericAssociations;
  }
}
