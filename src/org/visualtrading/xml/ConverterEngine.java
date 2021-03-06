/**
 * Copyright (c) 2000,1,2,3,4,5 visualtrading.org
 *
 * @author thanos vassilakis
 * @author yarik chinskiy
 *
 */
package org.visualtrading.xml;

import org.visualtrading.xml.nanoxml.XMLElement;

import java.util.Enumeration;
import java.util.Hashtable;


public class ConverterEngine {

// ------------------------------ FIELDS ------------------------------

    public static Hashtable factoryMappings = new Hashtable();

// -------------------------- STATIC METHODS --------------------------

    static {
        ConverterEngine.add(new HashtableConverter());
        ConverterEngine.add(new BooleanConverter());
        ConverterEngine.add(new IntegerConverter());
        ConverterEngine.add(new DoubleConverter());
        ConverterEngine.add(new StringConverter());
        ConverterEngine.add(new DimensionConverter());
        ConverterEngine.add(new ColorConverter());
        ConverterEngine.add(new PointConverter());
        ConverterEngine.add(new FontConverter());
        ConverterEngine.add(new KeyConverter());
    }

    static public void add(Converter converter) {
        add(converter, converter.getObjClass(), converter.tagName());
    }

    static public void add(Converter converter, Class clss, String name) {
        factoryMappings.put("CLASS:" + name, clss);
        factoryMappings.put(name, converter);
        factoryMappings.put(clss, converter);

    }

    static public XMLElement toXML(Object obj) {
        if (obj instanceof XMLObj) {
            return ((XMLObj) obj).toXML();
        }
        Converter converter = getConverterFrom(obj.getClass());
        if (converter == null) {
            throw new Error("ONT" + obj);
        }
        XMLElement xml = converter.fromObject(obj);
        //System.out.println(xml);
        return xml;

    }

    /* (non-Javadoc)
     * @see org.visualtrading.xml.Converter#getFromMapping(java.util.Hashtable, java.lang.Class)
     */
    static public Converter getConverterFrom(Class clss) {
        Converter converter = (Converter) factoryMappings.get(clss);
        while (converter == null && clss != null) {
            clss = clss.getSuperclass();
            if (clss == null) {
                System.err.println("CONVERTER FAILED:" + clss);
            } else {
                converter = (Converter) factoryMappings.get(clss);
            }
        }
        return converter;
    }

    /**
     * @param key
     * @param object
     *
     * @return
     */
    public static XMLElement toXML(String key, String keyValue, Object object) {
        XMLElement element = toXML(object);
        element.setAttribute(key, keyValue);
        return element;
    }

    static public Class getClassFrom(XMLElement xml) {
        return (Class) factoryMappings.get("CLASS:" + xml.getName());
    }


    /**
     * @param enumeration
     */
    public static Object[] childrenXML(XMLElement xml) {
        Object[] children = new Object[xml.countChildren()];
        int next = 0;
        for (Enumeration enumeration = xml.enumerateChildren(); enumeration.hasMoreElements(); next++) {
            XMLElement child = (XMLElement) enumeration.nextElement();
            children[next] = fromXML(child);
        }
        return children;
    }

    static public Object fromXML(XMLElement xml) {
        Converter converter = getConverterFrom(xml);
        if (converter == null) {
            try {
                Class classObj = Class.forName(xml.getStringAttribute("class"));
                Object obj = classObj.newInstance();
                if (obj instanceof XMLObj) {
                    XMLObj xmlObj = (XMLObj) obj;
                    xmlObj.fromXML(xml);
                    return xmlObj;
                } else {
                    return obj;
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return converter.fromXml(xml);

    }


    static public Converter getConverterFrom(XMLElement xml) {
        String name = xml.getStringAttribute("NAME", xml.getName());
        Converter converter = (Converter) factoryMappings.get(name);
        if (converter == null) {
            System.err.println("CONVERTER FAILED:" + xml);
        }
        return converter;
    }


}
