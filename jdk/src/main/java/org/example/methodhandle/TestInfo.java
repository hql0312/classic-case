package org.example.methodhandle;

import java.lang.invoke.MethodHandles;

public class TestInfo {

    private int id;
    private String name;

    public TestInfo(int id,String name) {
        this.id = id;
        this.name = name;
    }

    public String getDisplay() {
        return String.join("-",String.valueOf(id),this.name);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static MethodHandles.Lookup getLookup() {
        return MethodHandles.lookup();
    }


}
