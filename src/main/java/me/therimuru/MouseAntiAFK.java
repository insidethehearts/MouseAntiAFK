package me.therimuru;

import javax.swing.*;
import java.awt.*;

public class MouseAntiAFK  {

    private static boolean isRunning;
    private static Runnable runnable;

    private static JFrame frame;
    private static Font smallFont;
    private static Font bigFont;

    private static JButton runButton;
    private static JLabel mouseMoveDelayText, mouseMoveIntervalText, xIndentText, yIndentText;
    private static JTextField mouseMoveDelayInput, mouseMoveIntervalInput, xIndentInput, yIndentInput;

    public static void main(String[] args) {
        isRunning = false;
        runnable = () -> {
            while (true) {
                if (isRunning) {
                    try {
                        PointerInfo info = MouseInfo.getPointerInfo();
                        Robot robot = new Robot();

                        int startX = (int) info.getLocation().getX();
                        int startY = (int) info.getLocation().getY();

                        robot.mouseMove(startX + Integer.parseInt(xIndentInput.getText()), startY + Integer.parseInt(yIndentInput.getText()));
                        Thread.sleep(Long.parseLong(mouseMoveDelayInput.getText()));
                        robot.mouseMove(startX, startY);
                        Thread.sleep(Long.parseLong(mouseMoveIntervalInput.getText()));
                    } catch (AWTException | InterruptedException e) {
                        throw new RuntimeException(e);
                    } catch (NumberFormatException exception) {
                        JOptionPane.showMessageDialog(frame, "Fields can only contain numbers.", "Error", JOptionPane.ERROR_MESSAGE);
                        stopRunnable();
                    }
                } else { break; }
            }
        };
        frame = new JFrame("MouseAntiAFK");
        smallFont = new Font("Arial", Font.BOLD, 15);
        bigFont = new Font("Arial", Font.BOLD, 50);

        frame.setSize(300, 500);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);;

        mouseMoveDelayText = new JLabel("Delay (ms) ");
        mouseMoveDelayText.setBounds(15, 15, 125, 30);
        mouseMoveDelayText.setFont(smallFont);
        mouseMoveDelayText.setForeground(Color.BLACK);

        mouseMoveDelayInput = new JTextField("5", 3);
        mouseMoveDelayInput.setSize(125, 30);
        mouseMoveDelayInput.setFont(smallFont);
        equateLocation(mouseMoveDelayInput, mouseMoveDelayText);
        setAbsence(mouseMoveDelayInput, mouseMoveDelayText, 10, Indents.RIGHT);

        mouseMoveIntervalText = new JLabel("Interval (ms) ");
        mouseMoveIntervalText.setBounds(15, 15, 125, 30);
        mouseMoveIntervalText.setSize(125, 30);
        mouseMoveIntervalText.setFont(smallFont);
        setAbsence(mouseMoveIntervalText, mouseMoveDelayText, 15, Indents.DOWN);


        mouseMoveIntervalInput = new JTextField("3000");
        mouseMoveIntervalInput.setSize(125, 30);
        mouseMoveIntervalInput.setFont(smallFont);
        equateLocation(mouseMoveIntervalInput, mouseMoveIntervalText);
        setAbsence(mouseMoveIntervalInput, mouseMoveIntervalText, 10, Indents.RIGHT);

        xIndentText = new JLabel("Indent x");
        xIndentText.setBounds(15, 15, 125, 30);
        setAbsence(xIndentText, mouseMoveIntervalText, 15, Indents.DOWN);
        xIndentText.setFont(smallFont);
        xIndentText.setForeground(Color.BLACK);

        xIndentInput = new JTextField("100", 3);
        xIndentInput.setSize(125, 30);
        xIndentInput.setFont(smallFont);
        equateLocation(xIndentInput, xIndentText);
        setAbsence(xIndentInput, xIndentText, 10, Indents.RIGHT);

        yIndentText = new JLabel("Indent y");
        yIndentText.setBounds(15, 15, 125, 30);
        setAbsence(yIndentText, xIndentText, 15, Indents.DOWN);
        yIndentText.setFont(smallFont);
        yIndentText.setForeground(Color.BLACK);

        yIndentInput = new JTextField("100", 3);
        yIndentInput.setSize(125, 30);
        yIndentInput.setFont(smallFont);
        equateLocation(yIndentInput, yIndentText);
        setAbsence(yIndentInput, yIndentText, 10, Indents.RIGHT);

        runButton = new JButton("Enable");
        runButton.setBorder(BorderFactory.createEmptyBorder());
        runButton.setBackground(Color.GREEN);
        runButton.setFont(bigFont);
        runButton.setFocusable(false);
        runButton.setSize(250, 75);
        runButton.setBounds(15, frame.getHeight() - runButton.getHeight() - 50, runButton.getWidth(), runButton.getHeight());

        runButton.addActionListener(e -> {
            if (isRunning) {
                stopRunnable();
            } else {
                startRunnable();
            }
        });

        frame.add(mouseMoveDelayText);
        frame.add(mouseMoveDelayInput);
        frame.add(mouseMoveIntervalText);
        frame.add(mouseMoveIntervalInput);
        frame.add(xIndentText); frame.add(xIndentInput);
        frame.add(yIndentText); frame.add(yIndentInput);
        frame.add(runButton);
        frame.setVisible(true);
    }

    private static void stopRunnable() {
        runButton.setText("Enable");
        runButton.setBackground(Color.GREEN);
        isRunning = false;
    }

    private static void startRunnable() {
        runButton.setText("Disable");
        runButton.setBackground(Color.RED);
        isRunning = true;
        new Thread(runnable).start();
    }

    private static void setAbsence(Component currentComponent, Component relationComponent, int pixels, Indents indent) {
        int leftBorder, rightBorder;
            leftBorder = relationComponent.getX();
            rightBorder = relationComponent.getX() + relationComponent.getWidth();
        switch (indent) {
            case LEFT: {
                currentComponent.setLocation(leftBorder - pixels - currentComponent.getWidth(), currentComponent.getY());
                break;
            }
            case RIGHT: {
                currentComponent.setLocation(rightBorder + pixels, currentComponent.getY());
                break;
            }
            case UP: {
                currentComponent.setLocation(currentComponent.getX(), relationComponent.getY() - currentComponent.getHeight() - pixels);
                break;
            }
            case DOWN: {
                currentComponent.setLocation(currentComponent.getX(), relationComponent.getY() + currentComponent.getHeight() + pixels);
                break;
            }
        }
    }

    private static void equateLocation(Component currentComponent, Component relativeComponent) {
        currentComponent.setLocation(relativeComponent.getLocation());
    }
}