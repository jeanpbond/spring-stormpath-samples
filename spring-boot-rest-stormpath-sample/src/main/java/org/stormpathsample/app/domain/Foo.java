package org.stormpathsample.app.domain;

public class Foo {
    
    private String foo;
    private String bar;
    
    public Foo(String foo, String bar) {
        this.foo = foo;
        this.bar = bar;
    }
    
    public String getFoo() {
        return foo;
    }
    
    public void setFoo(String foo) {
        this.foo = foo;
    }
    
    public String getBar() {
        return bar;
    }
    
    public void setBar(String bar) {
        this.bar = bar;
    }
}
