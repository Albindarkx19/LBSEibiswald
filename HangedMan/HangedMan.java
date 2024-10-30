/*
 * Project: HangedMan
 * Klasse: 2aAPC / 2024
 * Author:  Albin Prushi
 * Last Change:
 *    by:   APR
 *    date: 09.10.2024
 * Copyright (c): LBS Eibiswald, 2024
 */

package HangedMan;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

public class HangedMan
{
    private String wordToGuess;
    private StringBuilder currentGuess;
    private ArrayList<Character> guessedLetters;
    private int wrongGuesses;
    private int maxAttempts;
    private int timeRemaining;
    private JPanel panel1;
    private JTextField wordDisplayField;
    private JTextField letterInputField;
    private JLabel wrongGuessesLabel;
    private JLabel guessedLettersLabel;
    private JLabel imageLabel;
    private JLabel timerLabel;
    private int gamesWon;
    private int gamesLost;
    private Timer gameTimer;
    private Timer timerCountdown;
    private Timer easterEggTimer;
    private String[] images;
    private String endThemeImage = "images/EndTheme.jpg";
    private String[] timerImages = {"images/HangedManTimer1.png", "images/HangedManTimer2.png", "images/HangedManTimer3.png"};
    private String[] easterEggImages = {"images/HangedManEasterEGG1.png", "images/HangedManEasterEGG2.png"};

    public HangedMan(String word, int maxAttempts, int timeLimit)
    {
        this.wordToGuess = word.toLowerCase();
        this.currentGuess = new StringBuilder("_".repeat(word.length()));
        this.guessedLetters = new ArrayList<>();
        this.wrongGuesses = 0;
        this.maxAttempts = maxAttempts;
        this.timeRemaining = timeLimit;
        this.gamesWon = 0;
        this.gamesLost = 0;

        images = new String[9];
        for (int i = 0; i < 9; i++)
        {
            images[i] = "images/HangedMan" + (i + 1) + ".png";
        }

        startGameTimer();
        updateDisplay();
        updateImage();
    }

