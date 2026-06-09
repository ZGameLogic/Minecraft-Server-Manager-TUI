package com.zgamelogic;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Model {
    private boolean loggingIn;
    private boolean running;

    public Model(){
        loggingIn = false;
        running = true;
    }
}
