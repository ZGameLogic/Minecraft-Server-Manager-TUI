package com.zgamelogic;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Main {
    private static final List<String> SERVERS = Arrays.asList(
            "survival-1",
            "creative-1",
            "modded-1",
            "minigames-1"
    );

    public static void main(String[] args) throws IOException {
        Terminal terminal = new DefaultTerminalFactory().createTerminal();
        Screen screen = new TerminalScreen(terminal);
        screen.startScreen();
        screen.setCursorPosition(null); // hide cursor

        int selected = 0;
        boolean running = true;

        try {
            while (running) {
                draw(screen, selected);

                KeyStroke key = screen.readInput();
                if (key == null) continue;

                switch (key.getKeyType()) {
                    case ArrowUp:
                        selected = Math.max(0, selected - 1);
                        break;
                    case ArrowDown:
                        selected = Math.min(SERVERS.size() - 1, selected + 1);
                        break;
                    case Character:
                        char c = Character.toLowerCase(key.getCharacter());
                        if (c == 'k') selected = Math.max(0, selected - 1);
                        if (c == 'j') selected = Math.min(SERVERS.size() - 1, selected + 1);
                        if (c == 'q') running = false;
                        break;
                    case EOF:
                    case Escape:
                        running = false;
                        break;
                    default:
                        break;
                }
            }
        } finally {
            screen.stopScreen();
            terminal.close();
        }
    }

    private static void draw(Screen screen, int selected) throws IOException {
        screen.doResizeIfNecessary();
        screen.clear();

        TerminalSize size = screen.getTerminalSize();
        TextGraphics g = screen.newTextGraphics();

        int w = size.getColumns();
        int h = size.getRows();

        int headerH = 1;
        int footerH = 1;
        int bodyTop = headerH;
        int bodyBottom = h - footerH;
        int bodyH = Math.max(1, bodyBottom - bodyTop);
        int leftW = Math.max(20, w / 4);

        // Header
        g.setBackgroundColor(TextColor.ANSI.MAGENTA);
        g.setForegroundColor(TextColor.ANSI.WHITE);
        g.fillRectangle(new TerminalPosition(0, 0), new TerminalSize(w, headerH), ' ');
        g.putString(1, 0, " Minecraft Server Manager  |  View: Servers ");

        // Left pane
        g.setBackgroundColor(TextColor.ANSI.DEFAULT);
        g.setForegroundColor(TextColor.ANSI.CYAN);
        g.drawLine(0, bodyTop, w - 1, bodyTop, '-');
        g.putString(1, bodyTop, " Servers ");
        g.drawLine(leftW, bodyTop, leftW, h - 1, '|');

        for (int i = 0; i < SERVERS.size() && (bodyTop + 1 + i) < bodyBottom; i++) {
            int y = bodyTop + 1 + i;
            String row = " " + SERVERS.get(i);

            if (i == selected) {
                g.setBackgroundColor(TextColor.ANSI.WHITE);
                g.setForegroundColor(TextColor.ANSI.BLACK);
                g.fillRectangle(new TerminalPosition(1, y), new TerminalSize(Math.max(1, leftW - 1), 1), ' ');
                g.putString(2, y, row + "  <");
            } else {
                g.setBackgroundColor(TextColor.ANSI.DEFAULT);
                g.setForegroundColor(TextColor.ANSI.WHITE);
                g.putString(2, y, row);
            }
        }

        // Main pane
        int mainX = leftW + 2;
        g.setForegroundColor(TextColor.ANSI.GREEN);
        g.setBackgroundColor(TextColor.ANSI.DEFAULT);
        g.putString(mainX, bodyTop + 1, "Selected: " + SERVERS.get(selected));
        g.setForegroundColor(TextColor.ANSI.WHITE);
        g.putString(mainX, bodyTop + 3, "Status: RUNNING");
        g.putString(mainX, bodyTop + 4, "Players: 0/20");
        g.putString(mainX, bodyTop + 5, "TPS: 20.0");
        g.putString(mainX, bodyTop + 7, "Actions:");
        g.putString(mainX, bodyTop + 8, "- [S] Start  [T] Stop  [R] Restart  [L] Logs");

        // Footer
        g.setBackgroundColor(TextColor.ANSI.BLACK);
        g.setForegroundColor(TextColor.ANSI.WHITE);
        g.fillRectangle(new TerminalPosition(0, h - 1), new TerminalSize(w, 1), ' ');
        g.putString(1, h - 1, "j/k or arrows: move   q: quit");

        screen.refresh();
    }
}