    private void startGameTimer()
    {
        gameTimer = new Timer(1000, new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                timeRemaining--;
                timerLabel.setText("Verbleibende Zeit: " + timeRemaining + " Sekunden");
                if (timeRemaining <= 0)
                {
                    gameTimer.stop();
                    JOptionPane.showMessageDialog(null, "Zeit abgelaufen!");
                    startTimerCountdown();
                }
            }
        });
        gameTimer.start();
    }

    private void startTimerCountdown()
    {
        timerCountdown = new Timer(3000, new ActionListener()
        {
            int timerImageIndex = 0;

            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (timerImageIndex < timerImages.length)
                {
                    ImageIcon timerIcon = new ImageIcon(timerImages[timerImageIndex]);
                    imageLabel.setIcon(timerIcon);
                    timerImageIndex++;
                }
                else
                {
                    timerCountdown.stop();
                    ImageIcon endIcon = new ImageIcon(endThemeImage);
                    imageLabel.setIcon(endIcon);
                    JOptionPane.showMessageDialog(null, "Game Over!");
                }
            }
        });
        timerCountdown.start();
    }

    private void startEasterEgg()
    {
        easterEggTimer = new Timer(4000, new ActionListener()
        {
            int easterEggImageIndex = 0;

            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (easterEggImageIndex < easterEggImages.length)
                {
                    ImageIcon easterEggIcon = new ImageIcon(easterEggImages[easterEggImageIndex]);
                    imageLabel.setIcon(easterEggIcon);
                    easterEggImageIndex++;
                }
                else
                {
                    easterEggTimer.stop();
                    JOptionPane.showMessageDialog(null, "Easter Egg! Du hast automatisch gewonnen!");
                    gamesWon++;
                    updateGameStats();
                }
            }
        });
        easterEggTimer.start();
    }

    public boolean makeGuess(String input)
    {
        input = input.toLowerCase();

        if (input.equals("fallen"))
        {
            startEasterEgg();
            return true;
        }

        if (input.length() == 1)
        {
            char letter = input.charAt(0);
            if (guessedLetters.contains(letter))
            {
                JOptionPane.showMessageDialog(null, "Du hast diesen Buchstaben schon geraten!");
                return false;
            }

            guessedLetters.add(letter);
            updateGuessedLetters();

            if (wordToGuess.contains(String.valueOf(letter)))
            {
                for (int i = 0; i < wordToGuess.length(); i++)
                {
                    if (wordToGuess.charAt(i) == letter)
                    {
                        currentGuess.setCharAt(i, letter);
                    }
                }
                updateDisplay();
                return true;
            }
            else
            {
                wrongGuesses++;
                updateImage();
                updateDisplay();
                return false;
            }
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Bitte geben Sie nur einen Buchstaben oder das Easter-Egg-Wort ein.");
            return false;
        }
    }

    public boolean isGameOver()
    {
        return wrongGuesses >= maxAttempts || currentGuess.toString().equals(wordToGuess);
    }

    public void updateDisplay()
    {
        wordDisplayField.setText(currentGuess.toString().replace("", " ").trim());
        wrongGuessesLabel.setText("Falsche Versuche: " + wrongGuesses + "/" + maxAttempts);
    }

    public void updateGuessedLetters()
    {
        guessedLettersLabel.setText("Geratene Buchstaben: " + guessedLetters.toString());
    }

    public void updateImage()
    {
        if (wrongGuesses < 8)
        {
            ImageIcon icon = new ImageIcon(images[wrongGuesses]);
            imageLabel.setIcon(icon);
        }
        else if (wrongGuesses == 8)
        {
            ImageIcon icon = new ImageIcon(images[8]);
            imageLabel.setIcon(icon);
        }
    }

    public static void main(String[] args)
    {
        JFrame frame = new JFrame("Hanged Man");

        String[] wordList = { "apfel", "birne", "ananas", "banane", "hangman", "weihnachten", "galgenmännchen", "enzyklopädie" };
        Random random = new Random();
        String selectedWord = wordList[random.nextInt(wordList.length)];

        String attemptsInput = JOptionPane.showInputDialog(null, "Wie viele Fehlversuche sind erlaubt?");
        String timeInput = JOptionPane.showInputDialog(null, "Wie lange soll das Spiel (in Sekunden) dauern?");
        int maxAttempts = Integer.parseInt(attemptsInput);
        int timeLimit = Integer.parseInt(timeInput);

        HangedMan game = new HangedMan(selectedWord, maxAttempts, timeLimit);
        frame.setContentPane(game.panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set the frame to maximize the window, keeping the taskbar and title bar
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximized state
        frame.setVisible(true);

        game.letterInputField.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String input = game.letterInputField.getText();
                if (!input.isEmpty())
                {
                    if (!game.makeGuess(input))
                    {
                        JOptionPane.showMessageDialog(null, "Falscher Versuch!");
                    }

                    if (game.isGameOver())
                    {
                        if (game.wrongGuesses >= game.maxAttempts)
                        {
                            JOptionPane.showMessageDialog(null, "Game Over! Das Wort war: " + game.wordToGuess);
                            game.gamesLost++;
                        }
                        else
                        {
                            JOptionPane.showMessageDialog(null, "Du hast das Wort erraten: " + game.getCurrentGuess());
                            game.gamesWon++;
                        }
                        game.updateGameStats();
                        game.resetGame(wordList[random.nextInt(wordList.length)], maxAttempts);
                    }
                }
                game.letterInputField.setText("");
            }
        });
    }

    public void updateGameStats() {
        System.out.println("Gewonnen: " + gamesWon + " | Verloren: " + gamesLost);
    }

    public void resetGame(String newWord, int maxAttempts)
    {
        wordToGuess = newWord;
        currentGuess = new StringBuilder("_".repeat(wordToGuess.length()));
        guessedLetters.clear();
        wrongGuesses = 0;
        this.maxAttempts = maxAttempts;
        updateDisplay();
        updateGuessedLetters();
        updateImage();
        startGameTimer();
    }

    public String getCurrentGuess() {
        return currentGuess.toString();
    }
}
