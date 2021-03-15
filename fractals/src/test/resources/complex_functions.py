import numpy as np
from complex_struct import Complex
import matplotlib.pyplot as plt


# functions

def sin(z: Complex):
    return Complex(np.sin(z.x) * np.cosh(z.y), np.cos(z.x) * np.sinh(z.y))


def cos(z: Complex):
    return Complex(np.cos(z.x) * np.cosh(z.y), -np.sin(z.x) * np.sinh(z.y))


# f(z)
def f(z: Complex):
    return sin(z)


N = 100
M = 2 * np.pi * 1
T = np.linspace(0, M, N)
S = [Complex.exp(t) for t in T]
Z = [f(s) for s in S]

a = [z.x for z in Z]
b = [z.y for z in Z]

plt.plot(a, b)
plt.show()
