package softwaredesign;

//https://github.com/fauxpilot/fauxpilot
//please let me push

import java.util.*;

public class RankingContributorWeekday extends RankingContributor implements Command{
    static class MapValueSorter implements Comparator<String> {
        HashMap<String, Integer> uniqueAuthors;

        public MapValueSorter(HashMap<String, Integer> uniqueAuthors){
            this.uniqueAuthors = uniqueAuthors;
        }

        @Override
        public int compare(String a, String b) {
            return uniqueAuthors.get(b).compareTo(uniqueAuthors.get(a));
        }
    }

    private static final List<String> authors = new ArrayList<>();

    private static final List<Integer> dates = new ArrayList<>();

    private final HashMap<String, Integer> monAuthors = new HashMap<>();
    private final HashMap<String, Integer> tueAuthors = new HashMap<>();
    private final HashMap<String, Integer> wedAuthors = new HashMap<>();
    private final HashMap<String, Integer> thuAuthors = new HashMap<>();
    private final HashMap<String, Integer> friAuthors = new HashMap<>();

    private void sumWeekdayCommits(){
        for (int i = 0; i < dates.size(); i++){
            if(dates.get(i) == 1){
                countCommits(monAuthors, i);
            }
            else if(dates.get(i) == 2){
                countCommits(tueAuthors, i);
            }
            else if(dates.get(i) == 3){
                countCommits(wedAuthors,  i);
            }
            else if(dates.get(i) == 4){
                countCommits(thuAuthors, i);
            }
            else if(dates.get(i) == 5){
                countCommits(friAuthors, i);
            }
        }
    }

    private String[] args;

    private static void countCommits(HashMap<String, Integer> dayAuthors, Integer index){
        if (dayAuthors.containsKey(authors.get(index))){
            dayAuthors.put(authors.get(index), dayAuthors.get(authors.get(index))+1);
        }
        else{
            dayAuthors.put(authors.get(index), 1);
        }
    }

    private static void printResult(TreeMap<String, Integer> dayAuthors, String day){
        int counter = 0;
        int limit;

        if (dayAuthors.size() < LIMIT) limit = dayAuthors.size();
        else limit = LIMIT;

        if(dayAuthors.size() == 0){
            System.out.println("No one contributed on " + day);
        }
        else {
            System.out.println("\n" + day + ": ");
            for (Map.Entry<String, Integer> entry: dayAuthors.entrySet()){
                if (counter == limit) break;
                System.out.println(counter + 1 + ". " + entry.getKey() + " : "  + entry.getValue());
                counter++;
            }
            System.out.println("\n");
        }
    }

    @Override
    public void setArgs(String[] args) {
        this.args = args;
    }

    @Override
    public Boolean execute(GitLog log) {
        if (args.length != 0) {
            return Boolean.FALSE;
        } else {
            List<GitCommit> repoCommits = log.getCommits();

            //Get list of commit authors
            for (GitCommit commit: repoCommits){
                authors.add(commit.getAuthor());
            }

            //Get the day of the week when the commit was made
            for (GitCommit commit: repoCommits){
                long unixDate = commit.getUnixDate();
                Date date = new java.util.Date(unixDate * 1000L);
                dates.add(date.getDay());
            }

            sumWeekdayCommits();

            TreeMap<String, Integer> rankedMonAuthors = new TreeMap<>(new MapValueSorter(monAuthors));
            TreeMap<String, Integer> rankedTueAuthors = new TreeMap<>(new MapValueSorter(tueAuthors));
            TreeMap<String, Integer> rankedWedAuthors = new TreeMap<>(new MapValueSorter(wedAuthors));
            TreeMap<String, Integer> rankedThuAuthors = new TreeMap<>(new MapValueSorter(thuAuthors));
            TreeMap<String, Integer> rankedFriAuthors = new TreeMap<>(new MapValueSorter(friAuthors));

            //Sort
            rankedMonAuthors.putAll(monAuthors);
            rankedTueAuthors.putAll(tueAuthors);
            rankedWedAuthors.putAll(wedAuthors);
            rankedThuAuthors.putAll(thuAuthors);
            rankedFriAuthors.putAll(friAuthors);

            System.out.println("\nList of contributors ranked by who contributed most on the given weekdays: ");

            printResult(rankedMonAuthors, "Mondays");
            printResult(rankedTueAuthors, "Tuesdays");
            printResult(rankedWedAuthors, "Wednesdays");
            printResult(rankedThuAuthors, "Thursdays");
            printResult(rankedFriAuthors, "Fridays");

            History.getInstance().push("ranking contributor weekday");

            return Boolean.TRUE;
        }
    }
}
