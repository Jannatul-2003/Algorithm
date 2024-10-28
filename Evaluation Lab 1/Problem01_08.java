import java.util.*;
import java.io.*;

class City {
    int cityName;
    int distance = 0;
    City parent = null;

    enum Color {
        WHITE, GREY, BLACK;
    }

    Color color = Color.WHITE;
    Set<City> neighbours = new HashSet<>();

    City(int cityName) {
        this.cityName = cityName;
    }
}

class CityGraph {
    HashMap<Integer, City> map = new HashMap<>();

    City getCity(int a) {
        if (!map.containsKey(a))
            map.put(a, new City(a));
        return map.get(a);
    }

    void addRoad(int c1, int c2) {
        City c1City = getCity(c1);
        City c2City = getCity(c2);
        c1City.neighbours.add(c2City);
        c2City.neighbours.add(c1City);
    }
}

public class Problem01_08  {
    public static void main(String[] args) throws FileNotFoundException{
        CityGraph cg = new CityGraph();
        try{
        File f=new File("input1.txt");
        Scanner sc = new Scanner(f);
    
        int n = sc.nextInt();
        int m = sc.nextInt();
        int k = sc.nextInt();
        int c = sc.nextInt();
        while (m-- != 0) {
            int c1 = sc.nextInt();
            int c2 = sc.nextInt();
            cg.addRoad(c1, c2);
        }
        City currentCity = cg.getCity(c);
        Queue<City> queue = new LinkedList<City>();
        queue.add(currentCity);
        currentCity.color = City.Color.GREY;
        int count=0;
        while (!queue.isEmpty()) {
            City parentCity = queue.poll();
            for (City neighbour : parentCity.neighbours) {
                if (neighbour.color == City.Color.WHITE) {
                    queue.add(neighbour);
                    neighbour.color = City.Color.GREY;
                    neighbour.distance = parentCity.distance + 1;
                    neighbour.parent = parentCity;
                }
            }
            parentCity.color = City.Color.BLACK;
            //System.out.println(parentCity.cityName);
            if(parentCity.distance<=k)
                count++;
        }
        System.out.println(count);
    }
    catch(FileNotFoundException e){
        System.out.println("File not found");
    }
    }

}

