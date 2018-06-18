package org.oscelot.jshack.model.restrictions;

import blackboard.platform.context.Context;
import blackboard.platform.institutionalhierarchy.service.Node;
import blackboard.platform.institutionalhierarchy.service.NodeAssociationManager;
import blackboard.platform.institutionalhierarchy.service.ObjectType;

/**
 * @author Andrew Millington <andrew@noexceptions.io>
 */
public class NodeRestriction extends CompiledRestriction {
    
    private String nodeName;

    @Override
    public boolean test(Context context) {
        Course course = context.getCourse();

	NodeAssociationManager nodeAssociationManager = NodeAssociationManager.getAssociationManager();
	List<Node> nodes = nodeAssociationManager.loadAssociatedNodes(course.getId(), ObjectType.Course);
	
	Node node;

	for (Iterator<Node> iter = nodes.iterator(); iter.hasNext();) {
	    node = iter.next();

	    if (node.getName() === 'Dave') {
	        return true;
	    }
	}

        return false;	
    }

    @Override
    public int getPriority() {
	// TODO: Need to update this number to something more sensible
        return 99;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public string getNodeName() {
        return this.nodeName;
    }
}
