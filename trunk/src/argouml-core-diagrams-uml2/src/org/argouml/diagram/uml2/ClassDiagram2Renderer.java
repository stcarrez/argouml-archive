// $Id: ClassDiagramRenderer.java 17112 2009-04-13 19:02:54Z tfmorris $
// Copyright (c) 1996-2008 The Regents of the University of California. All
// Rights Reserved. Permission to use, copy, modify, and distribute this
// software and its documentation without fee, and without a written
// agreement is hereby granted, provided that the above copyright notice
// and this paragraph appear in all copies.  This software program and
// documentation are copyrighted by The Regents of the University of
// California. The software program and documentation are supplied "AS
// IS", without any accompanying services from The Regents. The Regents
// does not warrant that the operation of the program will be
// uninterrupted or error-free. The end-user understands that the program
// was developed for research purposes and is advised not to rely
// exclusively on the program for any reason.  IN NO EVENT SHALL THE
// UNIVERSITY OF CALIFORNIA BE LIABLE TO ANY PARTY FOR DIRECT, INDIRECT,
// SPECIAL, INCIDENTAL, OR CONSEQUENTIAL DAMAGES, INCLUDING LOST PROFITS,
// ARISING OUT OF THE USE OF THIS SOFTWARE AND ITS DOCUMENTATION, EVEN IF
// THE UNIVERSITY OF CALIFORNIA HAS BEEN ADVISED OF THE POSSIBILITY OF
// SUCH DAMAGE. THE UNIVERSITY OF CALIFORNIA SPECIFICALLY DISCLAIMS ANY
// WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
// MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE. THE SOFTWARE
// PROVIDED HEREUNDER IS ON AN "AS IS" BASIS, AND THE UNIVERSITY OF
// CALIFORNIA HAS NO OBLIGATIONS TO PROVIDE MAINTENANCE, SUPPORT,
// UPDATES, ENHANCEMENTS, OR MODIFICATIONS.

package org.argouml.diagram.uml2;

/**
 * This class defines a renderer object for UML Class Diagrams. In a
 * Class Diagram the following UML objects are displayed with the
 * following Figs: <p>
 *
 * <pre>
 *  UML Object       ---  Fig
 *  ---------------------------------------
 *  Class            ---  FigClass
 *  Interface        ---  FigInterface
 *  Instance         ---  FigInstance
 *  Model            ---  FigModel
 *  Subsystem        ---  FigSubsystem
 *  Package          ---  FigPackage
 *  Comment          ---  FigComment
 *  (CommentEdge)    ---  FigEdgeNote
 *  Generalization   ---  FigGeneralization
 *  Realization      ---  FigRealization
 *  Permission       ---  FigPermission
 *  Usage            ---  FigUsage
 *  Dependency       ---  FigDependency
 *  Association      ---  FigAssociation
 *  AssociationClass ---  FigAssociationClass
 *  Dependency       ---  FigDependency
 *  Link             ---  FigLink
 *  DataType         ---  FigDataType
 *  Stereotype       ---  FigStereotypeDeclaration
 * </pre>
 *
 * @author jrobbins
 */
public class ClassDiagram2Renderer extends StructureDiagram2Renderer {
    
}
