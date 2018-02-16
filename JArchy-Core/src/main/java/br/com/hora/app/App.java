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
		List<List<String>> rows = new ArrayList<List<String>>();
		
		List<String> headers = Arrays.asList(new String[] {"block", "product", "accessibility", "quality", "expiration", "amount"});
		List<Class<?>> typos = Arrays.asList(new Class<?>[] {String.class, String.class, String.class, String.class, Integer.class, Integer.class});
		
		rows = getRowsTest1();
		
		TreeBuilder<Block> treeBuilder = new TreeBuilder<Block>(Block.class);
		
		for(List<String> list : rows) {
			JArchyRow row = new JArchyRow(headers, typos, list);
			treeBuilder.add(row);
		}
		
		for(Block b : treeBuilder.getTree()) {
			System.out.println(b.getCode());
		}
		//List<Block> roots = treeBuilder.getRoots();
		
	}

	private static List<List<String>> getRowsTest1() {
		List<List<String>> rows = new ArrayList<List<String>>();
		rows.add(Arrays.asList(new String[]{"B1", "EC", "N1", "HI", "30", "1"}));
		rows.add(Arrays.asList(new String[]{"B1", "PP", "N1", "AL", "10", "2"}));
		rows.add(Arrays.asList(new String[]{"B1", "PP", "N2", "AL", "20", "3"}));
		rows.add(Arrays.asList(new String[]{"B2", "EC", "N1", "LW", "30", "1"}));
		//rows.add(Arrays.asList(new String[]{"B2", "EC", "N1", "AL", "30", "2"}));
		return rows;
	}
	
	
}
