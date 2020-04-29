import matplotlib.pyplot as plt
import numpy as np

a = [
(0.0,99.0),(0.5,98.53723251071018),(1.0,97.9204698125747),(1.5,97.41548196630804),(2.0,97.59640155694083),(2.5,98.4856921804634),(3.0,98.45735112710238),(3.5,97.89121476121596),(4.0,98.04139526983748),(4.5,97.69491250533224),(5.0,97.47832705170418),(5.5,97.29056938232999),(6.0,97.13243656878899),(6.5,97.23064864441083),(7.0,97.483902319858),(7.5,97.36196051414477),(8.0,97.24369959638308),(8.5,97.41799643725881),(9.0,97.34373530685438),(9.5,96.99055483311184),(10.0,97.53199251531967),(10.5,97.12973531196204),(11.0,96.63067254742543),(11.5,96.7547722309852),(12.0,96.54569138885961),(12.5,96.41469425259861),(13.0,96.57520230448786),(13.5,96.77104665073618),(14.0,96.27286989574561),(14.5,96.42302386743789),(15.0,96.06745209849254),(15.5,95.50724011421647),(16.0,95.49045134262263),(16.5,95.16865175171284),(17.0,95.01755405772849),(17.5,94.13562718580182),(18.0,93.78992005678188),(18.5,94.09840790304733),(19.0,94.08394058769304),(19.5,94.20727202039335),(20.0,93.6694723004001),(20.5,93.49176144862022),(21.0,93.42483320292017),(21.5,92.8437597569423),(22.0,92.83709739339585),(22.5,93.08812746202578),(23.0,92.58269072308458),(23.5,92.53950382288477),(24.0,92.84982330394568),(24.5,92.25768129716161),(25.0,92.319919424077),(25.5,92.19492562727797),(26.0,92.16084260216942),(26.5,91.79268354659462),(27.0,91.97660435596119),(27.5,92.31679869297508),(28.0,91.81289155193447),(28.5,91.1135705789276),(29.0,91.05112803210535),(29.5,91.54956875957613),(30.0,91.83790466925788),(30.5,91.79064139180223),(31.0,91.09252597432415),(31.5,91.51760248533475),(32.0,91.47199812406858),(32.5,91.0526558712973),(33.0,90.52457292868769),(33.5,90.47720580593159),(34.0,90.12191247871839),(34.5,90.16482859686377),(35.0,90.43696195859195),(35.5,90.43914859453503),(36.0,90.84992974504073),(36.5,90.76730288854873),(37.0,90.20852325012382),(37.5,90.43432978279792),(38.0,90.27720361281429),(38.5,90.12994692013703),(39.0,89.89850821725865),(39.5,89.44077745068135),(40.0,88.73111183621518),(40.5,88.41538528686847),(41.0,88.00200671581914),(41.5,87.93021170097191),(42.0,88.03670510441005),(42.5,88.20742532438823),(43.0,87.3874230742406),(43.5,86.79598396902523),(44.0,86.42537708822388),(44.5,86.17136595533323),(45.0,85.79795380437471),(45.5,85.3276042348742),(46.0,85.68849843775348),(46.5,85.8108090306685),(47.0,85.57513035022437),(47.5,85.97065800392495),(48.0,85.85564847480846),(48.5,85.77268878942924),(49.0,85.89870387464458),(49.5,85.61584293959383),(50.0,85.61865510897792),(50.5,85.88757741687438),(51.0,86.36600359431762),(51.5,85.42509212611456),(52.0,86.29098831635554),(52.5,85.91637790049154),(53.0,85.50792354163772),(53.5,85.33761676196127),(54.0,85.79274741496744),(54.5,85.32063432927421),(55.0,85.29987855968791),(55.5,85.1726787361742),(56.0,84.59535429301413),(56.5,83.9870309064026),(57.0,83.8393454307111),(57.5,84.37308411615561),(58.0,84.59849267445331),(58.5,84.62493319185738),(59.0,85.2448842014971),(59.5,84.76457612984406),(60.0,85.10430717773792),(60.5,84.81632086645055),(61.0,84.30122285627513),(61.5,84.40605315430926),(62.0,84.51604189894202),(62.5,84.7120177487707),(63.0,84.44411017121176),(63.5,84.7049608494029),(64.0,84.12189023484565),(64.5,84.59165731239072),(65.0,84.00091289799127),(65.5,83.84421517176945),(66.0,83.09630786715645),(66.5,82.94212433141288),(67.0,82.68154388331207),(67.5,82.83006531403117),(68.0,82.89926555885914),(68.5,83.2242956460266),(69.0,83.74727344932485),(69.5,83.86276863186194),(70.0,83.64602613730925),(70.5,84.33653082852167),(71.0,83.92908246055337),(71.5,83.77225197300908),(72.0,83.78272442965219),(72.5,84.01556085750295),(73.0,84.3038215200387),(73.5,84.23117226090025),(74.0,84.26500146267725),(74.5,84.3472436974382),(75.0,84.07677241596895),(75.5,83.79804072847551),(76.0,83.90162885944108),(76.5,83.54524099737851),(77.0,83.3840102316203),(77.5,83.63595055747057),(78.0,83.82230871701172),(78.5,83.69425879207397),(79.0,83.47010275351474),(79.5,83.33355602832222),(80.0,83.91676074361902),(80.5,83.28776108610207),(81.0,83.40200544275179),(81.5,83.57417452712572),(82.0,83.32450676135437),(82.5,83.63763851246976),(83.0,83.16999911310671),(83.5,83.68772341981571),(84.0,83.24352892307479),(84.5,83.51852745805104),(85.0,82.81651973982949),(85.5,82.87470700045692),(86.0,82.64358898334376),(86.5,83.22466400861772),(87.0,83.07767742846083),(87.5,82.6280882516111),(88.0,82.34579140924757),(88.5,81.80306856175733),(89.0,82.31534906388343),(89.5,82.53275684117315),(90.0,82.00196129336821),(90.5,81.45713747107642),(91.0,81.4273415318093),(91.5,81.9624910244513),(92.0,81.49967435329546),(92.5,81.39785842610756),(93.0,81.26257804239981),(93.5,81.43535036373562),(94.0,81.38785498511513),(94.5,81.66054564348374),(95.0,81.69201455820317),(95.5,81.29559500470491),(96.0,80.99375114401137),(96.5,81.63210562353342),(97.0,81.04369973929327),(97.5,81.53819202298735),(98.0,81.7914879805321),(98.5,81.90673646198633),(99.0,81.89004899548002),(99.5,82.26411995942144),(100.0,1.0)
    ]
a = np.array(a)

plt.xlim(-10,110)
plt.ylim(-10,110)
# plt.scatter(height,weight,s=area,c=colors)
plt.plot(a[:,0], a[:,1])
plt.plot(a[0][0], a[0][1], 'go')
plt.plot(a[-1][0], a[-1][1] ,'go')

# file = open('C:\Users\20161\Desktop\BTL_Lab\src\M_ExposurePath\test.txt')
file = open("E:\\Documents\\20192\\EvoComputation\\pso_maximal_exposure\\giangdl\\data\\test.txt")
# doc file
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

plt.show()

file.close()