import numpy as np


class Complex:

    def __init__(self, x, y):
        self.x = x
        self.y = y

    def __add__(self, other):
        x = self.x + other.x
        y = self.y + other.y
        return Complex(x, y)

    def __sub__(self, other):
        x = self.x - other.x
        y = self.y - other.y
        return Complex(x, y)

    def __mul__(self, other):
        if isinstance(other, Complex):
            x = self.x * other.x - self.y * other.y
            y = self.x * other.y + self.y * other.x
        else:
            x = self.x * other
            y = self.y * other
        return Complex(x, y)

    def __con__(self):
        return Complex(self.x, -self.y)

    def __truediv__(self, other):
        if isinstance(other, Complex):
            return self * other.__con__() / other.__abs__()
        else:
            return Complex(self.x / other, self.y / other)

    def __abs__(self):
        return self.x * self.x + self.y * self.y

    def pow(self, a):
        Z = np.power(self.__abs__(), a / 2)
        theta = np.arctan2(self.y, self.x)
        return Complex(Z * np.cos(a * theta), Z * np.sin(a * theta))

    @staticmethod
    def exp(a):
        return Complex(np.cos(a), np.sin(a))

    def getX(self):
        return self.x

    def getY(self):
        return self.y
