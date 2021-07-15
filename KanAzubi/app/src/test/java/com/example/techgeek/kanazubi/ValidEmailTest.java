package com.example.techgeek.kanazubi;

import org.junit.Test;

import static org.junit.Assert.*;

public class ValidEmailTest {
    @Test
    public void emailValidator_CorrectEmail() {
        assertTrue(Register.isEmailValid("name@email.com"));
        assertTrue(Register.isEmailValid("admin@dataport.de"));
        assertTrue(Register.isEmailValid("administrator@hotmail.co.uk"));
        assertFalse(Register.isEmailValid("dadfgrefasdas.adada"));
        assertFalse(Register.isEmailValid("da$s?@a.da"));
        assertFalse(Register.isEmailValid("dada"));
    }
}
