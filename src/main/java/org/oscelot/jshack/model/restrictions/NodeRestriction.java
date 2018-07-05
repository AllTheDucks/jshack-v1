package org.oscelot.jshack.model.restrictions;

import blackboard.data.course.Course;
import blackboard.persist.PersistenceException;
import blackboard.platform.context.Context;
import blackboard.platform.institutionalhierarchy.service.Node;
import blackboard.platform.institutionalhierarchy.service.NodeAssociationManager;
import blackboard.platform.institutionalhierarchy.service.NodeManagerFactory;
import blackboard.platform.institutionalhierarchy.service.ObjectType;
import java.util.Iterator;
import java.util.List;

/**
 * @author Andrew Millington <andrew@noexceptions.io>
 */
public class NodeRestriction extends CompiledRestriction {

    private String nodeId;

    @Override
    public boolean test(Context context) {
        Course course = context.getCourse();

        NodeAssociationManager nodeAssociationManager = NodeManagerFactory.getAssociationManager();

        try {
            List<Node> nodes = nodeAssociationManager.loadAssociatedNodes(course.getId(), ObjectType.Course);

            Node node;

            for (Iterator<Node> iter = nodes.iterator(); iter.hasNext();) {
                node = iter.next();

                if (node.getIdentifier().equals(this.nodeId)) {
                    return true;
                }
            }
        } catch (PersistenceException ex) {
            return false;
        }

        return false;
    }

    @Override
    public int getPriority() {
	// TODO: Need to update this number to something more sensible
        return 99;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    private String getNodeId() {
        return this.nodeId;
    }
}
