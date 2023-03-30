package softwaredesign;

//https://github.com/fauxpilot/fauxpilot

import java.util.*;

public class RankingContributorWeekday extends Application implements Command{
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

    private String[] args;

    private static void countCommits(HashMap<String, Integer> dayAuthors, List<String> authors, Integer index){
        if (dayAuthors.containsKey(authors.get(index))){
            dayAuthors.put(authors.get(index), dayAuthors.get(authors.get(index))+1);
        }
        else{
            dayAuthors.put(authors.get(index), 1);
        }
    }

    @Override
    public void setArgs(String[] args) {
        this.args = args;
    }

    @Override
    public Boolean execute(GitLog log) {
        if (args.length != 0) {
            System.err.println("DEBUG: There should be no args here");
            return Boolean.FALSE;
        } else {
            List<GitCommit> repoCommits = log.getCommits();

            List<String> authors = new ArrayList<>();
            for (GitCommit commit: repoCommits){
                authors.add(commit.getAuthor());
            }

            List<Integer> dates = new ArrayList<>();

            for (GitCommit commit: repoCommits){
                long unixDate = commit.getUnixDate();;
                Date date = new java.util.Date(unixDate * 1000L);
                dates.add(date.getDay());
            }

            HashMap<String, Integer> monAuthors = new HashMap<>();
            HashMap<String, Integer> tueAuthors = new HashMap<>();
            HashMap<String, Integer> wedAuthors = new HashMap<>();
            HashMap<String, Integer> thuAuthors = new HashMap<>();
            HashMap<String, Integer> friAuthors = new HashMap<>();


            for (int i = 0; i < dates.size(); i++){
                if(dates.get(i) == 1){
                    countCommits(monAuthors, authors, i);
                }
                else if(dates.get(i) == 2){
                    countCommits(tueAuthors, authors, i);
                }
                else if(dates.get(i) == 3){
                    countCommits(wedAuthors, authors, i);
                }
                else if(dates.get(i) == 4){
                    countCommits(thuAuthors, authors, i);
                }
                else if(dates.get(i) == 5){
                    countCommits(friAuthors, authors, i);
                }
            }

            TreeMap<String, Integer> rankedMonAuthors = new TreeMap<>(new RankingContributorWeekday.MapValueSorter(monAuthors));
            TreeMap<String, Integer> rankedTueAuthors = new TreeMap<>(new RankingContributorWeekday.MapValueSorter(tueAuthors));
            TreeMap<String, Integer> rankedWedAuthors = new TreeMap<>(new RankingContributorWeekday.MapValueSorter(wedAuthors));
            TreeMap<String, Integer> rankedThuAuthors = new TreeMap<>(new RankingContributorWeekday.MapValueSorter(thuAuthors));
            TreeMap<String, Integer> rankedFriAuthors = new TreeMap<>(new RankingContributorWeekday.MapValueSorter(friAuthors));

            rankedMonAuthors.putAll(monAuthors);
            rankedTueAuthors.putAll(tueAuthors);
            rankedWedAuthors.putAll(wedAuthors);
            rankedThuAuthors.putAll(thuAuthors);
            rankedFriAuthors.putAll(friAuthors);

            System.out.println("\nList of contributors ranked by who contributed most on the given weekdays: ");

            if(rankedMonAuthors.size() == 0){
                System.out.println("No one contributed on Mondays");
            }
            else {
                System.out.println("\nMondays: ");
                for (HashMap.Entry<String, Integer> entry: rankedMonAuthors.entrySet()){
                    System.out.println(entry.getKey() + " : "  + entry.getValue());
                }
            }

            if(rankedTueAuthors.size() == 0){
                System.out.println("No one contributed on Tuesdays");
            }
            else {
                System.out.println("\nTuesdays: ");
                for (HashMap.Entry<String, Integer> entry: rankedTueAuthors.entrySet()){
                    System.out.println(entry.getKey() + " : "  + entry.getValue());
                }
            }

            if(rankedWedAuthors.size() == 0){
                System.out.println("No one contributed on Wednesdays");
            }
            else {
                System.out.println("\nWednesdays: ");
                for (HashMap.Entry<String, Integer> entry: rankedWedAuthors.entrySet()){
                    System.out.println(entry.getKey() + " : "  + entry.getValue());
                }
            }

            if(rankedThuAuthors.size() == 0){
                System.out.println("No one contributed on Thursdays");
            }
            else {
                System.out.println("\nThursdays: ");
                for (HashMap.Entry<String, Integer> entry: rankedThuAuthors.entrySet()){
                    System.out.println(entry.getKey() + " : "  + entry.getValue());
                }
            }

            if(rankedFriAuthors.size() == 0){
                System.out.println("No one contributed on Fridays");
            }
            else {
                System.out.println("\nFridays: ");
                for (HashMap.Entry<String, Integer> entry: rankedFriAuthors.entrySet()){
                    System.out.println(entry.getKey() + " : "  + entry.getValue());
                }
                System.out.println("\n");
            }

            return Boolean.TRUE;
        }
    }
}
