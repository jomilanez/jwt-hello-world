package com.example.demo

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@RestController
class TestController {


    @GetMapping(value = ["/sources"])
    @ResponseBody
    fun getBlacklistedSources() = "{\"users\":[{\"firstname\":\"Richard\", \"lastname\":\"Feynman\"}," +
            "{\"firstname\":\"Marie\",\"lastname\":\"Curie\"}]}"

}