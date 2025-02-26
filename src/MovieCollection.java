import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class MovieCollection
{
    private ArrayList<Movie> movies;
    private Scanner scanner;

    public MovieCollection(String fileName)
    {
        importMovieList(fileName);
        scanner = new Scanner(System.in);
    }

    public ArrayList<Movie> getMovies()
    {
        return movies;
    }

    public void menu()
    {
        String menuOption = "";

        System.out.println("Welcome to the movie collection!");
        System.out.println("Total: " + movies.size() + " movies");

        while (!menuOption.equals("q"))
        {
            System.out.println("------------ Main Menu ----------");
            System.out.println("- search (t)itles");
            System.out.println("- search (k)eywords");
            System.out.println("- search (c)ast");
            System.out.println("- see all movies of a (g)enre");
            System.out.println("- list top 50 (r)ated movies");
            System.out.println("- list top 50 (h)igest revenue movies");
            System.out.println("- (q)uit");
            System.out.print("Enter choice: ");
            menuOption = scanner.nextLine();

            if (!menuOption.equals("q"))
            {
                processOption(menuOption);
            }
        }
    }

    private void processOption(String option)
    {
        if (option.equals("t"))
        {
            searchTitles();
        }
        else if (option.equals("c"))
        {
            searchCast();
        }
        else if (option.equals("k"))
        {
            searchKeywords();
        }
        else if (option.equals("g"))
        {
            listGenres();
        }
        else if (option.equals("r"))
        {
            listHighestRated();
        }
        else if (option.equals("h"))
        {
            listHighestRevenue();
        }
        else
        {
            System.out.println("Invalid choice!");
        }
    }

    private void searchTitles()
    {
        System.out.print("Enter a title search term: ");
        String searchTerm = scanner.nextLine();

        // prevent case sensitivity
        searchTerm = searchTerm.toLowerCase();

        // arraylist to hold search results
        ArrayList<Movie> results = new ArrayList<Movie>();

        // search through ALL movies in collection
        for (int i = 0; i < movies.size(); i++)
        {
            String movieTitle = movies.get(i).getTitle();
            movieTitle = movieTitle.toLowerCase();

            if (movieTitle.indexOf(searchTerm) != -1)
            {
                //add the Movie objest to the results list
                results.add(movies.get(i));
            }
        }

        // sort the results by title
        sortResults(results);

        // now, display them all to the user
        for (int i = 0; i < results.size(); i++)
        {
            String title = results.get(i).getTitle();

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + title);
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = results.get(choice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void sortResults(ArrayList<Movie> listToSort)
    {
        for (int j = 1; j < listToSort.size(); j++)
        {
            Movie temp = listToSort.get(j);
            String tempTitle = temp.getTitle();

            int possibleIndex = j;
            while (possibleIndex > 0 && tempTitle.compareTo(listToSort.get(possibleIndex - 1).getTitle()) < 0)
            {
                listToSort.set(possibleIndex, listToSort.get(possibleIndex - 1));
                possibleIndex--;
            }
            listToSort.set(possibleIndex, temp);
        }
    }

    private void displayMovieInfo(Movie movie)
    {
        System.out.println();
        System.out.println("Title: " + movie.getTitle());
        System.out.println("Tagline: " + movie.getTagline());
        System.out.println("Runtime: " + movie.getRuntime() + " minutes");
        System.out.println("Year: " + movie.getYear());
        System.out.println("Directed by: " + movie.getDirector());
        System.out.println("Cast: " + movie.getCast());
        System.out.println("Overview: " + movie.getOverview());
        System.out.println("User rating: " + movie.getUserRating());
        System.out.println("Box office revenue: " + movie.getRevenue());
    }

    private void searchCast() {
        System.out.print("Enter a cast search term: ");
        String searchTerm = scanner.nextLine().toLowerCase();

        ArrayList<String> peopleWSearchTerm = new ArrayList<>();

        for (Movie movie : movies) {
            String[] castArray = movie.getCast().split("\\|");

            for (String member : castArray) {
                member = member.trim().toLowerCase();

                // Add member if it contains searchTerm and is not already in the list
                if (member.contains(searchTerm) && !peopleWSearchTerm.contains(member)) {
                    peopleWSearchTerm.add(member);
                }
            }
        }

        sortString(peopleWSearchTerm);

        for (int i = 0; i < peopleWSearchTerm.size(); i++)
        {
            String title = peopleWSearchTerm.get(i);
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + title);
        }

        System.out.println("Which cast member would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        String selectedCast = peopleWSearchTerm.get(choice - 1);
        ArrayList<String> moviesWCast = new ArrayList<>();


        for (Movie m : movies) {
            String[] castArray = m.getCast().split("\\|"); // Split cast into an array

            for (String member : castArray) {
                if (member.trim().equalsIgnoreCase(selectedCast)) { // Exact match (case insensitive)
                    moviesWCast.add(m.getTitle());
                }
            }
        }

        for (int i = 0; i < moviesWCast.size(); i++)
        {
            String title = moviesWCast.get(i);
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + title);
        }
        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int movieSelect = scanner.nextInt();
        scanner.nextLine();

        String selectedMovie = moviesWCast.get(movieSelect - 1);

        for (Movie m : movies) {
            if (m.getTitle().toLowerCase().contains(selectedMovie.toLowerCase())) {
                displayMovieInfo(m);
                break;
            }
        }

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void sortString(ArrayList<String> listToSort) {
        for (int j = 1; j < listToSort.size(); j++) {
            String temp = listToSort.get(j);

            int possibleIndex = j;
            while (possibleIndex > 0 && temp.compareTo(listToSort.get(possibleIndex - 1)) < 0) {
                listToSort.set(possibleIndex, listToSort.get(possibleIndex - 1));
                possibleIndex--;
            }
            listToSort.set(possibleIndex, temp);
        }
    }

    private void searchKeywords()
    {
        System.out.print("Enter keyword search term: ");
        String searchTerm = scanner.nextLine();

        // prevent case sensitivity
        searchTerm = searchTerm.toLowerCase();

        // arraylist to hold search results
        ArrayList<Movie> results = new ArrayList<Movie>();

        // search through ALL movies in collection
        for (int i = 0; i < movies.size(); i++)
        {
            String movieKeyword = movies.get(i).getKeywords();
            movieKeyword = movieKeyword.toLowerCase();

            if (movieKeyword.indexOf(searchTerm) != -1)
            {
                //add the Movie object to the results list
                results.add(movies.get(i));
            }
        }

        // sort the results by title
        sortResults(results);

        for (int i = 0; i < results.size(); i++)
        {
            String keyword = results.get(i).getTitle();

            // this will print index 0 as choice 1 in the results list
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + keyword);
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = results.get(choice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void listGenres()
    {
        ArrayList<String> listOfUniqueGenres = new ArrayList<>();

        for (Movie movie : movies) {
            String[] genreArray = movie.getGenres().split("\\|");

            for (String genre : genreArray) {
                genre = genre.trim().toLowerCase();

                // Add genre if it contains searchTerm and is not already in the list
                if (!listOfUniqueGenres.contains(genre)) {
                    listOfUniqueGenres.add(genre);
                }
            }
        }

        sortString(listOfUniqueGenres);

        for (int i = 0; i < listOfUniqueGenres.size(); i++)
        {
            String title = listOfUniqueGenres.get(i);
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + title);
        }

        System.out.println("Which genre would you like to learn more about?");
        System.out.print("Enter number: ");

        int genreSelect = scanner.nextInt();
        scanner.nextLine();

        String selectedGenre = listOfUniqueGenres.get(genreSelect - 1);

        ArrayList<Movie> moviesWSelectedGenre = new ArrayList<>();

        for (Movie m : movies) {
            if (m.getGenres().toLowerCase().contains(selectedGenre.toLowerCase())) {
                moviesWSelectedGenre.add(m);
            }
        }

        for (int i = 0; i < moviesWSelectedGenre.size(); i++)
        {
            String title = moviesWSelectedGenre.get(i).getTitle();
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + title);
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int movieSelect = scanner.nextInt();
        scanner.nextLine();

        Movie selected = moviesWSelectedGenre.get(movieSelect - 1);
        displayMovieInfo(selected);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void sortRating(ArrayList<Movie> listToSort) {
        for (int j = 1; j < listToSort.size(); j++) {
            double temp = listToSort.get(j).getUserRating();
            Movie m = listToSort.get(j);
            int possibleIndex = j;

            while (possibleIndex > 0 && temp < listToSort.get(possibleIndex - 1).getUserRating()) {
                listToSort.set(possibleIndex, listToSort.get(possibleIndex - 1));
                possibleIndex--;
            }

            listToSort.set(possibleIndex, m);
        }
    }

    private void sortRevenue(ArrayList<Movie> listToSort) {
        for (int j = 1; j < listToSort.size(); j++) {
            double temp = listToSort.get(j).getRevenue();
            Movie m = listToSort.get(j);
            int possibleIndex = j;

            while (possibleIndex > 0 && temp < listToSort.get(possibleIndex - 1).getRevenue()) {
                listToSort.set(possibleIndex, listToSort.get(possibleIndex - 1));
                possibleIndex--;
            }

            listToSort.set(possibleIndex, m);
        }
    }

    private void listHighestRated()
    {
        ArrayList<Movie> temp = movies;
        ArrayList<Movie> top50 = new ArrayList<>();

        sortRating(temp);

        for (int i = temp.size() - 1; i > temp.size() - 51; i--) {
            top50.add(temp.get(i));
        }

        for (int i = 0; i < top50.size(); i++)
        {
            String title = top50.get(i).getTitle();
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + title);
        }
        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = top50.get(choice - 1);

        displayMovieInfo(selectedMovie);
    }

    private void listHighestRevenue()
    {
        ArrayList<Movie> temp = movies;
        ArrayList<Movie> top50 = new ArrayList<>();

        sortRevenue(temp);

        for (int i = temp.size() - 1; i > temp.size() - 51; i--) {
            top50.add(temp.get(i));
        }

        for (int i = 0; i < top50.size(); i++)
        {
            String title = top50.get(i).getTitle();
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + title);
        }
        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = top50.get(choice - 1);

        displayMovieInfo(selectedMovie);
    }

    private void importMovieList(String fileName)
    {
        try
        {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();

            movies = new ArrayList<Movie>();

            while ((line = bufferedReader.readLine()) != null)
            {
                String[] movieFromCSV = line.split(",");

                String title = movieFromCSV[0];
                String cast = movieFromCSV[1];
                String director = movieFromCSV[2];
                String tagline = movieFromCSV[3];
                String keywords = movieFromCSV[4];
                String overview = movieFromCSV[5];
                int runtime = Integer.parseInt(movieFromCSV[6]);
                String genres = movieFromCSV[7];
                double userRating = Double.parseDouble(movieFromCSV[8]);
                int year = Integer.parseInt(movieFromCSV[9]);
                int revenue = Integer.parseInt(movieFromCSV[10]);

                Movie nextMovie = new Movie(title, cast, director, tagline, keywords, overview, runtime, genres, userRating, year, revenue);
                movies.add(nextMovie);
            }
            bufferedReader.close();
        }
        catch(IOException exception)
        {
            // Print out the exception that occurred
            System.out.println("Unable to access " + exception.getMessage());
        }
    }
}