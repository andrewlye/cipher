#Cipher

*Encoding, decrypting, and cracking*

The cipher program enciphers and decrypts text using known offset and block size values, and even cracks ciphers with unknown values by trying all combinations of values and comparing deciphered strings against simple punctuation and capitalization conventions.

To encipher a string, three operations are done: an offset is applied to each character, the string divides itself into blocks of a specified size (with a possible partial block left over), and the order of characters in each block is reversed.

For example, enciphering the clear text string *"Test offset three, block length two."* with offset 3 and block size 2 gives the enciphered text string *"hWwvr;iihv;wkwhu.he;ronfo;qhwj;kzwAr"*, and deciphering it with the same offset and block size gives back the original clear text string.