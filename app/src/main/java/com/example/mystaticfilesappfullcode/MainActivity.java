package com.example.mystaticfilesappfullcode;

//Projects in Series 2:
//1. Build an App in Android Studio using Resources
//2. Build an App in Android Studio using Static Files
//3. Build an App in Android Studio using Read - Write
//4. Build an App in Android Studio using onTouch
//5. Build an App in Android Studio using Activities

//***THIS FILE WILL SHOW ERRORS UNTIL YOU HAVE COMPLETED THE TASKS YOU ARE REQUIRED TO DO.  WHEN YOU HAVE
//SUCCESSFULLY FINISHED THE TASK THE ERRORS SHOULD ALL BE GONE.***

//androidx.appcompat.app.AppCompatActivity and android.os.Bundle are put in by default when basic
//activity selected when new project is created in Android Studio. All of the other imports where
//put in manually later during the making of the project.
//START
import androidx.appcompat.app.AppCompatActivity;
import android.content.res.Resources;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
//END

public class MainActivity extends AppCompatActivity {
    //The strings below are used for persistent storage - remembering a user selections after the user
    //closes the app. Persistent storage is looked at in much more detail in another project, "Build
    //a Persistent Storage App in Android Studio", and we will not be looking at it here. You do not
    //have to do anything with this code.
    //START
    public static final String MYPREFS = "mySharedPreferences";
    private String savedAuthor;
    //END

    //Creating Java equivalent objects for the widgets in our user interface which we created in xml
    //that we want to interact with (change) or give values to in some way.
    //START
    private TextView txtPickedAuthor;
    private Spinner sprPickedAuthor;
    private ImageView imgPickedAuthor;
    private TextView txtPickedAuthorBook;
    private TextView txtPickedAuthorQuote;
    //END

    //We are creating six String arrays to store the quotes from the relevant text files when the when
    //the data is read from the text files.
    //START
    String[] defaultquote;
    String[] oscarwilde;
    String[] cslewis;
    String[] bramstoker;
    String[] jonathanswift;
    String[] samuelbeckett;
    //END

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Connect our java objects to the appropriate widget in our user interface
        //START
        txtPickedAuthor = findViewById(R.id.txtPickedAuthor);
        sprPickedAuthor = findViewById(R.id.sprAuthor);
        imgPickedAuthor = findViewById(R.id.imgAuthor);
        txtPickedAuthorBook = findViewById(R.id.txtPickedAuthorBook);
        txtPickedAuthorQuote = findViewById(R.id.txtPickedAuthorQuote);
        //END

        //Read the information from the six text files that we put in the raw directory and store the
        //information inside the appropriate String[] that we created. We identify a specific file in
        //the raw directory and send that file id to the loadQuotations method and then puts the information
        //returned back from the loadQuotations method into a specific String[] that we created earlier.
        //The loadQuotations method looks for the specific file, takes the data from inside the file,
        //and returns that data.
        //START
        defaultquote = loadQuotations(R.raw.defaultquote);
        oscarwilde = loadQuotations(R.raw.oscarwilde);
        cslewis = loadQuotations(R.raw.cslewis);
        bramstoker = loadQuotations(R.raw.bramstoker);
        jonathanswift = loadQuotations(R.raw.jonathanswift);
        samuelbeckett = loadQuotations(R.raw.samuelbeckett);
        //END

        //The four lines below are setting an image called questionmark from the res/drawable
        //folder to our ImageView imgPickedAuthor, a string called pickanauthor from res/values/strings
        //to txtPickedAuthor, a string called whoistheauthor from res/values/strings to txtPickedAuthorBook,
        //and a string called defaultquote to txtPickedAuthorQuote.
        //START
        imgPickedAuthor.setImageResource(R.drawable.questionmark);
        txtPickedAuthor.setText(R.string.pickanauthor);
        txtPickedAuthorBook.setText(R.string.whoistheauthor);
        txtPickedAuthorQuote.setText(Arrays.toString(defaultquote));
        //END

