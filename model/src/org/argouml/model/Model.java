// $Id$
// Copyright (c) 2004-2005 The Regents of the University of California. All
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

package org.argouml.model;

import java.awt.Container;
import java.io.Writer;

import org.apache.log4j.Logger;


/**
 * This is the root class of the Model subsystem. All other subsystems
 * can retreive the correct version of the API from this class.<p>
 *
 * Notice that all API's returned from this class are to be independant
 * of and specific UML model implementation.<p>
 *
 * @stereotype utility
 * @since 0.15.5
 * @author Linus Tolke
 */
public final class Model {
    /**
     * Logger.
     */
    private static final Logger LOG = Logger.getLogger(Model.class);
    
    private static ActivityGraphsHelperProxy activityGraphsHelperProxy;
    private static CollaborationsHelperProxy collaborationsHelperProxy;
    private static CommonBehaviorHelperProxy commonBehaviorHelperProxy;
    private static CoreHelperProxy coreHelperProxy;
    private static DataTypesHelperProxy dataTypesHelperProxy;
    private static ExtensionMechanismsHelperProxy extensionMechanismsHelperProxy;
    private static StateMachinesHelperProxy stateMachinesHelperProxy;
    private static UmlHelperProxy umlHelperProxy;
    private static UseCasesHelperProxy useCasesHelperProxy;

    /**
     * Constructor to prohibit creation.
     */
    private Model() {
    }

    /**
     * The object used to get objects of the implementation.
     */
    private static ModelImplementation impl;

    static {
        String className =
            System.getProperty(
                    "argouml.model.implementation",
            	    "org.argouml.model.uml.DefaultModelImplementation");

        try {
            Class implType = Class.forName(className);
            impl = (ModelImplementation) implType.newInstance();
        } catch (ClassNotFoundException e) {
            reportError(e);
        } catch (InstantiationException e) {
            reportError(e);
        } catch (IllegalAccessException e) {
            reportError(e);
        }
        activityGraphsHelperProxy = new ActivityGraphsHelperProxy(impl.getActivityGraphsHelper());
        collaborationsHelperProxy = new CollaborationsHelperProxy(impl.getCollaborationsHelper());
        commonBehaviorHelperProxy = new CommonBehaviorHelperProxy(impl.getCommonBehaviorHelper());
        coreHelperProxy = new CoreHelperProxy(impl.getCoreHelper());
        dataTypesHelperProxy = new DataTypesHelperProxy(impl.getDataTypesHelper());
        extensionMechanismsHelperProxy = new ExtensionMechanismsHelperProxy(impl.getExtensionMechanismsHelper());
        stateMachinesHelperProxy = new StateMachinesHelperProxy(impl.getStateMachinesHelper());
        umlHelperProxy = new UmlHelperProxy(impl.getUmlHelper());
        useCasesHelperProxy = new UseCasesHelperProxy(impl.getUseCasesHelper());
    }

    /**
     * @param e The exception to be logged.
     */
    private static void reportError(Exception e) {
        LOG.fatal("Model component not correctly initiated.", e);
    }

    /**
     * Selects the implementation.<p>
     *
     * This is used for testing purposes only when a fake implementation
     * is used. Normally this is set when loading the Model subsystem.
     *
     * @param newImpl The ModelImplementation object of the selected
     * 		      implementation.
     */
    static void setImplementation(ModelImplementation newImpl) {
        impl = newImpl;
    }

    /**
     * Get the facade.<p>
     *
     * The facade is probably the most used interface. It contains recognizers
     * and getters for all method kinds.
     *
     * @return The facade object.
     */
    public static Facade getFacade() {
        return impl.getFacade();
    }

    /**
     * Get the event pump.
     *
     * @return the current ModelEventPump.
     */
    public static ModelEventPump getPump() {
        return impl.getModelEventPump();
    }

    /**
     * Getter for ActivityGraphsFactory.
     *
     * @return the factory
     */
    public static ActivityGraphsFactory getActivityGraphsFactory() {
        return impl.getActivityGraphsFactory();
    }

