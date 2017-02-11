package com.approdo.addressbookapp;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;


public class MainActivity extends ListActivity {

	// The Intent is used to issue that an operation should
	// be performed
	Intent intent;
	TextView contactId;

	// The object that allows me to manipulate the database
	DBTools dbTools = new DBTools(this);
	
	// Called when the Activity is first called
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// Gets all the data from the database and stores it
		// in an ArrayList
		ArrayList<HashMap<String, String>> contactList =  dbTools.getAllContacts();

		// Check to make sure there are contacts to display
		if(contactList.size()!=0) {
			
			// Get the ListView and assign an event handler to it
			ListView listView = getListView();
			listView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub
					
					// When an item is clicked get the TextView
					// with a matching checkId
					contactId = (TextView) view.findViewById(R.id.contactId);
					
					// Convert that contactId into a String					
					String contactIdValue = contactId.getText().toString();
					
					// Signals an intention to do something
					// getApplication() returns the application that owns
					// this activity
					Intent  theIndent = new Intent(getApplication(), EditContact.class);
					
					// Put additional data in for EditContact to use				
					theIndent.putExtra("contactId", contactIdValue); 
					
					// Calls for EditContact					
					startActivity(theIndent);
				}
			});
			
			// A list adapter is used bridge between a ListView and
			// the ListViews data
						
			// The SimpleAdapter connects the data in an ArrayList
			// to the XML file
			
			// First we pass in a Context to provide information needed
			// about the application
			// The ArrayList of data is next followed by the xml resource
			// Then we have the names of the data in String format and
			// their specific resource ids
						
			ListAdapter adapter = new SimpleAdapter( MainActivity.this,contactList, R.layout.contact_entry, 
					new String[] { "contactId","lastName", "firstName"}, 
					new int[] {R.id.contactId, R.id.lastName, R.id.firstName});
				
			// setListAdapter provides the Cursor for the ListView
			// The Cursor provides access to the database data
				
			setListAdapter(adapter);
		}
	}
	
	// When showAddContact is called with a click the Activity 
	// NewContact is called	
	public void showAddContact(View view) {
		Intent theIntent = new Intent(getApplication(), NewContact.class);
		startActivity(theIntent);
	}
}
