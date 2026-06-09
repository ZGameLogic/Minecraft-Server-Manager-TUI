package com.zgamelogic;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;

public class Main {
    public static void main(String[] args) throws Exception {
        DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory()
                .setInitialTerminalSize(new TerminalSize(100, 30));

        Screen screen = terminalFactory.createScreen();
        screen.startScreen();

        try {
            MultiWindowTextGUI textGUI = new MultiWindowTextGUI(screen);

            BasicWindow window = new BasicWindow("Minecraft Server Manager");
            window.setHints(java.util.List.of(Window.Hint.CENTERED));

            Panel content = new Panel(new GridLayout(1));

            Label title = new Label("Minecraft Server Manager");
            title.addStyle(SGR.BOLD);
            title.setForegroundColor(new TextColor.RGB(80, 170, 255));

            Label status = new Label("Status: Not connected");
            Label server = new Label("Selected Server: None");

            Button loginButton = new Button("Login", () -> status.setText("Status: Logging in..."));
            Button refreshButton = new Button("Refresh", () -> status.setText("Status: Refreshed"));
            Button quitButton = new Button("Exit", window::close);

            Panel actions = new Panel(new GridLayout(3));
            actions.addComponent(loginButton);
            actions.addComponent(refreshButton);
            actions.addComponent(quitButton);

            content.addComponent(title);
            content.addComponent(new EmptySpace(new TerminalSize(0, 1)));
            content.addComponent(status);
            content.addComponent(server);
            content.addComponent(new EmptySpace(new TerminalSize(0, 1)));
            content.addComponent(actions);

            window.setComponent(content.withBorder(Borders.singleLine("Main")));
            textGUI.addWindowAndWait(window);
        } finally {
            screen.stopScreen();
        }
    }
}