package com.delete.blogg.controller;

import org.apache.commons.codec.digest.DigestUtils;

import static org.apache.commons.codec.digest.DigestUtils.md5Hex;

public class MdSa {
    public static void main(String[] args) {
        String s = md5Hex("lisi");
        System.out.println(s);

    }
}
