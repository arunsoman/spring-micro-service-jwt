package com.flytxt.dummy;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/b6")
public class WebController {

    @PreAuthorize("hasAuthority('B6_READ')")
    @RequestMapping(method = RequestMethod.GET)
    public String readFoo() {
        return "read foo " + UUID.randomUUID().toString();
    }

    @PreAuthorize("hasAuthority('B6_WRITE')")
    @RequestMapping(method = RequestMethod.POST)
    public String writeFoo() {
        return "write foo " + UUID.randomUUID().toString();
    }
}
