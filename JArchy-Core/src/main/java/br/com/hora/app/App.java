package br.com.hora.app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.hora.model.Block;
import br.com.hora.struct.JArchyRow;
import br.com.hora.struct.TreeBuilder;

/**
 * Limitations:
 * - Work only with Strings, Integers or Double yet. Needs to improve a lot.
 * - Does not validate data yet
 * - Does not validate duplicate cell
 * - 
 * @author Ygor Hora
 */
public class App {
	public static void main(String[] args) {
		ArrayList<List<Object>> rows = new ArrayList<List<Object>>();
		
		List<String> headers = Arrays.asList(new String[] {"block", "product", "accessibility", "quality", "expiration", "amount"});
		
		rows.add(Arrays.asList(new Object[]{"B1", "EC", "N1", "HI", 30, 1}));
		rows.add(Arrays.asList(new Object[]{"B1", "PP", "N1", "AL", 10, 2}));
		rows.add(Arrays.asList(new Object[]{"B1", "PP", "N2", "AL", 20, 3}));
		rows.add(Arrays.asList(new Object[]{"B2", "EC", "N1", "LW", 30, 1}));
		rows.add(Arrays.asList(new Object[]{"B2", "EC", "N1", "AL", 30, 2}));		
		
		TreeBuilder treeBuilder = new TreeBuilder(Block.class);
		
		for(List<Object> list : rows) {
			JArchyRow row = new JArchyRow(headers, list);
			treeBuilder.add(row);
		}
		
		//List<Block> roots = treeBuilder.getRoots();
		
	}
	
	
}
