import matplotlib.pyplot as plt
import numpy as np

a = []

fileout = open('C:\\Users\\giang.dl161164\\Desktop\\BTL_TTTH_PSO\\output\\output_temp_10.txt')

data = fileout.read()
lines = data.split('\n')

N = int(lines[0])
M = int(lines[1])
C = int(lines[2])
print(N) # N = 201
print(M) # M = 200
print(C) # C = 1001

st = 3
end = C + 3
it = 0
j = 0
c = 0
# print(N)
while it < N:
    j = 0
    # ve sensor network (tuong ung moi the he)
    plt.xlim(-10,110)
    plt.ylim(-10,110)
    file = open('C:\\Users\\giang.dl161164\\Desktop\\BTL_TTTH_PSO\\data\\10.txt')
    
    data = file.read()
    lines1 = data.split('\n')

    N8 = int(lines1[0])
    # print(N)

    circle = []
    for i in range(1, N8+1):
        line1 = lines1[i]
        parts = line1.split()
        x = float(parts[0])
        y = float(parts[1])
        r = float(parts[2])
        # print(x)
        # print('Sensor ',i, ': x = ', x, ', y = ', y, ', r = ',r)
        circle1 = plt.Circle((x, y), r, color='r')
        ax = plt.gca()
        ax.add_artist(circle1)
    
    file.close()

    while ( j < M ): # ve quan the cua moi the he
        for i in range(st, end): # ve tung ca the
            line = lines[i]
            parts = line.split()
            x8 = float(parts[0])
            y8 = float(parts[1])
            a.append((x8, y8))
            # if i == st:
            #     print(x8)
        
        a = np.array(a)

        plt.plot(a[:,0], a[:,1])
        plt.plot(a[0][0], a[0][1], 'go')
        plt.plot(a[-1][0], a[-1][1] ,'go')
        # print(a)
        a = []
        # print("--------")
        st = end
        end = st + C
        j = j + 1
    
    print(it)
    
    # plt.show()
    filesave = 'C:\\Users\\giang.dl161164\\Desktop\\BTL_TTTH_PSO\\output_temp_png\\' + str(it) + '.png'
    it = it + 1
    plt.savefig(filesave)
    plt.clf()
    c = c + 1

print(it)
fileout.close()