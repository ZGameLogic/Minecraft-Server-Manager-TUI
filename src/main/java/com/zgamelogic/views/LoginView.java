package com.zgamelogic.views;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.zgamelogic.Model;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class LoginView {
    private final Model model;

    public void input(KeyStroke key){
        switch(key.getKeyType()){
            case KeyType.Escape:
                model.setLoggingIn(false);
                break;
        }
    }

    public void draw(){

    }
}
