package com.revature.thevault.service.classes;

import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

@Service("randomStringService")
public class RandomStringService {
	private ConcurrentHashMap<Integer, String> saltStrings = new ConcurrentHashMap<>();
	
	public String getSaltString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 18) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        if(!saltStrings.contains(saltStr)) {
        	System.out.println("This is unused and therefore safe.");
        	saltStrings.put(saltStrings.size(), saltStr);
        } else {
        	System.out.println("This is duplicate!");
        	getSaltString();
        }
        return saltStr;
	}
}
