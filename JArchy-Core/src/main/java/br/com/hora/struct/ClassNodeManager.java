package br.com.hora.struct;

import java.util.HashMap;

import br.com.hora.enums.TreeNodeType;

public class ClassNodeManager {
	private HashMap<Object, TreeBuilder> classificationInstancesChilds = new HashMap<Object, TreeBuilder>();

	public TreeBuilder getTree(Object instanceJArchyClass) {
		if(classificationInstancesChilds.containsKey(instanceJArchyClass)) {
			return classificationInstancesChilds.get(instanceJArchyClass);
		} else {
			TreeBuilder subtree = new TreeBuilder(TreeNodeType.CLASS, instanceJArchyClass);
			classificationInstancesChilds.put(instanceJArchyClass, subtree);
			return subtree;
		}
	}
}
