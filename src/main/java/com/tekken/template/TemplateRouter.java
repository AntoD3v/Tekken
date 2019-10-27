package com.tekken.template;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class TemplateRouter {

    public Map<String, String> routers = new HashMap<String, String>();

    public void addRoot(String root, String pagename){
        routers.put(root, pagename);
    }

    public void reset(String pagename){
        Iterator<String> it = routers.values().iterator();
        while (it.hasNext())
            if(it.next().equalsIgnoreCase(pagename))
                it.remove();
    }

    public String getPage(String root){
        return (routers.containsKey(root)) ? routers.get(root) : null;
    }

}
