package com.peacockweb.billsplitter;

import android.support.annotation.RequiresPermission;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListPopupWindow;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.tokenautocomplete.TokenCompleteTextView;

import java.util.ArrayList;
import java.util.List;

public class AddGroup extends AppCompatActivity implements View.OnClickListener, AddGroupMemberDialog.NoticeDialogListener, TokenCompleteTextView.TokenListener {

    LinearLayout layoutOfPopup;
    PopupWindow popupMessage;
    EditText popupButton;
    Button insidePopupButton;
    TextView popupText;
    ListPopupWindow friendsPopup;

    ArrayList<GroupMember> people;
    ArrayAdapter<GroupMember> adapter;
    MembersCompletionView completionView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_group);

        people = new ArrayList();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Users");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> users, ParseException e) {
                if (e == null) {
                    for (int i = 0; i < users.size(); i++) {
                        people.add(new GroupMember(
                                users.get(i).getString("fName") + " " + users.get(i).getString("lName"),
                                users.get(i).getString("username")));
                    }
                } else {
                    Log.d("score", "Error: " + e.getMessage());
                }
            }
        });

/*        people = new GroupMember[]{
                new GroupMember("Marshall Weir", "marshall@example.com"),
                new GroupMember("Margaret Smith", "margaret@example.com"),
                new GroupMember("Max Jordan", "max@example.com"),
                new GroupMember("Meg Peterson", "meg@example.com"),
                new GroupMember("Amanda Johnson", "amanda@example.com"),
                new GroupMember("Terry Anderson", "terry@example.com")
        };*/

        adapter = new ArrayAdapter<GroupMember>(this, android.R.layout.simple_list_item_1, people);

        completionView = (MembersCompletionView)findViewById(R.id.addGroupMembers);
        completionView.setTokenListener(this);
        completionView.setTokenClickStyle(TokenCompleteTextView.TokenClickStyle.Delete);
        completionView.setAdapter(adapter);
    }

    @Override
    public void onTokenAdded(Object token) {
        System.out.println("Added: " + token);
    }

    @Override
    public void onTokenRemoved(Object token) {
        System.out.println("Removed: " + token);
    }

    public void onCreateGroupClick(View view) {
        EditText groupName = (EditText) findViewById(R.id.newGroupName);
        EditText groupMembers = (EditText) findViewById(R.id.addGroupMembers);

        popupButton = (EditText) findViewById(R.id.addGroupMembers);
        popupButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        //popupMessage.showAsDropDown(popupButton, 0, 0);
        //DialogFragment newFragment = new AddGroupMemberDialog();
        //newFragment.show(getSupportFragmentManager(), "groupMember");
    }

    @Override
    public void onDialogPositiveClick(String username) {
        EditText text = (EditText) findViewById(R.id.addGroupMembers);
        text.setText(username);
    }
}
