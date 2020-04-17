package main.java.data;

import main.java.model.Point;
import main.java.model.Sensor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GetData {
    public Point startPoint;
    public Point endPoint;

    public List<Sensor> initialFromFile(String fileName) throws Exception {
        List<Sensor> sensors = new ArrayList<>();
        Scanner sc = new Scanner(new BufferedReader(new FileReader(fileName)));
        String[] line = sc.nextLine().trim().split(" ");
        int n = Integer.parseInt(line[0]);
        for (int i = 0; i < n; i++) {
            line = sc.nextLine().trim().split(" ");
            Double x = Double.parseDouble(line[0]);
            Double y = Double.parseDouble(line[1]);
            Double r = Double.parseDouble(line[2]);
            Sensor sensor = new Sensor(x, y, r);
            sensors.add(sensor);
        }
        line = sc.nextLine().trim().split(" ");
        this.startPoint = new Point(Double.parseDouble(line[0]), Double.parseDouble(line[1]));

        line = sc.nextLine().trim().split(" ");
        this.endPoint = new Point(Double.parseDouble(line[0]), Double.parseDouble(line[1]));
        sc.close();
        return sensors;
    }
}
