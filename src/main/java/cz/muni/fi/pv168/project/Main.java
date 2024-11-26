package cz.muni.fi.pv168.project;

import cz.muni.fi.pv168.project.ui.MainWindow;

import java.awt.EventQueue;

public class Main {

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new MainWindow().show());
    }
}
