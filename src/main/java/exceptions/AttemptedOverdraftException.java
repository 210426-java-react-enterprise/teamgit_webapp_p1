package exceptions;

public class AttemptedOverdraftException extends RuntimeException{
    public AttemptedOverdraftException(){
        super("That withdrawal amount exceeds your current balance!");
    }
}
