package sorter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.stream.Stream;

public class Analyse {
	static int i=0;
	static SimpleDateFormat simp = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
	static String dat = null;

	static Path path2 = Paths.get("C:\\Users\\flypa\\Desktop\\Wowza-logs\\Nov_1.txt");
	static Path path3 = Paths.get("C:\\Users\\flypa\\Desktop\\Wowza-logs\\Nov_2.txt");
	static Path path4 = Paths.get("C:\\Users\\flypa\\Desktop\\Wowza-logs\\Nov_3.txt");
	static Path path1 = Paths.get("C:\\Users\\flypa\\Desktop\\Wowza-logs\\ErrorInRange.txt");
	
	static List<String> list1 = new ArrayList<>();
	static List<String> list2 = new ArrayList<>();
	static List<String> list3 = new ArrayList<>();
	static List<String> list4 = new ArrayList<>();
	
	static List<String> allocatedIDs = new ArrayList<>();
	static List<String> allocated = new ArrayList<>();
	
	static Map<Date, List<String>> map = new TreeMap<>();
	
	Analyse(String file) throws IOException{
		
		//"C:\\Users\\flypa\\Desktop\\Wowza-logs\\wowzastreamingengine-access.log.2022-11-01"
		try (Stream<String> lines = Files.lines(Paths.get(file))) {
			lines.forEach(l -> {

				String[] split = l.split("	");
			     
			     try {
			    	if(split.length>20) {
				    	dat = split[0].trim()+" "+split[1].trim();
				    	
						Date lowerBound = simp.parse("2022-11-01 08:00:00");
						Date upperBound = simp.parse("2022-11-01 23:59:59");
						Date date = simp.parse(dat);
						
						if(date.after(lowerBound) && date.before(upperBound)) {
							List<String> list = new ArrayList<>();
							for(int r=0; r<split.length; r++) {
								list.add(split[r]);
							}
							map.put(date, list);
							
							if(split[5].trim().equals("ERROR"))list1.add(l);
						}
			    	}
			    	}catch (ArrayIndexOutOfBoundsException p) {
						p.printStackTrace();
					} catch (ParseException e) {
						//e.printStackTrace();
					}
			
			});
		}
	}
	
	Object[][] objects(){
		Object[][] tableAllocated;
		if(map.size()>0) {
			//get column size
			List<String> items = (List<String>) map.get(((String)allocatedIDs.get(0)));
			//System.out.println("items:"+items);
			tableAllocated = new Object[allocated.size()][items.size()];
			System.out.println("allocated size:"+allocated.size());
			Iterator<Entry<String, Object>> entries = allocated.entrySet().iterator();
			int row = 0;
			while(entries.hasNext()) {
				int column = 0;
				Entry<String, Object> entry = entries.next();
				
				List<Object> item = (List<Object>) entry.getValue();
				//System.out.println(row+ " -> "+item);
				for(Object value : item) {
					tableAllocated[row][column] = value;
					//System.out.println("["+row+"]"+"["+column+"] = "+value);
					column++;
				}
				row++; 
			}
		}
		
		return tableAllocated;
		
	}
	
	
	
	public static void main(String[] args) throws IOException, ParseException {
		
		Map<Date, List<String>> map = new TreeMap<>();
	
		try (Stream<String> lines = Files.lines(Paths.get("C:\\Users\\flypa\\Desktop\\Wowza-logs\\wowzastreamingengine-access.log.2022-11-01"))) {
			lines.forEach(l -> {

				String[] split = l.split("	");
			     
			     try {
			    	if(split.length>20) {
				    	dat = split[0].trim()+" "+split[1].trim();
				    	
						Date lowerBound = simp.parse("2022-11-01 08:00:00");
						Date upperBound = simp.parse("2022-11-01 23:59:59");
						Date date = simp.parse(dat);
						
						if(date.after(lowerBound) && date.before(upperBound)) {
							List<String> list = new ArrayList<>();
							for(int r=0; r<split.length; r++) {
								list.add(split[r]);
							}
							map.put(date, list);
							
							if(split[5].trim().equals("ERROR"))list1.add(l);
						}
						
						Date lowerBound2 = simp.parse("2022-11-01 08:00:00");
						Date upperBound2 = simp.parse("2022-11-01 11:00:00");
						if(date.after(lowerBound2) && date.before(upperBound2)) {
							List<String> list = new ArrayList<>();
							for(int r=0; r<split.length; r++) {
								list.add(split[r]);
							}
							map.put(date, list);
							list2.add(l);
						}
						
						Date lowerBound3 = simp.parse("2022-11-02 08:00:00");
						Date upperBound3 = simp.parse("2022-11-02 11:00:00");
						if(date.after(lowerBound3) && date.before(upperBound3)) {
							List<String> list = new ArrayList<>();
							for(int r=0; r<split.length; r++) {
								list.add(split[r]);
							}
							map.put(date, list);
							list3.add(l);
						}
						
						Date lowerBound4 = simp.parse("2022-11-03 08:00:00");
						Date upperBound4 = simp.parse("2022-11-03 11:00:00");
						
						if(date.after(lowerBound4) && date.before(upperBound4)) {
							List<String> list = new ArrayList<>();
							for(int r=0; r<split.length; r++) {
								list.add(split[r]);
							}
							map.put(date, list);
							list4.add(l);
						}
			    	}
				}catch (ArrayIndexOutOfBoundsException p) {
					p.printStackTrace();
				} catch (ParseException e) {
					//e.printStackTrace();
				}
			});
		}
		
		if(list1.size()>0)Files.write(path1, list1);
		//if(list2.size()>0)Files.write(path2, list2);
		//if(list3.size()>0)Files.write(path3, list3);
		//if(list4.size()>0)Files.write(path4, list4);
		
		
		
		Date lowerBoundt = simp.parse("2022-10-31 08:00:01");
		Date upperBoundt = simp.parse("2022-11-01 10:59:59");
			System.out.println("map size: "+map.size());
			System.out.println("map: "+map.get(lowerBoundt));
			System.out.println("map: "+map.get(upperBoundt));
		
	}

}