    /**
     * Getter for the ActivityGraphsHelper.
     *
     * @return the instance of the helper
     */
    public static ActivityGraphsHelper getActivityGraphsHelper() {
        return activityGraphsHelperProxy;
    }

    /**
     * Getter for CollaborationsFactory.
     *
     * @return the factory
     */
    public static CollaborationsFactory getCollaborationsFactory() {
        return impl.getCollaborationsFactory();
    }

    /**
     * Getter for CollaborationsHelper.
     *
     * @return the helper
     */
    public static CollaborationsHelper getCollaborationsHelper() {
        return collaborationsHelperProxy;
    }

    /**
     * Getter for CommonBehaviorFactory.
     *
     * @return the factory
     */
    public static CommonBehaviorFactory getCommonBehaviorFactory() {
        return impl.getCommonBehaviorFactory();
    }

    /**
     * Getter for CommonBehaviorHelper.
     *
     * @return the helper
     */
    public static CommonBehaviorHelper getCommonBehaviorHelper() {
        return commonBehaviorHelperProxy;
    }

    /**
     * Getter for CoreFactory.
     *
     * @return the factory
     */
    public static CoreFactory getCoreFactory() {
        return impl.getCoreFactory();
    }

    /**
     * Getter for CoreHelper.
     *
     * @return The helper.
     */
    public static CoreHelper getCoreHelper() {
        return coreHelperProxy;
    }

    /**
     * Getter for DataTypesFactory.
     *
     * @return the factory
     */
    public static DataTypesFactory getDataTypesFactory() {
        return impl.getDataTypesFactory();
    }

    /**
     * Getter for DataTypesHelper.
     *
     * @return the helper.
     */
    public static DataTypesHelper getDataTypesHelper() {
        return dataTypesHelperProxy;
    }

    /**
     * Getter for ExtensionMechanismsFactory.
     *
     * @return the factory instance.
     */
    public static ExtensionMechanismsFactory getExtensionMechanismsFactory() {
        return impl.getExtensionMechanismsFactory();
    }

    /**
     * Getter for ExtensionMechanismsHelper.
     *
     * @return the helper
     */
    public static ExtensionMechanismsHelper getExtensionMechanismsHelper() {
        return extensionMechanismsHelperProxy;
    }

    /**
     * Getter for EventAdapter.
     *
     * @return the event adapter
     */
    public static EventAdapter getEventAdapter() {
        return impl.getEventAdapter();
    }

    /**
     * Getter for ModelManagementFactory.
     *
     * @return the factory
     */
    public static ModelManagementFactory getModelManagementFactory() {
        return impl.getModelManagementFactory();
    }

    /**
     * Getter for ModelManagementHelper.
     *
     * @return The model management helper.
     */
    public static ModelManagementHelper getModelManagementHelper() {
        return impl.getModelManagementHelper();
    }

    /**
     * Getter for StateMachinesFactory.
     *
     * @return the factory
     */
    public static StateMachinesFactory getStateMachinesFactory() {
        return impl.getStateMachinesFactory();
    }

    /**
     * Getter for StateMachinesHelper.
     *
     * @return the helper
     */
    public static StateMachinesHelper getStateMachinesHelper() {
        return stateMachinesHelperProxy;
    }

    /**
     * Getter for UmlFactory.
     *
     * @return the factory
     */
    public static UmlFactory getUmlFactory() {
        return impl.getUmlFactory();
    }

    /**
     * Getter for UmlHelper.
     *
     * @return the helper
     */
    public static UmlHelper getUmlHelper() {
        return umlHelperProxy;
    }

    /**
     * Getter for UseCasesFactory.
     *
     * @return the factory
     */
    public static UseCasesFactory getUseCasesFactory() {
        return impl.getUseCasesFactory();
    }

    /**
     * Getter for UseCasesHelper.
     *
     * @return the helper
     */
    public static UseCasesHelper getUseCasesHelper() {
        return useCasesHelperProxy;
    }

