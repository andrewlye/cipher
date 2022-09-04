/* Name: Andrew
 * CS3 project 1: Cipher, Spring 2022
 * Cipher class
 */

import java.util.Scanner;

public class Cipher {
    private static Scanner scanner = new Scanner(System.in);
    private static char[] characters = {
        'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z',
        'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z',
        ' ','\n',',',';',':','.','!','?'
    }; // supported characters in specified order
    
    // Declaration of private attributes offset and blockSize
    private int offset;
    private int blockSize;

    /* precondition: 0 <= offset < 60, 1 <= blockSize */

    /**
     * Cipher constructor
     * precondition: 0 <= offset < 60, 1 <= blockSize
     * @param offset offset to index in characters array
     * @param blockSize size of blocks to reverse
     */
    public Cipher(int off, int block) {
        // TODO: initialize attributes here
        offset = off;
        blockSize = block;
    }

    // Offset method

    private void offsetChars(char[] arr, int offset)
    {

        for (int i = 0; i < arr.length; i++)
        {

            for (int j = 0; j < characters.length; j++)
            {
                if (characters[j] == arr[i])
                {
                    if ((j + offset) < 60)
                    {
                        arr[i] = characters[j + offset];
                        break;
                    }
                    else
                    {
                            arr[i] = characters[j - (60 - offset)];
                            break;
                    }
                    
                }
                else if (j == 59)
                {
                        arr[i] = '$';
                        break;
                }   
            }
        }
    }

    // Block creation and reversal method

    private void reverseBlocks(char[] arr, int blockSize)
    {
        
        char[] temp = new char[arr.length];
        int extra = arr.length % blockSize;

        for(int i = 0; i < arr.length - extra; i = i + blockSize)
        {
            for(int j = 0; j < blockSize; j++)
            {
                temp[i+j] = arr[(blockSize - 1 - j) + i];
            }
        }
        if (extra != 0)
        {
            for(int i = arr.length - extra; i < arr.length; i++)
            {
                temp[i] = arr[arr.length - 1 - (i - (arr.length - extra))];
            }
        }
        
        for(int i = 0; i < arr.length; i++)
        {
            arr[i] = temp[i];
        }

    }

    //Method for scoring a passed char[] converted string when the static crack method is called.

    private static int arrScore(char[] a)
    {
        int score = 0;
        //first letter capital
        for(int i = 0; i < 26; i++)
        {
            if (a[0] == characters[i])
            {
                score = score + 2;
                break;
            }
        }
        //first character space or punc
        for(int i = 52; i < 60; i++)
        {
            if (a[0] == characters[i])
            {
                score = score - 2;
                break;
            }
        }
        //last char
        for(int i = 57; i < 60; i++)
        {
            if (a[a.length - 1] == characters[i])
            {
                score = score + 2;
                break;
            }
        }
        //last char ,;:
        for(int i = 54; i < 57; i++)
        {
            if (a[a.length - 1] == characters[i])
            {
                score = score - 2;
                break;
            }
        }

        for(int i = 0; i < 51; i++)
        {
            if (a[a.length - 1] == characters[i])
            {
                score = score - 1;
                break;
            }
        }

        for (int i = 0; i < a.length; i++)
        {
            if (a[i] == ',' || a[i] == '.' || a[i] == ';' || a[i] == ':')
            {
                if (i != 0 && i != a.length - 1)
                {
                    for(int j = 0; j < 51; j++)
                    {
                        if (a[i - 1] == characters[j])
                        {
                            score = score + 1;
                            break;
                        }
                    }

                    if (a[i-1] == ' ')
                    {
                        score = score - 2;
                    }

                    for(int j = 0; j < 51; j++)
                    {
                        if (a[i + 1] == characters[j])
                        {
                            score = score - 2;
                            break;
                        }
                    }

                    if (a[i+1] == ' ')
                    {
                        score = score + 1;
                    }


                }
            }
        }



        return score;


    }

    /**
     * The encipher method returns the result of enciphering the given clear text string 
     * @param s clear text string to encipher
     * @return enciphered string
     */
    public String encipher(String clear) {
        char[] arr = clear.toCharArray();
        offsetChars(arr, offset);
        reverseBlocks(arr, blockSize);

        // TODO: add code here to encipher arr in place

        String result = new String(arr);
        return result;
    }

    /**
     * The decipher method returns the result of deciphering the given enciphered string
     * @param enciphered enciphered string to decipher
     * @return clear text deciphered string
     */
    public String decipher(String enciphered) {
        char[] arr = enciphered.toCharArray();
        reverseBlocks(arr, blockSize);
        offsetChars(arr, 60 - offset);

        // TODO: add code here to decipher arr in place

        String result = new String(arr);
        return result;
    }

    /**
     * The static crack method attempts to decipher a string that was enciphered with unknown offset and block size
     * @param enciphered
     * @return
     */
    public static String crack(String enciphered)
    {

        String temp = "";
        String bestDeciphered = null;
        int arrScore = 0;
        int highestScore = 0;

        /* TODO: add code here to construct Cipher instances with all reasonable
         * offsets and block lengths, using each to decipher the string, giving each
         * a score based on punctuation/capitalization conventions, keeping track of
         * the best deciphered string and score.
         */

         for (int i = 0; i < 60; i++)
         {
            for (int j = 1; j < enciphered.length(); j++)
            {
                 Cipher cipher = new Cipher(i, j);
                 String result = cipher.decipher(enciphered);
                 char[] arr = result.toCharArray();
                 arrScore = arrScore(arr);
                 if (arrScore > highestScore)
                 {
                     highestScore = arrScore;
                     bestDeciphered = result;
                 }

            }

         }

        return bestDeciphered;
    }

    public static void main(String[] args)
    {
        boolean cont = true;
        Scanner scanner = new Scanner(System.in);
        while (cont)
        {
            System.out.print("Would you like to encrypt, decrypt, or crack? (q to quit) ");
            String response = scanner.nextLine();
            int off = 0;
            int block = 0;
            String input = "";
            String output = "";
            if (response.equals("encrypt") || response.equals("decrypt"))
            {
                System.out.print("Enter offset (0-59): ");
                off = scanner.nextInt();
                System.out.print("Enter block size (>=1): ");
                block = scanner.nextInt();
                Cipher cipher = new Cipher(off, block);
                scanner.nextLine();
                if (response.equals("encrypt"))
                {
                    System.out.print("Enter text to encrypt (\\n for new lines): ");
                    input = scanner.nextLine().replace("\\n", "\n");
                    output = cipher.encipher(input);
                    output = output.replace("\n", "\\n");
                    System.out.println(output);
                }
                else 
                {
                    System.out.print("Enter text to decrypt: ");
                    input = scanner.nextLine().replace("\\n", "\n");
                    output = cipher.decipher(input);
                    System.out.println(output);
                }
            

            }
            else if (response.equals("crack"))
            {
                System.out.print("Enter text to crack: ");
                input = scanner.nextLine().replace("\\n", "\n");
                output = Cipher.crack(input);
                System.out.println(output);
            }
            else if (response.equals("q"))
                cont = false;
            else
            {
                System.out.println("Please enter a valid input.");
            }
        }

    }
}