        //The below two lines are using array from resources (res/values/strings.xml) to fill in the
        //pieces of information that will be inside our Spinner (author names).
        //You do not have to do anything with this code.
        //START
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.authors, R.layout.support_simple_spinner_dropdown_item);
        sprPickedAuthor.setAdapter(adapter);
        //END

        //For a Spinner you can not use an onClickListener like you would for a Button.  You must
        //use setOnItemSelectedListener where you set the instructions for what to do when a item in
        //the Spinner is selected. There are two parts of an item selected listener, what to do when
        //an item is selected and what to do when no item is selected.
        //START
        sprPickedAuthor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //This line is checking what item in the Spinner has been selected based on the
                //position (index number) of the item.
                Object author = parent.getItemAtPosition(position);
                //The position number (same as all arrays in Java) starts at index 0. So "Pick an author"
                //would be at position 0 but it is easier for us to picture in our brains if the first
                //equals 1, second equals 2 etc. To do this we create an int called pickedAuthor and
                //give it the value of the position number plus 1.
                int pickedAuthor = position + 1;
                //We take the the value in picked author, change it to a String and give it as the
                //value of savedAuthor for use in Persistent Storage (remembering the selection
                //when the user closes the app.
                savedAuthor = String.valueOf(author).toLowerCase();
                //We are calling a method called getPickedAuthorBookAndQuote and sending it the name
                //of the selected author.
                getPickedAuthorBooksAndQuote(savedAuthor);
                //We are using a switch statement to check what number we just saved in pickedAuthor
                //and then we are changing the image used in the ImageView imgPickedAuthor so that it
                //matches the author that is selected.
                switch (pickedAuthor) {
                    case 2:  imgPickedAuthor.setImageResource(R.drawable.oscarwilde);    break;
                    case 3:  imgPickedAuthor.setImageResource(R.drawable.cslewis);       break;
                    case 4:  imgPickedAuthor.setImageResource(R.drawable.bramstoker);    break;
                    case 5:  imgPickedAuthor.setImageResource(R.drawable.jonathanswift); break;
                    case 6:  imgPickedAuthor.setImageResource(R.drawable.samuelbeckett); break;
                    default :  imgPickedAuthor.setImageResource(R.drawable.questionmark);  break;
                }
                //We are using a switch statement to check what number we just saved in pickedAuthor
                //and then we are changing the string used in txtPickedAuthor so that it matches the
                //author that is selected.
                switch (pickedAuthor) {
                    case 1: txtPickedAuthor.setText(R.string.pickanauthor);  break;
                    default : txtPickedAuthor.setText(String.valueOf(author)); break;
                }
            }

            //Nothing being selected in our Spinner is not an option as it will be at Pick an Author by
            //default if the user does nothing.  We are giving the instruction that if the user does
            //not actively (actually physically select an author instead of leaving it on Pick an Author
            //without pressing anything) select an author then the value in savedAuthor is set
            //to "" and the image in the ImageView imgPickedAuthor is set to the file called
            //questionmark in the res/drawable folder.
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                savedAuthor = "";
                imgPickedAuthor.setImageResource(R.drawable.questionmark);
            }
        });
        //END

        //We are calling the loadPreferences method for Persistent Storage. Persistent storage is looked at
        //in much more detail in another project "Build a Persistent Storage App in Android Studio"
        //and we will not be looking at it here. You do not have to do anything with this code.
        //START
        loadPreferences();
        //END
    }

    //This method checks what information is sent to it from the onSelectedItem method using if/else if
    // and then updates what is in txtPickedAuthorBook and txtPickedAuthorQuote to match the author
    //that is picked.
    public void getPickedAuthorBooksAndQuote(String s){
        String theAuthor = s;
        if (theAuthor.equals("oscar wilde")){
            txtPickedAuthorBook.setText(R.string.wildebook);
            txtPickedAuthorQuote.setText(Arrays.toString(oscarwilde));
        }
        else if (theAuthor.equals("cs lewis")){
            txtPickedAuthorBook.setText(R.string.lewisbook);
            txtPickedAuthorQuote.setText(Arrays.toString(cslewis));
        }
        else if (theAuthor.equals("bram stoker")){
            txtPickedAuthorBook.setText(R.string.stokerbook);
            txtPickedAuthorQuote.setText(Arrays.toString(bramstoker));
        }
        else if (theAuthor.equals("jonathan swift")){
            txtPickedAuthorBook.setText(R.string.swiftbook);
            txtPickedAuthorQuote.setText(Arrays.toString(jonathanswift));
        }
        else if (theAuthor.equals("samuel beckett")){
            txtPickedAuthorBook.setText(R.string.beckettbook);
            txtPickedAuthorQuote.setText(Arrays.toString(samuelbeckett));
        }
        else{
            txtPickedAuthorBook.setText(R.string.whoistheauthor);
            txtPickedAuthorQuote.setText(Arrays.toString(defaultquote));
        }
    }

    //The loadQuotations method looks for the specific file based on the id sent to this method from the
    //onCreate method (e.g. R.raw.defaultquote), takes the data from inside the file,
    //and returns that data to the place from which the method was called. How java reads from files is
    //not android specific and is beyond the scope of an android project.
    private String[] loadQuotations(int fileResourceID) {
        Resources myRes = getResources();
        InputStream is;
        InputStreamReader isr;
        BufferedReader br;
        ArrayList<String> quotes = new ArrayList<String>();
        String line;
        try {
            is = myRes.openRawResource( fileResourceID );
            isr = new InputStreamReader(is);
            br = new BufferedReader(isr);
            while ( (line = br.readLine()) != null ) {
                quotes.add(line);
            }
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
        }
        String[] returnStrings = new String[quotes.size()];
        returnStrings = quotes.toArray(returnStrings);
        return returnStrings;
    }

    //You do not have to change any code below here.  The code below here is related to Persistent
    //Storage (Shared Preferences) and Menus. We have looked at persistent storage in the Build a
    //Persistent Storage App in Android Studio project on Coursera. If you would like to know more
    //about this you can go to that course. We have adjusted the value of the Strings beginning
    //with saved (savedAuthor) to match what the user has selected the last time they
    //closed the app. We are using a key value pairs below to remember what the value is and to update
    //the String value based on what is in the key value pair. In the loadPreferences() we are also
    //setting what is showing on our app for our Spinner to match what we have now put in savedAuthor.
    public void loadPreferences() {
        // Get the stored preferences
        int mode = Activity.MODE_PRIVATE;
        android.content.SharedPreferences mySharedPreferences = getSharedPreferences(MYPREFS, mode);
        //When you see words in grey like key: and defValue: below they are tags put in by android
        //studio.  These are not typed in by a person.
        //Retrieve the saved values and put them in savedMonthOfBirth, savedToggleButtonChoice, &
        //savedNumberPicked as appropriate.
        savedAuthor = mySharedPreferences.getString("author", "").toLowerCase();

        //We are using a switch statement to check what is the value that was saved in savedAuthor
        //and if what is saved is "oscar wilde", or "cs lewis", or "bram stoker", or "jonathan swift",
        //or "samuel beckett" then our app will go and select the appropriate author for us in the
        // Spinner sprPickedAuthor. If what is saved in savedAuthor does not match one of these then
        //the default of "Pick an Author" is selected. Remember that the position index starts at 0
        //so oscar wilde is at position 1, option "Pick an Author" is at position 0, etc.
        switch (savedAuthor) {
            case "oscar wilde":  sprPickedAuthor.setSelection(1);       break;
            case "cs lewis":  sprPickedAuthor.setSelection(2);          break;
            case "bram stoker":  sprPickedAuthor.setSelection(3);       break;
            case "jonathan swift":  sprPickedAuthor.setSelection(4);    break;
            case "samuel beckett":  sprPickedAuthor.setSelection(5);    break;
            default :  sprPickedAuthor.setSelection(0);                 break;
        }


    }
    //When the user closes the app we are checking what value is in savedAuthor, and we are saving
    //that information in a key value pair with the keys of "author".
    protected void savePreferences() {
        // Create or retrieve the shared preference object.
        int mode = Activity.MODE_PRIVATE;
        android.content.SharedPreferences mySharedPreferences = getSharedPreferences(MYPREFS, mode);
        // Retrieve an editor to modify the shared preferences.
        android.content.SharedPreferences.Editor editor = mySharedPreferences.edit();
        // Store data in the shared preferences object as key value pair.
        editor.putString("author", savedAuthor);
        // Commit the changes.
        editor.commit();
    }

    //This method just tells program what to do when the user closes the app (exit the app and call
    //the savePreferences method so that the relevant information is saved.
    @Override
    protected void onStop() {
        super.onStop();
        this.savePreferences();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_shared_preferences_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
