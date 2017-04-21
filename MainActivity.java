package learn2crack.listview;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;



import learn2crack.listview.library.JSONParser;



public class MainActivity extends Activity {
	ListView list;
	TextView ver;
	TextView ids;
	TextView name;
	TextView api;
	Button Btngetdata;
	ArrayList<HashMap<String, String>> oslist = new ArrayList<HashMap<String, String>>();
	
	//URL to get JSON Array
	private static String url = "https://api.github.com/users";
	
	//JSON Node Names 
	private static final String TAG_LOGIN = "login";
	private static final String TAG_ID = "id";
	private static final String TAG_AVATAR_URL = "avatar_url";
	private static final String TAG_GRAVATER_ID = "gravatar_id";
	
	JSONArray android = null;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      
        setContentView(R.layout.activity_main);
        oslist = new ArrayList<HashMap<String, String>>();

        
        
        Btngetdata = (Button)findViewById(R.id.getdata);
        Btngetdata.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View view) {
		         new JSONParse().execute();

				
			}
		});
        
        
    }


    
    private class JSONParse extends AsyncTask<String, Void, Boolean> {
    	 private ProgressDialog pDialog;
    	@Override
        protected void onPreExecute() {
            super.onPreExecute();
             ver = (TextView)findViewById(R.id.vers);
			ids = (TextView)findViewById(R.id.id);
			 name = (TextView)findViewById(R.id.name);
			 api = (TextView)findViewById(R.id.api);
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Getting Data ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
            
            
            
    	}
    	
    	@Override
        protected Boolean doInBackground(final String... args) {
    		
    		//JSONParser jParser = new JSONParser();
           JSONParserArry JSONARRY= new JSONParserArry();
			JSONArray json=JSONARRY.getJSONFromUrl(url);
    		// Getting JSON from URL
    		//JSONObject json = jParser.getJSONFromUrl(url);
			/*JSONObject json=jParser.getJSONFromUrl(url);
    		return json;*/

			for (int i = 0; i < json.length(); i++){
				try{

					JSONObject e = json.getJSONObject(i);
					String login=e.getString(TAG_LOGIN);
					String id=e.getString(TAG_ID);
					String avatar_id=e.getString(TAG_AVATAR_URL);
					String gravater_id=e.getString(TAG_GRAVATER_ID);

					// Adding value HashMap key => value


					HashMap<String, String> map = new HashMap<String, String>();

					map.put(TAG_LOGIN, login);
					map.put(TAG_ID, id);
					map.put(TAG_AVATAR_URL, avatar_id);
					map.put(TAG_GRAVATER_ID, gravater_id);

					oslist.add(map);
					list=(ListView)findViewById(R.id.list);

				}  catch (JSONException e)
				{ e.printStackTrace(); }


			}
			return null;
    	}
    	 @Override
         protected void onPostExecute(final Boolean success) {
    		 pDialog.dismiss();


    				
    				ListAdapter adapter = new SimpleAdapter(MainActivity.this, oslist,
    						R.layout.list_v,
    						new String[] { TAG_LOGIN,TAG_ID, TAG_AVATAR_URL,TAG_GRAVATER_ID }, new int[] {
    								R.id.vers,R.id.id,R.id.name, R.id.api});

    				list.setAdapter(adapter);
    				list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

    		            @Override
    		            public void onItemClick(AdapterView<?> parent, View view,
    		                                    int position, long id) {
    		                Toast.makeText(MainActivity.this, "You Clicked at "+oslist.get(+position).get("name"), Toast.LENGTH_SHORT).show();

    		            }
    		        });


    		}

    		 
    	 }
    }
    

