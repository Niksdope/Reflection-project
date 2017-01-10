package ie.gmit.sw;

import java.lang.reflect.*;
import java.util.Map;

public class Processor {
	
	private Map<String, Metric> classMap;
	
	public Processor(Map <String, Metric> classMap){
		this.classMap = classMap;
	}
	
	public void process(Class<?> cls, Metric m) throws ClassNotFoundException{
		processInterfaces(cls, m);
		processFields(cls, m);
		processConstructors(cls, m);
		processMethods(cls, m);
	}
	
	public void processMethods(Class<?> cls, Metric m){
		Method metList[] = cls.getDeclaredMethods();
		
		for (Method met : metList){
			Class<?> params[] = met.getParameterTypes();
			Class<?> exceps[] = met.getExceptionTypes();
			
			for (Class<?> p : params){
				if (p.getName().startsWith("java") || p.getName().startsWith("[Ljava") || p.isPrimitive()){
					continue;
				}else{
					m.outIncrement();
					
					if(classMap.containsKey(p.getName())){
						classMap.get(p.getName()).inIncrement();
					}
				}
			}
			for (Class<?> e : exceps){
				if (e.getName().startsWith("java") || e.getName().startsWith("[Ljava") || e.isPrimitive()){
					continue;
				}else{
					m.outIncrement();
					
					if(classMap.containsKey(e.getName())){
						classMap.get(e.getName()).inIncrement();
					}
				}
			}
			
			if (met.getReturnType().getName().startsWith("java") || met.getReturnType().getName().startsWith("[Ljava") || met.getReturnType().isPrimitive()){
				continue;
			}else{
				m.outIncrement();
				
				if(classMap.containsKey(met.getName())){
					classMap.get(met.getName()).inIncrement();
				}
			}
		}
	}
	
	public void processConstructors(Class<?> cls, Metric m){
		Constructor<?> ctorlist[] = cls.getDeclaredConstructors();
		
		for (Constructor<?> c : ctorlist){
			Class<?> params[] = c.getParameterTypes();
			Class<?> exceps[] = c.getExceptionTypes();
			
			for (Class<?> p : params){
				if (p.getName().startsWith("java") || p.getName().startsWith("[Ljava") || p.isPrimitive()){
					continue;
				}else{
					
					m.outIncrement();

					if(classMap.containsKey(p.getName())){
						classMap.get(p.getName()).inIncrement();
					}
				}
			}
			for (Class<?> e : exceps){
				if (e.getName().startsWith("java") || e.getName().startsWith("[Ljava") || e.isPrimitive()){
					continue;
				}else{
					m.outIncrement();

					if(classMap.containsKey(e.getName())){
						classMap.get(e.getName()).inIncrement();
					}
				}
			}
		}
	}
	
	public void processFields(Class<?> cls, Metric m){
		Field fieldlist[] = cls.getDeclaredFields();
		
		for (Field f : fieldlist){
			
			if (f.getType().getName().startsWith("java") || f.getType().isPrimitive()){
				continue;
			}else{
				m.outIncrement();
				
				if(classMap.containsKey(f.getName())){
					classMap.get(f.getName()).inIncrement();
				}
			}
		}
	}
	
	public void processInterfaces(Class<?> cls, Metric m){
		Class<?>[] interfaces = cls.getInterfaces();
		
		for (Class<?> i : interfaces){
			//System.out.println(i.getName());
			if(i.getName().startsWith("java")){
				continue;
			}else{
				// Outdegrees
				m.outIncrement();
				// Indegrees
				if(classMap.containsKey(i.getName())){
					classMap.get(i.getName()).inIncrement();
				}
			}
		}	
	}
}