    /**
     * Getter for the MetaTypes object.
     *
     * @return the MetaTypes object.
     */
    public static MetaTypes getMetaTypes() {
        return impl.getMetaTypes();
    }

    // Here follows the interfaces that contain the enums of different
    // kinds in the UML meta-model.
    /**
     * Getter for the ChangeableKind object.
     *
     * @return The object implementing the interface.
     */
    public static ChangeableKind getChangeableKind() {
        return impl.getChangeableKind();
    }

    /**
     * Getter for the AggregationKind object.
     *
     * @return The object implementing the interface.
     */
    public static AggregationKind getAggregationKind() {
        return impl.getAggregationKind();
    }

    /**
     * Getter for the PseudostateKind object.
     *
     * @return The object implementing the interface.
     */
    public static PseudostateKind getPseudostateKind() {
        return impl.getPseudostateKind();
    }

    /**
     * Getter for the ScopeKind object.
     *
     * @return The object implementing the interface.
     */
    public static ScopeKind getScopeKind() {
        return impl.getScopeKind();
    }

    /**
     * Getter for the ConcurrencyKind object.
     *
     * @return The object implementing the interface.
     */
    public static ConcurrencyKind getConcurrencyKind() {
        return impl.getConcurrencyKind();
    }

    /**
     * Getter for the DirectionKind object.
     *
     * @return The object implementing the interface.
     */
    public static DirectionKind getDirectionKind() {
        return impl.getDirectionKind();
    }

    /**
     * Getter for the Multiplicities object.
     *
     * @return The object implementing the interface.
     */
    public static Multiplicities getMultiplicities() {
        return impl.getMultiplicities();
    }

    /**
     * Getter for the OrderingKind object.
     *
     * @return The object implementing the interface.
     */
    public static OrderingKind getOrderingKind() {
        return impl.getOrderingKind();
    }

    /**
     * Getter for the VisibilityKind object.
     *
     * @return The object implementing the interface.
     */
    public static VisibilityKind getVisibilityKind() {
        return impl.getVisibilityKind();
    }

    /**
     * Getter for the XmiReader object.
     *
     * @return the object implementing the XmiReader interface
     * @throws UmlException on any error while reading
     */
    public static XmiReader getXmiReader() throws UmlException {
        return impl.getXmiReader();
    }

    /**
     * Getter for the XmiWriter object.
     *
     * @param model the project member model
     * @param writer the writer
     * @return the object implementing the XmiWriter interface
     * @throws UmlException on any error while writing
     */
    public static XmiWriter getXmiWriter(Object model, Writer writer)
        throws UmlException {
        return impl.getXmiWriter(model, writer);
    }

    /**
     * @return <code>true</code> if the Model subsystem is correctly initiated.
     */
    public static boolean isInitiated() {
        return impl != null;
    }


    /**
     * Create a new ComponentDispatcher for a Component.
     *
     * @param container The Component the dispatcher should be registered to.
     * @return A newly created Container Dispatcher.
     */
    public static ContainerDispatcher createContainerDispatcher(
            Container container) {
        return impl.createContainerDispatcher(container);
    }
    
    /**
     * Allows an external system to register itself to recieve mementos created
     * by the model subsystem
     * @param observer the interested party
     */
    public static void setMementoCreationObserver(
            MementoCreationObserver observer) {
        impl.setMementoCreationObserver(observer);
    }
    
    /**
     * Gets the external class responsible for handling mementos.
     * @return the MementoCreationObserver
     */
    public static MementoCreationObserver getMementoCreationObserver() {
        return impl.getMementoCreationObserver();
    }
    
    /**
     * Notify the 
     * @return the MementoCreationObserver
     */
    public static ModelMemento notifyMementoCreationObserver(
            ModelMemento memento) {
        MementoCreationObserver mco = getMementoCreationObserver();
        if (mco != null) {
            Model.getMementoCreationObserver().mementoCreated(memento);
        }
        return memento;
    }
}
