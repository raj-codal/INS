###
#
# MAIN DES ALGORITHM
#
# AUTHOR: Raj Dhanani
#
###

from methods import *

# DES ALGORITHM:
def DESencryption(plainText,key):
    _64bits_binary_key = hex_to_binary_64bits(key)
    _56bits_binary_key = keyGenerator(_64bits_binary_key)
    _64bit_binary: str = hex_to_binary_64bits(plainText)
    initialPermutedString = initialPermutationOfPlainText(_64bit_binary)
    _56bits_key = _56bits_binary_key
    for i in range(16):
        l,r = splitBitsInHalf(_56bits_key)
        l = circularLeftShift(l,shiftTable[i])
        r = circularLeftShift(r, shiftTable[i])
        _56bits_key = l+r
        _48bit_key = keyCompressor(_56bits_key)
        initialPermutedString = roundFunction(initialPermutedString, _48bit_key, i)
    cipherText = binary_64bits_To_Hex(permutateFinal(initialPermutedString))
    return cipherText

# MAIN DRIVER
# EXAMPLE:
# plainText = ab2221abcd132536
# key =  0000160420116013
# cipher = e276fb75f0150ac8

plainText = input("ENTER PLAIN TEXT (64 bits HEX):")
key = input("ENTER KEY:")
cipherText = DESencryption(plainText,key)
print("INPUT:",plainText,"\nKEY:",key)
print("CIPHERTEXT:",cipherText)
exit(0)