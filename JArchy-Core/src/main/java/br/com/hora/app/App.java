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
		
		//testeInicial();
		//testeInicial2();
		rows = getRowsTest1();
		
		TreeBuilder treeBuilder = new TreeBuilder(Block.class);
		
		for(List<String> list : rows) {
			JArchyRow row = new JArchyRow(headers, typos, list);
			treeBuilder.add(row);
		}
		
		@SuppressWarnings("unchecked")
		List<Block> blocks = (List<Block>)(Object) treeBuilder.getTree();
		
		for(Block b : blocks) {
			System.out.println(b.getCode());
		}
	}

	private static List<List<String>> getRowsTest1() {
		List<List<String>> rows = new ArrayList<List<String>>();
		rows.add(Arrays.asList(new String[]{"B1", "EC", "N1", "HI", "30", "2"}));
		rows.add(Arrays.asList(new String[]{"B1", "EC", "N1", "BX", "30", "2"}));
		rows.add(Arrays.asList(new String[]{"B1", "PC", "N1", "BX", "30", "2"}));
		
		rows.add(Arrays.asList(new String[]{"B1", "EP", "N1", "HI", "30", "1"}));
		rows.add(Arrays.asList(new String[]{"B1", "EP", "N1", "HI", "30", "1"}));
		
		rows.add(Arrays.asList(new String[]{"B2", "EC", "N1", "LW", "30", "1"}));
		rows.add(Arrays.asList(new String[]{"B2", "EC", "N1", "AL", "30", "2"}));
		return rows;
	}
	
	/*
	private static void testeInicial2() {
		try {
			Block one = Block.class.newInstance();
			Block two = Block.class.newInstance();
			
			Field codeone = one.getClass().getField("code");
			Field codetwo = two.getClass().getField("code");
			
			System.out.println(codeone == codetwo);
			System.out.println(codeone.equals(codetwo));
			
			System.out.println(Arrays.asList("a","b") == Arrays.asList("a","b"));
			System.out.println(Arrays.asList("a","b").equals(Arrays.asList("a","b")));
			
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private static void testeInicial() {
		Multimap<List<String>, String> map = ArrayListMultimap.create();

		map.put(Arrays.asList("a","b"), "c");
		
		System.out.println(map.containsKey(Arrays.asList("a","b")));
		System.out.println(map.containsKey(Arrays.asList("a","b","c")));
	}
	*/
	
	
}
