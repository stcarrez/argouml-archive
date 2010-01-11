/* $Id$
 *****************************************************************************
 * Copyright (c) 2009 Contributors - see below
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    tfmorris
 *****************************************************************************
 *
 * Some portions of this file was previously release using the BSD License:
 */

// Copyright (c) 2007,2008 Tom Morris and other contributors
// All rights reserved.
//
// Redistribution and use in source and binary forms, with or without
// modification, are permitted provided that the following conditions are met:
//     * Redistributions of source code must retain the above copyright
//       notice, this list of conditions and the following disclaimer.
//     * Redistributions in binary form must reproduce the above copyright
//       notice, this list of conditions and the following disclaimer in the
//       documentation and/or other materials provided with the distribution.
//     * Neither the name of the project or its contributors may be used 
//       to endorse or promote products derived from this software without
//       specific prior written permission.
//
// THIS SOFTWARE IS PROVIDED BY THE CONTRIBUTORS ``AS IS'' AND ANY
// EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
// WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
// DISCLAIMED. IN NO EVENT SHALL THE CONTRIBUTORS BE LIABLE FOR ANY
// DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
// (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
// LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
// ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
// (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
// SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

package org.argouml.model.euml;

import java.util.Collection;

import org.argouml.model.CollaborationsHelper;

/**
 * Eclipse UML2 implementation of CollaborationsHelper.
 */
class CollaborationsHelperEUMLImpl implements CollaborationsHelper {

    /**
     * The model implementation.
     */
    private EUMLModelImplementation modelImpl;

    /**
     * Constructor.
     *
     * @param implementation The ModelImplementation.
     */
    public CollaborationsHelperEUMLImpl(EUMLModelImplementation implementation) {
        modelImpl = implementation;
    }

    public void addBase(Object arole, Object abase) {
        // TODO: Auto-generated method stub
        
    }

    public void addConstrainingElement(Object handle, Object constraint) {
        // TODO: Auto-generated method stub
        
    }

    public void addInstance(Object classifierRole, Object instance) {
        // TODO: Auto-generated method stub
        
    }

    public void addMessage(Object handle, Object elem) {
        // TODO: Auto-generated method stub
        
    }

    public void addPredecessor(Object handle, Object predecessor) {
        // TODO: Auto-generated method stub
        
    }

    public void addSuccessor(Object handle, Object mess) {
        // TODO: Auto-generated method stub
        
    }

    public Collection allAvailableContents(Object arole) {
        // TODO: Auto-generated method stub
        return null;
    }

    public Collection allAvailableFeatures(Object arole) {
        // TODO: Auto-generated method stub
        return null;
    }

    public Collection getAllClassifierRoles(Object ns) {
        // TODO: Auto-generated method stub
        return null;
    }

    public Collection getAllPossibleActivators(Object ames) {
        // TODO: Auto-generated method stub
        return null;
    }

    public Collection getAllPossibleAssociationRoles(Object role) {
        // TODO: Auto-generated method stub
        return null;
    }

    public Collection getAllPossibleBases(Object role) {
        // TODO: Auto-generated method stub
        return null;
    }

    public Collection getAllPossiblePredecessors(Object amessage) {
        // TODO: Auto-generated method stub
        return null;
    }

    public Object getAssociationRole(Object afrom, Object ato) {
        // TODO: Auto-generated method stub
        return null;
    }

    public Collection getClassifierRoles(Object role) {
        // TODO: Auto-generated method stub
        return null;
    }

    public boolean hasAsActivator(Object message, Object activator) {
        // TODO: Auto-generated method stub
        return false;
    }

    public boolean isAddingCollaborationAllowed(Object context) {
        // TODO: Auto-generated method stub
        return false;
    }

    public void removeBase(Object handle, Object c) {
        // TODO: Auto-generated method stub
        
    }

    public void removeConstrainingElement(Object handle, Object constraint) {
        // TODO: Auto-generated method stub
        
    }

    public void removeInteraction(Object collab, Object interaction) {
        // TODO: Auto-generated method stub
        
    }

    public void removeMessage(Object handle, Object message) {
        // TODO: Auto-generated method stub
        
    }

    public void removePredecessor(Object handle, Object message) {
        // TODO: Auto-generated method stub
        
    }

    public void removeSuccessor(Object handle, Object mess) {
        // TODO: Auto-generated method stub
        
    }

    public void setAction(Object handle, Object action) {
        // TODO: Auto-generated method stub
        
    }

    public void setActivator(Object ames, Object anactivator) {
        // TODO: Auto-generated method stub
        
    }

    public void setBase(Object arole, Object abase) {
        // TODO: Auto-generated method stub
        
    }

    public void setBases(Object role, Collection bases) {
        // TODO: Auto-generated method stub
        
    }

    public void setContext(Object handle, Object col) {
        // TODO: Auto-generated method stub
        
    }

    public void setPredecessors(Object handle, Collection predecessors) {
        // TODO: Auto-generated method stub
        
    }

    public void setRepresentedClassifier(Object handle, Object classifier) {
        // TODO: Auto-generated method stub
        
    }

    public void setRepresentedOperation(Object handle, Object operation) {
        // TODO: Auto-generated method stub
        
    }

    public void setSender(Object handle, Object sender) {
        // TODO: Auto-generated method stub
        
    }

    public void setSuccessors(Object handle, Collection messages) {
        // TODO: Auto-generated method stub
        
    }


}
