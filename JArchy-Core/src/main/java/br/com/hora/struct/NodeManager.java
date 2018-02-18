package br.com.hora.struct;

import java.lang.reflect.Field;
import java.util.HashMap;

public class NodeManager {

	private HashMap<Object, ChildrenColumnNodeManager> instanceToChildrenColumnNodeManager = new HashMap<Object, ChildrenColumnNodeManager>();

	public void constructTreeBuilderJArchyColumnInstance(Field field, JArchyRow row, Object rootInstance) {
		if(!this.instanceToChildrenColumnNodeManager.containsKey(rootInstance)) {
			ChildrenColumnNodeManager childrenColumnNodeManager = new ChildrenColumnNodeManager();
			this.instanceToChildrenColumnNodeManager.put(rootInstance, childrenColumnNodeManager);
			childrenColumnNodeManager.constructTreeBuilderJArchyColumnInstance(field, row, rootInstance);
		} else {
			this.instanceToChildrenColumnNodeManager.get(rootInstance).constructTreeBuilderJArchyColumnInstance(field, row, rootInstance);
		}
	}
}
