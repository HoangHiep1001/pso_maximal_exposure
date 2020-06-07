import matplotlib.pyplot as plt
import numpy as np

a = []

fileout = open('C:\\Users\\giang.dl161164\\Desktop\\BTL_TTTH_PSO\\output\\output_temp.txt')

data = fileout.read()
lines = data.split('\n')

N = int(lines[0])
M = int(lines[1])
C = int(lines[2])

st = 3
end = C + 3

while 1:
    plt.xlim(-10,110)
    plt.ylim(-10,110)
    file = open('C:\\Users\\giang.dl161164\\Desktop\\BTL_TTTH_PSO\\data\\test.txt')
    
    data = file.read()
    lines = data.split('\n')

    N = int(lines[0])
    print(N)

    circle = []
    for i in range(1, N+1):
        line = lines[i]
        parts = line.split()
        x = float(parts[0])
        y = float(parts[1])
        r = float(parts[2])
        # print(x)
        print('Sensor ',i, ': x = ', x, ', y = ', y, ', r = ',r)
        circle1 = plt.Circle((x, y), r, color='r')
        ax = plt.gca()
        ax.add_artist(circle1)

    while 1:
        for i in range(st, end):
            line = lines[i]
            parts = line.split()
            x8 = float(parts[0])
            y8 = float(parts[1])
            a.append((x8, y8))
        
        a = np.array(a)

        plt.plot(a[:,0], a[:,1])
        plt.plot(a[0][0], a[0][1], 'go')
        plt.plot(a[-1][0], a[-1][1] ,'go')
        a = []

        if end == (3 + M * C):
            break

        st = end + 1
        end = st + C

    plt.show()






# print(N)
c = 0
circle = []
for i in range(1, N+1):
    line = lines[i]
    c = 1
    parts = line.split()
    x8 = float(parts[0])
    y8 = float(parts[1])
    a.append((x8, y8))
    
a = np.array(a)
fileout.close()

plt.xlim(-10,110)
plt.ylim(-10,110)
# plt.scatter(height,weight,s=area,c=colors)
plt.plot(a[:,0], a[:,1])
plt.plot(a[0][0], a[0][1], 'go')
plt.plot(a[-1][0], a[-1][1] ,'go')


    




plt.show()

file.close()