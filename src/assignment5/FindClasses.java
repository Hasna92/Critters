/* CRITTERS GUI <MyClass.java>
 * EE422C Project 5 submission by
 * Replace <...> with your actual data.
 * Syed Naqvi
 * smn756
 * 16225
 * Khalid Ahmad
 * kaa2625
 * 16190
 * Slip days used: <0>
 * Spring 2019
 */

/*
   Describe here known bugs or issues in this file. If your issue spans multiple
   files, or you are not sure about details, add comments to the README.txt file.
 */
package assignment5;


import java.io.File;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.*;

public class FindClasses {

    //Code borrowed and than modified from Victor Tatai from DZONE java tutorials
    /**
     * Scans all classes accessible from the context class loader which belong to the given package and subpackages.
     *
     * @param packageName The base package
     * @return The classes
     * @throws ClassNotFoundException
     * @throws IOException
     */
    public static Class[] getClasses(String packageName)
            throws ClassNotFoundException, IOException {
        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            assert classLoader != null;
            String path = packageName.replace('.', '/');
            Enumeration resources = classLoader.getResources(path);
            List<File> dirs = new ArrayList();
            while (resources.hasMoreElements()) {
                URL resource = (URL) resources.nextElement();
                dirs.add(new File(resource.getFile()));
            }
            ArrayList classes = new ArrayList();
            for (File directory : dirs) {
                classes.addAll(findClasses(directory, packageName));
            }
            return (Class[]) classes.toArray(new Class[classes.size()]);
        }catch(ClassNotFoundException | IOException e){
            System.out.println("Fail");
            throw new IOException();
        }
    }
    /**
     * Recursive method used to find all classes in a given directory and subdirs.
     *
     * @param directory   The base directory
     * @param packageName The package name for classes found inside the base directory
     * @return The classes
     * @throws ClassNotFoundException
     */
    private static List findClasses(File directory, String packageName) throws ClassNotFoundException {
        try {
            List classes = new ArrayList();
            if (!directory.exists()) {
                return classes;
            }
            File[] files = directory.listFiles();
            for (File file : files) {
                if (file.isDirectory()) {
                    assert !file.getName().contains(".");
                    classes.addAll(findClasses(file, packageName + "." + file.getName()));
                } else if (file.getName().endsWith(".class")) {
                    classes.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
                }
            }
            return classes;
        }catch(ClassNotFoundException e){
            System.out.println("Fail.");
            throw new ClassNotFoundException();
        }
    }

    //added from within main
    //modify an ArrayList to hold all valid Critter names
    public static void getValidCritters(List<Class> validClasses, List<String> critterNames, int i) {
        try {
            while(i<validClasses.size()) {
                if (Modifier.isAbstract(validClasses.get(i).getModifiers())) {
                    i++;
                    continue;
                } else if (validClasses.get(i).newInstance() instanceof Critter) {
                    String classString = validClasses.get(i).toString();
                    classString = classString.substring(18);
                    critterNames.add(classString);
                    i++;
                }
                else{
                    i++;
                }
            }
        }catch (InstantiationException | IllegalAccessException e){
            validClasses.remove(i);
            getValidCritters(validClasses,critterNames,i);
        }

    }
}
