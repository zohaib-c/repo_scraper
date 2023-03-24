package softwaredesign;

import java.util.Comparator;
import java.util.HashMap;

class MapValueSorter implements Comparator<String> {
    HashMap<String, Integer> uniqueAuthors;

    public MapValueSorter(HashMap<String, Integer> uniqueAuthors){
        this.uniqueAuthors = uniqueAuthors;
    }

    @Override
    public int compare(String a, String b) {
        if (uniqueAuthors.get(a) >= uniqueAuthors.get(b)){
            return -1;
        }
        else if (uniqueAuthors.get(a) <= uniqueAuthors.get(b)){
            return 1;
        }
        else{
            return 0;
        }
    }
}