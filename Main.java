package bullscows;

import java.util.Scanner;
import java.util.Random;

public class Main {


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // todo geen copypasta

        System.out.println("Please, enter the secret code's length:");

        String langTest = scanner.nextLine();
        char [] inputTest = convertToCharacters(langTest, langTest.length());
        for (int i = 0; i < langTest.length(); i++) {
            if (!Character.isDigit(inputTest[i])) {
                System.out.printf("Error: %s isn't a valid number.", langTest);
                System.exit(0);
            }
        }
        int hoelang = Integer.parseInt(langTest);

        if (hoelang <= 0 || hoelang > 36) {
            System.out.println("Out of bounds error.");
            System.exit(0);
        }

        System.out.println("Input the number of possible symbols in the code:");

        String secondLangTest = scanner.nextLine();

        char [] secondInputTest = convertToCharacters(secondLangTest, secondLangTest.length());
        for (int i = 0; i < secondLangTest.length(); i++) {
            if (!Character.isDigit(secondInputTest[i])) {
                System.out.printf("Error: %s isn't a valid number.", secondLangTest);
                System.exit(0);
            }
        }

        int possibleSymbols = Integer.parseInt(secondLangTest);

        if (possibleSymbols <= 0 || possibleSymbols > 36) {
            System.out.println("Error, incorrect input.");
            System.exit(0);
        }
        String allSymbols = "0123456789abcdefghijklmnopqrstuvwxyz";

        String kruisjes = "*".repeat(hoelang);

        if (possibleSymbols <= 10) {
            System.out.printf("The secret is prepared: %s (0-%d).\n", kruisjes, possibleSymbols - 1);
        } else {
            System.out.printf("The secret is prepared: %s (0-9, a-%s).\n", kruisjes, allSymbols.charAt(possibleSymbols - 1));
        }

        System.out.println("Okay, let's start a game!");

        if (hoelang > possibleSymbols) {
            System.out.printf("Error: it's not possible to generate a code with a length of %s with %s unique symbols.", hoelang, possibleSymbols);
            System.exit(0);
        }
        String secret = uniekeCodeMaken(hoelang, possibleSymbols);

        boolean gewonnen = false;
        int turn = 1;
        while (!gewonnen) {
            System.out.println("Turn " + turn++);
            String code = scanner.next();
       //     if (code > hoelang)
            grading(secret, code, hoelang);
            if (secret.equals(code)) {
                gewonnen = true;
            }
        }
    }

    public static String uniekeCodeMaken(int hoelang, int possibleSymbols) {
        String allSymbols = "0123456789abcdefghijklmnopqrstuvwxyz";
        String usedChars = allSymbols.substring(0, possibleSymbols);

        StringBuilder uniek = new StringBuilder();
        Random randO = new Random(hoelang);

        while (uniek.length() < hoelang) {
            char c = usedChars.charAt(randO.nextInt(possibleSymbols));
            if (uniek.toString().indexOf(c) == - 1) {
                uniek.append(c);
            }
        }
        return uniek.toString();
    }

    public static void grading(String secretCode, String codeInput, int hoelang) {
        int cowCount = 0;
        int bullCount = 0;

        char[] secretChars = convertToCharacters(secretCode, hoelang);
        char[] codeChars = convertToCharacters(codeInput, hoelang);

        // vergelijk code met geheime code
        for (int i = 0; i < hoelang; i++) {
            if (secretChars[i] == codeChars[i]) {
                bullCount++; // als ze gelijk zijn en op dezelfde positie, bulls++
            } else {
                for (int j = 0; j < hoelang; j++) {
                    if (codeChars[i] == secretChars[j]) {
                        cowCount++; // als het getal in de code zit maar op een andere positie, cows++
                        break;
                    }
                }
            }
        }

        if (bullCount == hoelang) {
            System.out.printf("Grade: %s bull(s). \nCongratulations! You guessed the secret code.\n", hoelang);
        } else if (bullCount == 0 && cowCount == 0) {
            System.out.println("Grade: None.");
        } else if (bullCount == 0) {
            System.out.printf("Grade: %s cow(s).\n", cowCount);
        } else {
            System.out.printf("Grade: %s bulls(s) and %s cow(s).\n", bullCount, cowCount);
        }
    }

    public static char[] convertToCharacters(String code, int hoelang) {
        char[] characters = new char[hoelang];
        for (int i = 0; i < hoelang; i++) {
            characters[i] = (code.charAt(i));
        }
        return characters;
    }
}