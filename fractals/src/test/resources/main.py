import numpy as np
import matplotlib.pyplot as plt
from complex_struct import Complex

# points = [[0, 1], [1, 2], [3, 2]]
# x = [p[0] for p in points]
# y = [p[1] for p in points]

k = 5
# Xlist = np.linspace(0, 1, k + 1)
# Ylist = [1, 2, 2, 3, 3, 2, 1, 2, 2, 1, 2]

Xlist = [Complex(np.cos(a), np.sin(a)) for a in np.linspace(0, 2 * np.pi, k + 2)]
Xlist.pop(-1)
Ylist = [Complex(np.cos(a) + np.sin(2 * a), np.cos(a) + np.sin(a)) for a in np.linspace(0, 2 * np.pi, k + 2)]
Ylist.pop(-1)

# print points
# print([c.getX() for c in Xlist])
# print([c.getY() for c in Xlist])
# plt.plot([c.getX() for c in Xlist], [c.getY() for c in Xlist], 'go')

print([c.getX() for c in Ylist])
print([c.getY() for c in Ylist])
plt.plot([c.getX() for c in Ylist], [c.getY() for c in Ylist], 'yo')


# newton
#######################################################
# def diff(b: int, e: int):
#     if b == e:
#         return Ylist[b]
#     return (diff(b + 1, e) - diff(b, e - 1)) / (Xlist[e] - Xlist[b])
#
#
# def a(j):
#     return diff(0, j)
#
#
# def n(j, x):
#     prod = Complex(1, 0)
#     for _i in range(0, j):  # [0, j-1]
#         prod *= x - Xlist[_i]
#     return prod
#
#
# def f(x):
#     sum = Complex(0, 0)
#     for _j in range(0, k + 1):  # [0, k]
#         sum += a(_j) * n(_j, x)
#     return sum
#
#
# var_x = [Complex(np.cos(a), np.sin(a)) for a in np.linspace(0, 2 * np.pi, 100)]
# xx = [c.getX() for c in var_x]
# xy = [c.getY() for c in var_x]
# plt.plot(xx, xy, 'b')
#
# var_y = [f(e) for e in var_x]
# yx = [c.getX() for c in var_y]
# yy = [c.getY() for c in var_y]
# plt.plot(yx, yy, 'r')
#######################################################

# julia
#######################################################
def w(z):
    prod = Complex(1, 0)
    for _i in range(0, k + 1):  # [0, k]
        prod *= (z - Ylist[_i]) / Complex(1, 1)
    return z * (prod + Complex(1, 0))


var_x = [Complex(np.cos(a), np.sin(a)) for a in np.linspace(0, 2 * np.pi, 100)]
# plt.plot([c.getX() for c in var_x], [c.getY() for c in var_x], 'b')

var_y = [w(e) for e in var_x]
plt.plot([c.getX() for c in var_y], [c.getY() for c in var_y], 'r')
#######################################################

plt.show()
