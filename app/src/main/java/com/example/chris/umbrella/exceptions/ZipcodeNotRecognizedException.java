package com.example.chris.umbrella.exceptions;

public class ZipcodeNotRecognizedException extends Exception
{
	public ZipcodeNotRecognizedException() 
	{
        super("Zipcode must be 5 digits.");
    }
}
