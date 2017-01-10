package ie.gmit.sw;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

public class JARParser {
	private Map<String, Metric> classMap = new HashMap<String, Metric>();
	private Processor proc = new Processor(classMap);
	
	public JARParser(){
		super();
	}
	
	public Map<String, Metric> parse(File file) throws Exception{
		// Parse the jar file once to put all the class names and empty metrics into map
		URL[] urls = { file.toURL()};
		URLClassLoader cl = URLClassLoader.newInstance(urls);
		JarInputStream in = new JarInputStream(new FileInputStream(file));
		JarEntry next = in.getNextJarEntry();
		while (next != null) {
			if (next.getName().endsWith(".class")) {
				String name = next.getName().replaceAll("/", "\\.");
				name = name.replaceAll(".class", "");
				if (!name.contains("$")) name.substring(0, name.length() - ".class".length());
				//System.out.println(name);
				classMap.put(name, new Metric());
			}
			next = in.getNextJarEntry();
		}
		
		// Parse again to process each class
		in = new JarInputStream(new FileInputStream(new File("string-service.jar")));
		next = in.getNextJarEntry();
		while (next != null) {
			if (next.getName().endsWith(".class")) {
				String name = next.getName().replaceAll("/", "\\.");
				name = name.replaceAll(".class", "");
				if (!name.contains("$")) name.substring(0, name.length() - ".class".length());
				Class<?> cls = cl.loadClass(name);
				proc.process(cls, classMap.get(name));
			}
			next = in.getNextJarEntry();
		}
		
		in.close();

		return classMap;
	}
}
