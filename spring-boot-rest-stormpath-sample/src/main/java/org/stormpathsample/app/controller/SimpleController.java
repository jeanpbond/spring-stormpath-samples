package org.stormpathsample.app.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.stormpathsample.app.domain.Foo;

@RestController
@RequestMapping(value = "/foo")
public class SimpleController {
	
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Foo> getAllSpending() {
        Foo foo = new Foo("foo", "bar");
    	return new ResponseEntity<Foo>(foo, HttpStatus.OK); 
    }
}
