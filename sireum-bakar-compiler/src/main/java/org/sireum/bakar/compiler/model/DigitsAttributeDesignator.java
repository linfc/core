/******************************************************************************
 * Copyright (c) 2009 Robby, Kansas State University, and others.             *
 * All rights reserved. This program and the accompanying materials           *
 * are made available under the terms of the Eclipse Public License v1.0      *
 * which accompanies this distribution, and is available at                   *
 * http://www.eclipse.org/legal/epl-v10.html                                  *
 ******************************************************************************/
package org.sireum.bakar.compiler.model;

public final class DigitsAttributeDesignator extends AttributeDesignator {

  public final static int DESCRIPTOR = 44;

  public DigitsAttributeDesignator() {
  }

  @Override
  public final int getDescriptor() {
    return DigitsAttributeDesignator.DESCRIPTOR;
  }
}
